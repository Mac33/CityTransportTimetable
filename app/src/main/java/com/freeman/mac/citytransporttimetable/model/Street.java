package com.freeman.mac.citytransporttimetable.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mac on 7. 3. 2017.
 */

public class Street {
    public String Name;

    public List<TimePeriod> TimePeriods;


    public Street(Vehicle vehicle, String name )
    {
        this(vehicle);
        this.Name = name;


    }

    public boolean RequestStop = false;



    public List<TimePeriod> getTimePeriods() {
        return this.TimePeriods;
    }



    public Street(Vehicle vehicle)
    {
        this.TimePeriods = new ArrayList<>();
        for (String name:vehicle.timePeriodNames) {
            this.TimePeriods.add(new TimePeriod(name));
        }

    }


    @Override
    public String toString() {
        return this.Name;
    }
}
