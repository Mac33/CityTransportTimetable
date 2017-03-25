package com.freeman.mac.citytransporttimetable.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mac on 7. 3. 2017.
 */

public class Vehicle {

    public int CurrentDirection = 0;
    public String CurrentStreetName = "";
    public int CurrentStreetIndex = 0;
    public int Number = 0;

    public static int DIRECTION_ONE = 0;
    public static int DIRECTION_TWO = 1;

    private List<TimePeriod> directionOne;
    private List<TimePeriod> directionTwo;

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

    public List<Street> getCurrentDirectionStreets()
    {
        return getCurrentDirectionTimePeriods().get(0).getStreets();
    }



    public String getCurrentDirectionStreetName(int index)
    {
        return getCurrentDirectionTimePeriods().get(0).getStreets().get(index).Name;
    }



    public Vehicle(int value) {
        this();
        this.Number = value;
    }


    public Vehicle() {
        this.directionOne = new ArrayList<TimePeriod>();
        this.directionTwo = new ArrayList<TimePeriod>();
    }



    void addTimePeriod(String name)
    {
        this.directionOne.add(new TimePeriod(name));
        this.directionTwo.add(new TimePeriod(name));
    }

    public void setCurrentStreet(int index) {
        this.CurrentStreetIndex = index;
        for (TimePeriod item:this.getCurrentDirectionTimePeriods()) {
            item.setCureentStreet(index);
        }
        this.CurrentStreetName = this.getCurrentDirectionTimePeriods().get(0).getStreetName(index);
    }



    public void setCurrentDirection(int index) {
        this.CurrentDirection = index;
    }

    @Override
    public String toString() {
        return Integer.toString(this.Number);
    }
}
