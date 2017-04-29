package com.freeman.mac.citytransporttimetable.model;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mac on 7. 3. 2017.
 */

public class MinuteMapping {

    public static final String AdditionalInfromation = StringUtils.Empty;



    public int Minute = 0;



    public List<String> Signs = new ArrayList<>();



    public MinuteMapping() {
        Signs = new ArrayList<>();
    }



    public MinuteMapping(int minute, String type) {
        this.Minute = minute;
        this.Signs.add(type);
    }



    public String getText() {

        String minute = String.format("%02d", this.Minute);

        for ( String item : this.Signs) {
            minute = minute + item;
        }
        return minute;
    }

}
