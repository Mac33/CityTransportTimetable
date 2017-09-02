package com.freeman.mac.citytransporttimetable.model;




import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;

/**
 * Created by Mac on 12. 3. 2017.
 */

public class TimePeriod {

    public static final String SchoolHolidays = "PRÁZDNINY - School Holidays";
    public static final String WorkDays = "PRACOVNÉ DNI - Work Days";
    public static final String WeekendOrHolidays = "VOĽNÉ DNI - Weekend";


    public static Calendar getCurrentTime()
    {
        Calendar currentTime = Calendar.getInstance();
        //currentTime.set(2017,Calendar.SEPTEMBER,1,22,30,0);
        return currentTime ;
    }

    public static int getTimePeriodType(String name) {
        switch (name) {
            case SchoolHolidays:
                return 1;
            case WorkDays:
                return 2;
            case WeekendOrHolidays:
                return 3;
            default:
                return 0;
        }
    }


    public List<HourMapping> Hours;
    public String Name;


    public TimePeriod(String name) {
        this.Name = name;
        this.Hours = new ArrayList<>();
    }


    public static int getTimePeriodViewPageIndex(Calendar date) {
        int arrayIndex  = getTimePeriodIndex(date);
        if(arrayIndex==2)
            return 3;
        if(arrayIndex==1)
            return 0;
        return  1;
    }


    public static int getTimePeriodIndex(Calendar date) {

        if (isHoliday(date))
            return 2;
        if(isSchoolHolidays(date))
            return 1;

        return 0;

    }


    public static int getDatabaseTimePeriod(Calendar date) {

        if (isHoliday(date))
            return 3;
        if(isSchoolHolidays(date))
            return 1;

        return 2;

    }


    private static boolean isHoliday(Calendar calendar) {


        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DATE);
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        if (day == Calendar.SUNDAY || day == Calendar.SATURDAY) {
            return true;
        }

        if (month == 1 && date == 1)
            return true;

        if (month == 1 && date == 6)
            return true;

        if (month == 5 && date == 1)
            return true;

        if (month == 5 && date == 8)
            return true;

        if (month == 7 && date == 5)
            return true;

        if (month == 8 && date == 29)
            return true;

        if (month == 9 && date == 1)
            return true;

        if (month == 9 && date == 15)
            return true;

        if (month == 11 && date == 1)
            return true;

        if (month == 11 && date == 17)
            return true;

        if (month == 12 && date == 24)
            return true;

        if (month == 12 && date == 25)
            return true;

        if (month == 12 && date == 26)
            return true;

        //Easter Friday
        EasterDay easterDay = getEasterSundayDate(year);
        if (easterDay.Month == month && easterDay.Day == (date - 2))
           return true;


        //Easter Monday
        if (easterDay.Month == month && easterDay.Day == (date + 1))
            return true;

        return false;
    }


    private static class EasterDay {
        public int Day;
        public int Month;

        public EasterDay(int day, int month){
            this.Day = day;
            this.Month = month;
        }

    }

    private static EasterDay getEasterSundayDate(int year) {
        int a = year % 19,
                b = year / 100,
                c = year % 100,
                d = b / 4,
                e = b % 4,
                g = (8 * b + 13) / 25,
                h = (19 * a + b - d - g + 15) % 30,
                j = c / 4,
                k = c % 4,
                m = (a + 11 * h) / 319,
                r = (2 * e + 2 * j - k - h + m + 32) % 7,
                n = (h - m + r + 90) / 25,
                p = (h - m + r + n + 19) % 32;

        return new EasterDay(p,n);
    }


    private static boolean isSchoolHolidays(Calendar calendar)
    {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int date = calendar.get(Calendar.DATE);


        // Summer holidays
        if(year == 2017 && month == 7 && date <= 31)
            return true;

        if(year == 2017 && month == 8 && date <= 31)
            return true;

        if(year == 2017 && month == 9 && date <= 3)
            return true;


        // Eastern holidays
        if(month == 4 && date >= 13 && date <= 18)
            return true;

        // Spring holidays
        if( (month == 2  && date >= 25) || (month == 3  && date <= 5))
            return true;



        // Winter holidays
        if((year == 2016 && month == 12 && date >= 23) || (year == 2017 && month == 1 && date <= 8))
            return true;


        // Autumn holidays
        if((year == 2016 && month == 10 && date >= 28) || (year == 2016 && month == 11 && date <= 1))
            return true;


        return  false;
    }


}
