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

    private static Street mStreet;
    public static Street Empty()
    {
        if (mStreet==null) {

            mStreet = new Street();
            for (int hour=0;hour<24;hour++)
            {
                mStreet.hours.add(new HourMapping(hour));
            }
        }
        return  mStreet;
    }

    @Override
    public String toString() {
        return this.Name;
    }
}
