package com.freeman.mac.citytransporttimetable.model;

import android.util.Log;

import com.freeman.mac.citytransporttimetable.StreetItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mac on 7. 3. 2017.
 */

public class Vehicle {

    public static int DIRECTION_ONE = 0;
    public static int DIRECTION_TWO = 1;
    public int CurrentDirection = 0;
    public String CurrentStreetName = "";
    public int CurrentStreetIndex = 0;
    public int Number = 0;
    private List<TimePeriod> directionOne;
    private List<TimePeriod> directionTwo;

    public eVehicleType Type = eVehicleType.None;
    public int IconResId=0;
    public  int IconToolBarId = 0;


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
        this.directionOne = new ArrayList<TimePeriod>();
        this.directionTwo = new ArrayList<TimePeriod>();
    }

    public List<TimePeriod> getDirectionOneTimePeriods() {
        return this.directionOne;
    }

    public List<TimePeriod> getDirectionTwoStreets() {
        return this.directionTwo;
    }

    public List<TimePeriod> getCurrentDirectionTimePeriods() {
        if (this.CurrentDirection == DIRECTION_ONE) {
            return this.directionOne;
        } else {
            return this.directionTwo;
        }
    }

    public List<Street> getCurrentDirectionStreets() {
        return getCurrentDirectionTimePeriods().get(0).getStreets();
    }

    public String getCurrentDirectionStreetName(int index) {
        return getCurrentDirectionTimePeriods().get(0).getStreets().get(index).Name;
    }

    void addTimePeriod(String name) {
        this.directionOne.add(new TimePeriod(name));
        this.directionTwo.add(new TimePeriod(name));
    }

    public void setCurrentStreet(int index) {
        this.CurrentStreetIndex = index;
        for (TimePeriod item : this.getCurrentDirectionTimePeriods()) {
            item.setCureentStreet(index);
        }
        this.CurrentStreetName = this.getCurrentDirectionTimePeriods().get(0).getStreetName(index);
    }



    public void setCurrentDirection(int index) {
        this.CurrentDirection = index;
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
        String oldStreetName = this.CurrentStreetName;
        boolean found = false;
        for (int index = 0; index< this.getCurrentDirectionTimePeriods().get(0).getStreets().size();index++)
        {
            String name = this.getCurrentDirectionTimePeriods().get(0).getStreetName(index);
            if(name==oldStreetName)
            {
                found = true;
                this.setCurrentStreet(index);
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


        ArrayList<List<TimePeriod>> directions = new ArrayList<List<TimePeriod>>();

        directions.add(this.directionOne);
        directions.add(this.directionTwo);

        int directionIndex = 0;
        for (List<TimePeriod>direction:directions) {

            for (int timePeriod = 0;timePeriod<3;timePeriod++)
            {
                int maxStreets = 15;
                for(int streetIndex = 0;streetIndex < maxStreets;streetIndex ++)
                {
                    Street currentStreet = new Street();
                    int number = Math.abs ((directionIndex * (maxStreets - 1))  - streetIndex) ;
                    currentStreet.Name = "Vehicle " + this.Number + ":StreetIndex " + number;

                    for (int hourNumber = 0 ;hourNumber < 24;hourNumber++)
                    {
                        HourMapping hour = new HourMapping();
                        hour.Hour = hourNumber;
                        for (int minute = 0; minute < timePeriod+1; minute++)
                        {
                            hour.addMinute(streetIndex,0);
                        }
                        currentStreet.getHours().add(hour);
                    }
                    direction.get(timePeriod).addStreet(currentStreet);
                }
            }
            directionIndex++;
        }
    }

    public void load(List<String> data) {
        this.addTimePeriod("Pondelok - Piatok (školský rok)");
        this.addTimePeriod("Pondelok - Piatok (školské prázdniny)");
        this.addTimePeriod("Sobota - Nedeľa, sviatok");
        int timePeriod = 0;
        String currentStreetName= StringUtils.Empty;
        Street currentStreet = null;
        List<TimePeriod> direction = this.directionOne;
        for (String item : data) {

            if (item.toCharArray().length > 0) {
                char streetChar = item.toCharArray()[0];
                if (!Character.isDigit(streetChar)) {
                    if (timePeriod ==2)
                    {
                        timePeriod = 0;
                    }
                    currentStreetName = item;
                    currentStreet = new Street();
                    currentStreet.Name  = currentStreetName;
                    direction.get(timePeriod).addStreet(currentStreet);
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
                    currentStreet.getHours().add(hour);
                }

            } else {
                timePeriod += 1;
                currentStreet = new Street();
                currentStreet.Name  = currentStreetName;
                direction.get(timePeriod).addStreet(currentStreet);


            }


        }

    }

    @Override
    public String toString() {
        return Integer.toString(this.Number);
    }
}
