package com.freeman.mac.citytransporttimetable;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.freeman.mac.citytransporttimetable.components.TimeView;

/**
 * Created by Mac on 20. 3. 2017.
 */

public class TimetableViewHolder extends RecyclerView.ViewHolder {


    final static String StringEmpty = "";

    TextView hourView;
    TimeView[] timeTableRow = new TimeView[7];
    TableRow row;

    public TimetableViewHolder(View itemView) {
        super(itemView);
    }


    public void bind(@NonNull TimetableRow item) {
        this.setTextViews();


        this.hourView.setText(Integer.toString(item.HourMapping.Hour));
        for (int i = 0; i <= item.HourMapping.getMinutes().size() - 1; i++) {
            this.timeTableRow[i + 1].setTime(item.HourMapping.getMinutes().get(i));
        }

        if ((item.HourMapping.Hour & 1)==0)
        {
          row.setBackgroundColor(Color.argb(255,235,235,235));
        }
        else
        {
          row.setBackgroundResource(0);
        }


    }

    private void setTextViews() {
        if (hourView == null) {
            hourView = (TextView) this.itemView.findViewById(R.id.Hour);
            for (int i = 1; i <= timeTableRow.length - 1; i++) {
                String index = String.format("%02d", i);
                String fullName = "Minute" + index;
                int id = this.itemView.getResources().getIdentifier(fullName, "id", this.itemView.getContext().getPackageName());
                timeTableRow[i] = (TimeView) this.itemView.findViewById(id);

            }
        }
        row = (TableRow) this.itemView.findViewById(R.id.Row);
    }
}
