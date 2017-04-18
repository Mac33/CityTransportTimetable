package com.freeman.mac.citytransporttimetable;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Mac on 20. 3. 2017.
 */

class TimetableViewHolder extends RecyclerView.ViewHolder {


    final static String StringEmpty = "";

    TextView[] timeTableRow = new TextView[8];


    public TimetableViewHolder(View itemView) {
        super(itemView);
    }


    public void bind(@NonNull TimetableRow item) {
        this.setTextViews();
        for (int i = 0; i <= timeTableRow.length - 1; i++) {
            this.timeTableRow[i].setText(this.StringEmpty);
        }

        this.timeTableRow[0].setText(Integer.toString(item.HourMapping.Hour));
        for (int i = 0; i <= item.HourMapping.getMinutes().size() - 1; i++) {
            this.timeTableRow[i + 1].setText(item.HourMapping.getMinutes().get(i).getText());
        }


    }

    private void setTextViews() {
        if (timeTableRow[0] == null) {
            timeTableRow[0] = (TextView) this.itemView.findViewById(R.id.Hour);
            for (int i = 1; i <= timeTableRow.length - 1; i++) {
                String index = String.format("%02d", i);
                String fullName = "Minute" + index;
                int id = this.itemView.getResources().getIdentifier(fullName, "id", this.itemView.getContext().getPackageName());
                timeTableRow[i] = (TextView) this.itemView.findViewById(id);

            }
        }
    }
}
