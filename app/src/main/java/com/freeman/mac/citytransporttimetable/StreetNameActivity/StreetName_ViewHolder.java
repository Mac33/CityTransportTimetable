package com.freeman.mac.citytransporttimetable.StreetNameActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.freeman.mac.citytransporttimetable.R;
import com.freeman.mac.citytransporttimetable.interfaces.ISelectedItemByInteger;

/**
 * Created by Mac on 18. 3. 2017.
 */

public class StreetName_ViewHolder extends ViewHolder {

    public TextView streetName;
    public ImageView requestStopView;
    public LinearLayout streetsClickLinearLayout;

    private ISelectedItemByInteger mSelectedStreetListener = null;

    public StreetName_ViewHolder(View itemView) {
        super(itemView);
        this.requestStopView = (ImageView) itemView.findViewById(R.id.RequestStopView);
        this.streetName = (TextView) itemView.findViewById(R.id.MainStreetName);

        streetsClickLinearLayout = (LinearLayout)itemView.findViewById(R.id.StreetsLinearLayout);
        streetsClickLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStreetIndex();
            }
        });
    }

    /*Set index for current street */
    private void setStreetIndex() {
        int index = this.getAdapterPosition();
        this.mSelectedStreetListener.OnSelectedItem(index);
    }


    public void setSelectedStreetListener(ISelectedItemByInteger listener) {
        this.mSelectedStreetListener = listener;
    }


    public void bind(String name, boolean requestStop) {
       this.streetName.setText(name);
        if(requestStop)
        {
            this.requestStopView.setVisibility(View.VISIBLE);
        }else
        {
            this.requestStopView.setVisibility(View.GONE);
        }

    }


    public void setBoldName(boolean isSelected) {
        if (isSelected) {
            this.streetName.setTypeface(null, Typeface.BOLD);
        } else {
            this.streetName.setTypeface(null, Typeface.NORMAL);
        }
    }

    public void setZoneLayout(boolean isFirsZone) {
        if (isFirsZone) {
          this.itemView.setBackgroundColor(Color.YELLOW);
        } else {
           this.itemView.setBackgroundColor(Color.WHITE);
        }
    }


}
