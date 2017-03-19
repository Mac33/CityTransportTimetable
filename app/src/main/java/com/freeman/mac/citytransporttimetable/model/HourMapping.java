package com.freeman.mac.citytransporttimetable.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Mac on 7. 3. 2017.
 */

public class HourMapping {

    private List<MinuteMapping> minutes;

    public int Hour = 0;

    public HourMapping(int value) {
        this();
        this.Hour = value;
    }


    public HourMapping() {
        this.minutes = new ArrayList<MinuteMapping>();

    }

    public List<MinuteMapping> getMinutes() {
        return this.minutes;
    }

    public void addMinute(int value, int type){
        this.minutes.add(new MinuteMapping(value,type));
    }


}
