package com.freeman.mac.citytransporttimetable;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.freeman.mac.citytransporttimetable.components.TimeView;
import com.freeman.mac.citytransporttimetable.model.TimePeriod;

import java.util.Calendar;

/**
 * Created by Mac on 20. 3. 2017.
 */

public class TimetableViewHolder extends RecyclerView.ViewHolder {


    public TimetableRow currentTimeTableRow;
    TextView hourView;
    TimeView[] timeTableRow = new TimeView[7];
    TableRow row;

    public TimetableViewHolder(View itemView) {
        super(itemView);
    }


    public void bind(@NonNull TimetableRow item) {
        this.setTextViews();
        this.currentTimeTableRow = item;
        int hour = this.currentTimeTableRow.HourMapping.Hour;

        this.hourView.setText(Integer.toString(hour));

        for (TimeView timeView:this.timeTableRow) {
            timeView.setVisibility(View.GONE);
        }

        for (int i = 0; i <= item.HourMapping.getMinutes().size() - 1; i++) {
            this.timeTableRow[i].setTime(item.HourMapping.getMinutes().get(i));
            this.timeTableRow[i].setVisibility(View.VISIBLE);
        }

        if ((hour & 1) == 0) {
            row.setBackgroundColor(Color.argb(255, 235, 235, 235));
        } else {
            row.setBackgroundResource(0);
        }

        Log.v("citytransporttimetable", "Bind TimePeriod: " + Integer.toString(item.TimePeriod) + " Hour: " + Integer.toString(item.HourMapping.Hour));

    }

    private void setTextViews() {
        hourView = (TextView) this.itemView.findViewById(R.id.Hour);
        for (int i = 0; i <= timeTableRow.length - 1; i++) {
            String index = String.format("%02d", i + 1);
            String fullName = "Minute" + index;
            int id = this.itemView.getResources().getIdentifier(fullName, "id", this.itemView.getContext().getPackageName());
            timeTableRow[i] = (TimeView) this.itemView.findViewById(id);

        }
        row = (TableRow) this.itemView.findViewById(R.id.Row);
    }



    public  void  clearSelection() {
        for (TimeView timeView : timeTableRow) {
            timeView.disableBorder();
        }
    }


    public boolean selectCurrentTimeView() {


        Calendar currentTime = TimePeriod.getCurrentTime();
        Boolean sameTimePeriod = (TimePeriod.getTimePeriodIndex(currentTime) == currentTimeTableRow.TimePeriod);

        int currentHour = currentTime.get(Calendar.HOUR_OF_DAY);
        int currentMinute = currentTime.get(Calendar.MINUTE);

        TimeView selectedTimeView = null;
        for (TimeView timeView : timeTableRow) {
            timeView.disableBorder();

            if (selectedTimeView == null) {
                if (currentTimeTableRow.HourMapping.Hour == currentHour) {
                    if (timeView.Minute >= currentMinute) {
                        selectedTimeView = timeView;
                    }
                } else {
                    if (timeView.Minute > -1) {
                        selectedTimeView = timeView;
                    }
                }
            }

        }

        if (sameTimePeriod && selectedTimeView != null) {
            selectedTimeView.allowBorder();
            return true;
        }

        return false;
    }



}
