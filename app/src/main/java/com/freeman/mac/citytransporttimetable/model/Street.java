package com.freeman.mac.citytransporttimetable.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mac on 7. 3. 2017.
 */

public class Street {
    public String Name;

    private List<TimePeriod> timePeriods;


    public Street(Vehicle vehicle, String name )
    {
        this(vehicle);
        this.Name = name;


    }

    public boolean RequestStop = false;



    public List<TimePeriod> getTimePeriods() {
        return this.timePeriods;
    }



    public Street(Vehicle vehicle)
    {
        this.timePeriods = new ArrayList<>();
        for (String name:vehicle.timePeriodNames) {
            this.timePeriods.add(new TimePeriod(name));
        }

    }


    @Override
    public String toString() {
        return this.Name;
    }
}
