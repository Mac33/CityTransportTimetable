package com.freeman.mac.citytransporttimetable.StreetNameActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import com.freeman.mac.citytransporttimetable.R;
import com.freeman.mac.citytransporttimetable.interfaces.ISelectedItemByInteger;

/**
 * Created by Mac on 18. 3. 2017.
 */

public class StreetName_ViewHolder extends ViewHolder {

    public TextView streetName;
    private ISelectedItemByInteger mSelectedStreetListener = null;

    public StreetName_ViewHolder(View itemView) {
        super(itemView);
        this.streetName = (TextView) itemView.findViewById(R.id.MainStreetName);
        this.streetName.setOnClickListener(new View.OnClickListener() {
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


    public void bind(String name) {
       this.streetName.setText(name);

    }


    void setBoldName(boolean isSelected) {
        if (isSelected) {
            this.streetName.setTypeface(null, Typeface.BOLD);
        } else {
            this.streetName.setTypeface(null, Typeface.NORMAL);
        }
    }

    void setZoneLayout(boolean isFirsZone) {
        if (isFirsZone) {
            this.itemView.setBackgroundColor(Color.YELLOW);
        } else {
            this.itemView.setBackgroundColor(Color.WHITE);
        }
    }


}
