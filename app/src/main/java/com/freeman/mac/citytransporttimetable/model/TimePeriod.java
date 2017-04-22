package com.freeman.mac.citytransporttimetable.model;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mac on 12. 3. 2017.
 */

public class TimePeriod {

    public static final String ShoolHolidays = "PRÁZDNINY - School Holidays";
    public static final String WorkDays = "PRACOVNÉ DNI - Work Days";
    public static final String Weekend = "VOĽNÉ DNI - Weekend";

    public List<HourMapping> Hours;
    public String Name;


    public TimePeriod(String name) {
        this.Name = name;
        this.Hours = new  ArrayList<HourMapping>();
     }



}
