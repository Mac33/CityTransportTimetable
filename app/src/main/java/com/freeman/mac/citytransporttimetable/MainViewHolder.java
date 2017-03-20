package com.freeman.mac.citytransporttimetable;

import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.freeman.mac.citytransporttimetable.model.HourMapping;
import com.freeman.mac.citytransporttimetable.model.Street;

import org.w3c.dom.Text;

/**
 * Created by Mac on 10. 3. 2017.
 */

/*
public class MainViewHolder extends ParentViewHolder {

    final static String StringEmpty = "";

    TextView streetName = null;
    TextView[] timeTableRow = new TextView[9];

    public MainViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void bind(@NonNull TimetableRow item) {
        this.setTextViews();

        if (item.isStreetView())
        {
            this.streetName.setText(item.Name);

        }else{

            for (int i=0;i<=timeTableRow.length -1 ;i++) {
                this.timeTableRow[i].setText(this.StringEmpty);
            }

            this.timeTableRow[0].setText(Integer.toString(item.HourMapping.Hour));
            for (int i=0;i<=item.HourMapping.getMinutes().size()-1 ;i++) {
                this.timeTableRow[i+1].setText( item.HourMapping.getMinutes().get(i).getText());
            }



        }


    }

    private  void setTextViews()
    {
        if (streetName == null)
        {
            streetName = (TextView) this.itemView.findViewById(R.id.MainStreetName);
        }

        if (timeTableRow[0]== null) {
            timeTableRow[0] = (TextView) this.itemView.findViewById(R.id.Hour);
            for (int i=1;i<=timeTableRow.length -1 ;i++)
            {
                String index = String.format("%02d",  i);
                String fullName = "Minute" + index;
                int id = this.itemView.getResources().getIdentifier(fullName, "id", this.itemView.getContext().getPackageName());
                timeTableRow[i] = (TextView) this.itemView.findViewById(id);

            }
        }
    }

}*/
