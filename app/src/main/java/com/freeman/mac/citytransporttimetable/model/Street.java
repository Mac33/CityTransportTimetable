package com.freeman.mac.citytransporttimetable.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mac on 7. 3. 2017.
 */



public class Street {
    public String Name;

    private List<HourMapping> hours ;


    public Street(String name )
    {
        this();
        this.Name = name;

    }


    public List<HourMapping> getHours() {
        return this.hours;
    }



    public Street()
    {
        this.hours = new ArrayList<HourMapping>();

    }

    @Override
    public String toString() {
        return this.Name;
    }
}
