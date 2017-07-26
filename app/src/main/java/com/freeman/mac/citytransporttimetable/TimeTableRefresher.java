package com.freeman.mac.citytransporttimetable;

import android.os.CountDownTimer;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Mac on 21. 5. 2017.
 */

public class TimeTableRefresher {

    CountDownTimer timer;
    List<TimetableFragment> items;

    TimeTableRefresher()
    {
        this.items = new ArrayList<>();
        timer = create();
        timer.start();
    }

    public void addTimeTable(TimetableFragment item)
    {
        if(item!=null)
        {
           items.add(item);
        }

    }


    public void cancel()
    {
        Log.w("citytransporttimetable","Cancel");
        this.timer.cancel();
    }


    private CountDownTimer create()
    {
        int miliSec = this.getInitialTime();
        timer = new CountDownTimer(miliSec,1000) {
            @Override
            public void onTick(long value) {
                Log.w("citytransporttimetable","Sconds remaining: " + value / 1000 );
            }

            @Override
            public void onFinish() {
                for (TimetableFragment item:items) {
                   item.refreshSelectedTimeView();
                }
                timer = create();
                timer.start();
            }
        };
        return timer;
    }



    private int getInitialTime()
    {
        Calendar currentTime = Calendar.getInstance();
        int sec = currentTime.get(Calendar.SECOND);
        sec = 60 - sec;
        if (sec==0)
            sec = 60;

        return sec*1000;
    }



}
