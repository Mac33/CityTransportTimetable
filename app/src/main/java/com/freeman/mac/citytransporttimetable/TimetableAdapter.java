package com.freeman.mac.citytransporttimetable;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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



    public  void refreshSelectedTimeView()
    {

        Calendar currentTime = Calendar.getInstance();
        int currentHour = currentTime.get(Calendar.HOUR_OF_DAY);

        for (int i = 0;i < holders.size()-1; i++ )
        {
            if(holders.get(i).currentTimeTableRow.HourMapping.Hour == currentHour)
            {
                if (!holders.get(i).refreshCurrentTimeView(false))
                {
                    if( holders.size() > i+1)
                        holders.get(i+1).refreshCurrentTimeView(true);
                }
            }
        }

        /*
        for (TimetableViewHolder item:holders)
        {
            if(item.selectFirstTimeView())
               return;
        }*/

    }

}

