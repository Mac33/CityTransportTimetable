package com.freeman.mac.citytransporttimetable.model;

import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import  com.google.gson.annotations.*;

/**
 * Created by Mac on 7. 3. 2017.
 */

public class Vehicle {

    public static int DIRECTION_ONE = 0;

    public static int DIRECTION_TWO = 1;

    public List<String> timePeriodNames = new ArrayList<String>();

    public int CurrentDirection = DIRECTION_ONE;

    public int CurrentStreetIndex = 0;

    private Street currentStreet = null;

    public int Number = 0;

    @SerializedName("DirectionOne")
    public List<Street> DirectionOne;

    @SerializedName("DirectionTwo")
    public List<Street> DirectionTwo;

    public eVehicleType Type = eVehicleType.None;

    public int IconResId=0;

    public  int IconToolBarId = 0;

    @SerializedName("DirectionOneName")
    public String DirectionOneName ;

    @SerializedName("DirectionTwoName")
    public String DirectionTwoName;

    @SerializedName("Descriptions")
    public List<VehicleDescriptionItem> Descriptions = new ArrayList<>();

    public  List<Street> getStreets()
    {
        return  this.getCurrentDirectionStreets();
    }

    public Street getCurrentStreet()
    {
      return this.currentStreet;
    }

    public enum eVehicleType
    {
        None,
        Trolleybus,
        CityBus,
        NightBus,
        BusForSelectedPassenger
    }



    public Vehicle(int number, eVehicleType type, int iconResId,int iconToolBarId) {
        this();
        this.Number = number;
        this.Type = type;
        this.IconResId = iconResId;
        this.IconToolBarId = iconToolBarId;
    }

    public Vehicle() {
        this.DirectionOne = new ArrayList<>();
        this.DirectionTwo = new ArrayList<>();

    }



    public List<Street> getCurrentDirectionStreets() {
        if (this.CurrentDirection == DIRECTION_ONE) {
            return this.DirectionOne;
        } else {
            return this.DirectionTwo;
        }
    }

    public String getCurrentDirectionName() {
        if (this.CurrentDirection == DIRECTION_ONE) {
            return this.DirectionOneName;
        } else {
            return this.DirectionTwoName;
        }
    }


    public  boolean HasTwoDirections()
    {
        return  !this.DirectionOne.isEmpty() && !this.DirectionTwo.isEmpty();
    }


    void addTimePeriod(String name) {
        this.timePeriodNames.add(name);
    }



    public void setCurrentStreet(int index) {
        this.CurrentStreetIndex = index;
        this.currentStreet = this.getCurrentDirectionStreets().get(index);
    }



    public  void  swapDirection()
    {
        if  (this.CurrentDirection == DIRECTION_ONE)
        {
            this.CurrentDirection = DIRECTION_TWO;
        }else
        {
            this.CurrentDirection = DIRECTION_ONE;
        }
        String oldStreetName = this.currentStreet.Name;
        boolean found = false;
        for (int index = 0; index< this.getCurrentDirectionStreets().size();index++)
        {
            String name = this.getCurrentDirectionStreets().get(index).Name ;
            if(name.equals(oldStreetName))
            {
                found = true;
                if (index==this.getCurrentDirectionStreets().size()-1)
                {
                    index-=1;
                }
                this.setCurrentStreet(index);
                break;
            }
        }
        if (!found)
        {
            this.setCurrentStreet(0);
        }


    }



    public void generate() {
        this.addTimePeriod("Pondelok - Piatok (školský rok)");
        this.addTimePeriod("Pondelok - Piatok (školské prázdniny)");
        this.addTimePeriod("Sobota - Nedeľa, sviatok");


        ArrayList<List<Street>> directions = new ArrayList<List<Street>>();

        directions.add(this.DirectionOne);
        directions.add(this.DirectionTwo);

        int directionIndex = 0;
        for (List<Street>direction:directions) {

                int maxStreets = 25;
                for(int streetIndex = 0;streetIndex < maxStreets;streetIndex ++)
                {
                    Street currentStreet = new Street(this);
                    int number = Math.abs ((directionIndex * (maxStreets - 1))  - streetIndex) ;
                    currentStreet.Name = "Vehicle " + this.Number + ":StreetIndex " + number;
                    for (int timePeriod = 0;timePeriod<3;timePeriod++) {
                        for (int hourNumber = 0; hourNumber < 24; hourNumber++) {
                            HourMapping hour = new HourMapping();
                            hour.Hour = hourNumber;
                            for (int minute = 0; minute < timePeriod + 1; minute++) {
                                if (directionIndex == DIRECTION_ONE) {
                                    hour.addMinute(number, 0);

                                } else {
                                    hour.addMinute(number, 1);
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

    public  static Vehicle Deserialize(String body)
    {
        Gson gson = new GsonBuilder().create();
        Vehicle item = gson.fromJson(body, Vehicle.class);
        return item;

    }

    public void load(List<String> data) {
        this.addTimePeriod("Pondelok - Piatok (školský rok)");
        this.addTimePeriod("Pondelok - Piatok (školské prázdniny)");
        this.addTimePeriod("Sobota - Nedeľa, sviatok");
        int timePeriod = 0;
        String currentStreetName= StringUtils.Empty;
        Street currentStreet = null;
        List<Street> direction = this.DirectionOne;
        for (String item : data) {
            if (item.toCharArray().length > 0) {
                char streetChar = item.toCharArray()[0];
                if (!Character.isDigit(streetChar))
                {
                    if (timePeriod == 2)
                    {
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

                    HourMapping hour=null;
                    StringBuilder strTime = new StringBuilder();
                    StringBuilder strType = new StringBuilder();

                    for (char timeChar:item.toCharArray()) {
                        if (Character.isLetterOrDigit(timeChar)) {
                            if (Character.isDigit(timeChar)){
                                strTime.append(timeChar);
                            }
                            if (Character.isLetter(timeChar)){
                                strType.append(timeChar);
                            }
                        }else {
                            int intValue = 0;
                            try {
                                intValue = Integer.parseInt(strTime.toString());
                            }catch (Exception e)
                            {
                                Log.w("CityTransportTimetable", "Invalid parseInt");
                            }

                            if (hour==null)
                            {
                                hour = new HourMapping();
                                hour.Hour = intValue;
                            }else{
                                hour.addMinute(intValue,0);
                            }
                            strTime = new StringBuilder();
                        }
                    }
                    int intValue = 0;
                    try {
                        intValue = Integer.parseInt(strTime.toString());
                    }catch (Exception e)
                    {
                        Log.w("CityTransportTimetable", "Invalid parseInt");
                    }
                    hour.addMinute(intValue,0);
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
}
