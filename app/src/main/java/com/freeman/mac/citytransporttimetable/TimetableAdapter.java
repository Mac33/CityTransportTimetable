package com.freeman.mac.citytransporttimetable;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.freeman.mac.citytransporttimetable.model.MinuteMapping;
import com.freeman.mac.citytransporttimetable.model.TimePeriod;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Mac on 11. 3. 2017.
 */

public class TimetableAdapter extends RecyclerView.Adapter<TimetableViewHolder> {


    private List<TimetableRow> timetableRows;
    private List<TimetableViewHolder> holders = new ArrayList<>();


    public TimetableAdapter(@NonNull List<TimetableRow> recipeList) {
        timetableRows = recipeList;
    }



    @Override
    public TimetableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.timetable_row, parent, false);
        return new TimetableViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(TimetableViewHolder holder, int position) {
        holders.add(holder);
        holder.bind(timetableRows.get(position));
        this.refreshSelectedTimeView();
    }

    @Override
    public int getItemCount() {
        return timetableRows.size();
    }

    private  int getTimePeriod()
    {
        if(!this.holders.isEmpty())
        {
            return this.holders.get(0).currentTimeTableRow.TimePeriod;
        }
        return -1;
    }


    public  void refreshSelectedTimeView() {

        TimetableRow needToRefresh = getColoredTimeTableRow();

        if (needToRefresh != null){
            for (TimetableViewHolder holder:holders) {

                if (holder.currentTimeTableRow == needToRefresh) {
                    holder.selectCurrentTimeView();
                } else {
                    holder.clearSelection();
                }

            }
        }
    }



    private TimetableRow getColoredTimeTableRow() {

        Calendar currentTime = TimePeriod.getCurrentTime();
        Boolean sameTimePeriod = (TimePeriod.getTimePeriodIndex(currentTime) == this.getTimePeriod());
        TimetableRow needToRefresh = null;

        if (sameTimePeriod) {
            int currentHour = currentTime.get(Calendar.HOUR_OF_DAY);
            int currentMinute = currentTime.get(Calendar.MINUTE);

            for (TimetableRow item : timetableRows) {

                for (MinuteMapping minute : item.HourMapping.getMinutes()) {
                    if (needToRefresh == null) {
                        if (item.HourMapping.Hour == currentHour && minute.Minute >= currentMinute)
                            needToRefresh = item;
                        if (item.HourMapping.Hour > currentHour && minute.Minute > -1)
                            needToRefresh = item;
                    }
                }
            }

            if (needToRefresh == null) {
                for (TimetableRow item : timetableRows) {
                    if (needToRefresh == null && !item.HourMapping.getMinutes().isEmpty()) {
                        needToRefresh = item;
                        break;
                    }
                }
            }
        }

        return needToRefresh;
    }
}

