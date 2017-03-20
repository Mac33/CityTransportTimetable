package com.freeman.mac.citytransporttimetable.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mac on 7. 3. 2017.
 */

public class Vehicle {

    public int CurrentDirection = 0;
    public String CurrentStreetName;
    public TimePeriod CurrentTimePeriod;

    public void setCurrentStreetToAllPeriods(int index) {

        for (TimePeriod item : this.timePeriods) {
            item.setCureentStreet(index);
        }
    }


    public TimePeriod getCurrentTimePeriod() {
        if (this.CurrentTimePeriod == null) {
            this.CurrentTimePeriod = this.timePeriods.get(0);
        }
        return this.CurrentTimePeriod;
    }

    public List<TimePeriod> timePeriods;

    public int Number = 0;

    public Vehicle(int value) {
        this();
        this.Number = value;
    }

    public Vehicle() {
        this.timePeriods = new ArrayList<TimePeriod>();
    }

    void addTimePeriod(String name) {
        this.timePeriods.add(new TimePeriod(name));
    }

    public List<TimePeriod> getTimePeriods() {
        return timePeriods;
    }

    public TimePeriod getTimePeriod(int index) {
        TimePeriod ret = null;
        if (timePeriods.size() > index) {
            ret = timePeriods.get(index);
        }
        return ret;
    }

    public boolean hasTimePeriod(int index)
    {
        return index >=0 && timePeriods.size() > index  ;
    }

    @Override
    public String toString() {
        return Integer.toString(this.Number);
    }
}
