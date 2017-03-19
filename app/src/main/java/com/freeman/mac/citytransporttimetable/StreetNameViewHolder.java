package com.freeman.mac.citytransporttimetable;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;

/**
 * Created by Mac on 11. 3. 2017.
 */



public class StreetNameViewHolder extends ChildViewHolder {

    private  TextView streetName;

    public StreetNameViewHolder (@NonNull View itemView) {
        super(itemView);

        streetName = (TextView)itemView.findViewById(R.id.StreetName);

    }


    public void bind(@NonNull StreetItem item) {
        this.streetName.setText(item.Name);
    }



}
