package com.freeman.mac.citytransporttimetable.model;

import android.widget.Switch;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mac on 12. 3. 2017.
 */

public class TimePeriod {

    public static final String SchoolHolidays = "PRÁZDNINY - School Holidays";
    public static final String WorkDays = "PRACOVNÉ DNI - Work Days";
    public static final String Weekend = "VOĽNÉ DNI - Weekend";


    public  static int getTimePeriodType(String name) {
        switch (name) {
            case SchoolHolidays:
                return 1;
            case WorkDays:
                return 2;
            case Weekend:
                return 3;
            default:
                return 0;
        }
    }


    public List<HourMapping> Hours;
    public String Name;


    public TimePeriod(String name) {
        this.Name = name;
        this.Hours = new  ArrayList<HourMapping>();
     }



}
