package com.freeman.mac.citytransporttimetable.model;

import android.content.Intent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by Mac on 7. 3. 2017.
 */

public class Street {
    public String Name;


    public List<TimePeriod> TimePeriods;

    @Expose
    @SerializedName("NextStreets")
    public List<NextStreetInfo> nextStreets = new ArrayList<>();


    public Street(Vehicle vehicle, String name) {
        this(vehicle);
        this.Name = name;

     }

    public boolean RequestStop = false;


    public List<TimePeriod> getTimePeriods() {
        return this.TimePeriods;
    }


    public Street(Vehicle vehicle) {
        this.TimePeriods = new ArrayList<>();
        for (String name : vehicle.timePeriodNames) {
            this.TimePeriods.add(new TimePeriod(name));
        }
    }



    public List<Integer> getUsedVehicleDescriptions()
    {
        List<Integer> usedDescriptions = new ArrayList<>();
        for (TimePeriod timePeriod:this.getTimePeriods())
        {
            for (HourMapping hourMapping :timePeriod.Hours)
            {
                for (MinuteMapping minuteMapping:hourMapping.getMinutes())
                {
                    if (minuteMapping.Type != 0)
                    {
                        if (!usedDescriptions.contains(minuteMapping.Type))
                        {
                              usedDescriptions.add(minuteMapping.Type);
                        }
                    }
                }
             }
        }
        return  usedDescriptions;
    }



    @Override
    public String toString() {
        return this.Name;
    }
}
