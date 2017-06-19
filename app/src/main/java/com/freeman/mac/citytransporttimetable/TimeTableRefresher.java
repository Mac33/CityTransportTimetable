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


    public  void  cancel()
    {
        Log.w("citytransporttimetable","Cancel");
        this.timer.cancel();
    }


    private CountDownTimer create()
    {
        timer = new CountDownTimer(20000,1000) {
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



}
