package com.freeman.mac.citytransporttimetable.model;


/**
 * Created by Mac on 7. 3. 2017.
 */

public class MinuteMapping {

    public int Minute = 0;

    public  int Type = 0;

    public MinuteMapping(int minute, int type)
    {
        this.Minute = minute;
        this.Type = type ;
    }

    public String getText()
    {

        String minute = String.format("%02d", this.Minute);
        if (Type == 1)
        {
            return minute + "z";
        }
        return minute;
    }

}
