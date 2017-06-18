package com.freeman.mac.citytransporttimetable.model;

import android.content.Context;
import android.util.Log;

import com.freeman.mac.citytransporttimetable.database_model.VehicleDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mac on 7. 3. 2017.
 */

public class Vehicle {

    public static int DIRECTION_ONE = 0;

    public static int DIRECTION_TWO = 1;
    public int CurrentDirection = DIRECTION_ONE;
    public int CurrentStreetIndex = 0;
    public eVehicleType Type = eVehicleType.None;
    public int ColorResId = 0;
    public int IconResId = 0;
    public int Number = 0;
    public int DataResId;
    public VehicleDatabase Database;
    private List<String> timePeriodNames = new ArrayList<String>();
    private Street currentStreet = null;
    private TrafficData mData;


    public Vehicle(int number, eVehicleType type, int colorResId, int iconResId) {
        this();
        this.Number = number;
        this.Type = type;
        this.ColorResId = colorResId;
        this.IconResId = iconResId;
    }


    public Vehicle() {
        //this.Data = new TrafficData();

    }

    public static TrafficData Deserialize(String body) {
        Gson gson = new GsonBuilder().create();
        TrafficData item = gson.fromJson(body, TrafficData.class);
        return item;

    }

    public TrafficData getData() {
        if (this.mData == null) {

            if (this.DataResId > 0 ) {
               this.loadDataFromJsonFile();
            }

            if (this.mData == null) {
                this.mData = new TrafficData();
                this.mData.DirectionOne = new ArrayList<>();
                this.mData.DirectionTwo = new ArrayList<>();
            }

            /*if (this.Database.getNeedToRefill())
            {
               this.saveToDatabase();
            }
            else
            {
               this.loadFromDatabase();
            }*/



        }
        return this.mData;

    }


    private void loadFromDatabase()
    {
        this.Database.LoadFromDatabase(this, this.mData, this.Number);
    }


    private void saveToDatabase()
    {
        this.Database.SaveToDatabase(this.mData,this.Number);
    }


    private void loadDataFromJsonFile() {
        String jsonData = this.loadDataJson(this.DataResId);
        this.mData = new TrafficData();

        String fileName = Integer.toString(this.Number) + ".txt";
        Context cx = TransportTimetables.getInstance().getContext();
        try {
            FileInputStream fis = cx.openFileInput(fileName);
            ObjectInputStream is = new ObjectInputStream(fis);
            this.mData = (TrafficData) is.readObject();
            is.close();
            fis.close();
        } catch (Exception ex) {
            this.mData = Vehicle.Deserialize(jsonData);
        }

        try {

            FileOutputStream fos = cx.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(this.mData);
            os.close();
            fos.close();
        } catch (Exception e) {
            Log.v("Traffic", e.toString());
        }
    }

    public String loadDataJson(int id) {
        Context cx = TransportTimetables.getInstance().getContext();
        InputStream inputStream = cx.getResources().openRawResource(id);
        InputStreamReader inputReader = new InputStreamReader(inputStream);
        BufferedReader buffReader = new BufferedReader(inputReader);

        String line;
        StringBuilder sb = new StringBuilder();
        try {
            while ((line = buffReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {

        }
        return sb.toString();
    }

    public List<Street> getStreets() {
        return this.getCurrentDirectionStreets();
    }

    public Street getCurrentStreet() {
        return this.currentStreet;
    }

    public void setCurrentStreet(int index) {

        //If street doesn't have any timetable set last one
        if (this.getCurrentDirectionStreets().get(index).isEmpty())
            index--;

        this.CurrentStreetIndex = index;
        this.currentStreet = this.getCurrentDirectionStreets().get(index);

    }

    public List<Street> getCurrentDirectionStreets() {
        if (this.CurrentDirection == DIRECTION_ONE) {
            return this.getData().DirectionOne;
        } else {
            return this.getData().DirectionTwo;
        }
    }

    public String getCurrentDirectionName() {
        if (this.CurrentDirection == DIRECTION_ONE) {
            return this.getData().DirectionOneName;
        } else {
            return this.getData().DirectionTwoName;
        }
    }

    public boolean HasTwoDirections() {
        return !this.getData().DirectionOne.isEmpty() && !this.getData().DirectionTwo.isEmpty();
    }

    void addTimePeriod(String name) {
        this.timePeriodNames.add(name);
    }

    public List<String> getCurrentStreetPeriodNames() {
        List<String> names = new ArrayList<>();

        if (this.currentStreet != null) {

            for (TimePeriod timePeriod : this.currentStreet.TimePeriods) {
                boolean timePeriodIsEmpty = true;
                for (HourMapping hour : timePeriod.Hours) {
                    if (!hour.getMinutes().isEmpty()) {
                        timePeriodIsEmpty = false;
                    }
                }
                if (!timePeriodIsEmpty)
                    names.add(timePeriod.Name);

            }
        }
        return names;
    }

    public List<String> getTimePeriodNames() {


        if (this.timePeriodNames.isEmpty()) {
            List<Street> allStreets = new ArrayList<>();
            allStreets.addAll(this.getData().DirectionOne);
            allStreets.addAll(this.getData().DirectionTwo);

            for (Street street : allStreets) {
                for (TimePeriod timePeriod : street.TimePeriods) {
                    if (!this.timePeriodNames.contains(timePeriod.Name)) {
                        this.timePeriodNames.add(timePeriod.Name);
                    }
                }
            }

        }
        return this.timePeriodNames;


    }

    public void swapDirection() {
        if (this.CurrentDirection == DIRECTION_ONE) {
            this.CurrentDirection = DIRECTION_TWO;
        } else {
            this.CurrentDirection = DIRECTION_ONE;
        }
        String oldStreetName = this.currentStreet.Name;
        boolean found = false;
        for (int index = 0; index < this.getCurrentDirectionStreets().size(); index++) {
            String name = this.getCurrentDirectionStreets().get(index).Name;
            if (name.equals(oldStreetName)) {
                found = true;
                if (index == this.getCurrentDirectionStreets().size() - 1) {
                    index -= 1;
                }
                this.setCurrentStreet(index);
                break;
            }
        }
        if (!found) {
            this.setCurrentStreet(0);
        }


    }


    public boolean hasAdditionalInformation() {
        for (VehicleDescriptionItem des : this.getData().Descriptions) {
            if (des.Sign == MinuteMapping.AdditionalInfromation)
                return true;
        }
        return false;
    }


    public void generate() {
        this.addTimePeriod(TimePeriod.WorkDays);
        this.addTimePeriod(TimePeriod.SchoolHolidays);
        this.addTimePeriod(TimePeriod.WeekendOrHolidays);


        ArrayList<List<Street>> directions = new ArrayList<List<Street>>();

        directions.add(this.getData().DirectionOne);
        directions.add(this.getData().DirectionTwo);

        int directionIndex = 0;
        for (List<Street> direction : directions) {

            int maxStreets = 25;
            for (int streetIndex = 0; streetIndex < maxStreets; streetIndex++) {
                Street currentStreet = new Street(this);
                int number = Math.abs((directionIndex * (maxStreets - 1)) - streetIndex);
                currentStreet.Name = "Vehicle " + this.Number + ":StreetIndex " + number;
                for (int timePeriod = 0; timePeriod < 3; timePeriod++) {
                    for (int hourNumber = 0; hourNumber < 24; hourNumber++) {
                        HourMapping hour = new HourMapping();
                        hour.Hour = hourNumber;
                        for (int minute = 0; minute < timePeriod + 1; minute++) {
                            if (directionIndex == DIRECTION_ONE) {
                                hour.addMinute(number, StringUtils.Empty);

                            } else {
                                hour.addMinute(number, "n");
                            }
                        }
                        currentStreet.getTimePeriods().get(timePeriod).Hours.add(hour);
                    }
                }
                direction.add(currentStreet);
            }
            directionIndex++;
        }
    }

    public void load(List<String> data) {
        this.addTimePeriod("Pondelok - Piatok (školský rok)");
        this.addTimePeriod("Pondelok - Piatok (školské prázdniny)");
        this.addTimePeriod("Sobota - Nedeľa, sviatok");
        int timePeriod = 0;
        String currentStreetName = StringUtils.Empty;
        Street currentStreet = null;
        List<Street> direction = this.getData().DirectionOne;
        for (String item : data) {
            if (item.toCharArray().length > 0) {
                char streetChar = item.toCharArray()[0];
                if (!Character.isDigit(streetChar)) {
                    if (timePeriod == 2) {
                        timePeriod = 0;
                    }
                    if (timePeriod == 0) {
                        currentStreetName = item;
                        currentStreet = new Street(this);
                        if (currentStreetName.contains("#RequestStop")) {
                            currentStreet.RequestStop = true;
                            currentStreetName = currentStreetName.substring(0, currentStreetName.indexOf("#RequestStop"));
                        }
                        currentStreet.Name = currentStreetName;
                        direction.add(currentStreet);
                    }
                } else {

                    HourMapping hour = null;
                    StringBuilder strTime = new StringBuilder();
                    StringBuilder strType = new StringBuilder();

                    for (char timeChar : item.toCharArray()) {
                        if (Character.isLetterOrDigit(timeChar)) {
                            if (Character.isDigit(timeChar)) {
                                strTime.append(timeChar);
                            }
                            if (Character.isLetter(timeChar)) {
                                strType.append(timeChar);
                            }
                        } else {
                            int intValue = 0;
                            try {
                                intValue = Integer.parseInt(strTime.toString());
                            } catch (Exception e) {
                                Log.w("CityTransportTimetable", "Invalid parseInt");
                            }

                            if (hour == null) {
                                hour = new HourMapping();
                                hour.Hour = intValue;
                            } else {
                                hour.addMinute(intValue, StringUtils.Empty);
                            }
                            strTime = new StringBuilder();
                        }
                    }
                    int intValue = 0;
                    try {
                        intValue = Integer.parseInt(strTime.toString());
                    } catch (Exception e) {
                        Log.w("CityTransportTimetable", "Invalid parseInt");
                    }
                    hour.addMinute(intValue, StringUtils.Empty);
                    currentStreet.getTimePeriods().get(timePeriod).Hours.add(hour);
                }

            } else {
                timePeriod += 1;

            }


        }

    }

    @Override
    public String toString() {
        return Integer.toString(this.Number);
    }

    public enum eVehicleType {
        None,
        Trolleybus,
        CityBus,
        NightBus,
        BusForSelectedPassenger
    }
}
