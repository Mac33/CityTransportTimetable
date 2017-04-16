package com.freeman.mac.citytransporttimetable.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mac on 7. 3. 2017.
 */

public class HourMapping {

    private List<MinuteMapping> Minutes;

    public int Hour = 0;

    public HourMapping(int value) {
        this();
        this.Hour = value;
    }


    public HourMapping() {
        this.Minutes = new ArrayList<MinuteMapping>();

    }

    public List<MinuteMapping> getMinutes() {
        return this.Minutes;
    }

    public void addMinute(int value, int type){
        this.Minutes.add(new MinuteMapping(value,type));
    }


}
