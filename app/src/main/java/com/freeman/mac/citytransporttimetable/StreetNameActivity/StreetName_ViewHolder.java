package com.freeman.mac.citytransporttimetable.StreetNameActivity;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;
import  com.freeman.mac.citytransporttimetable.R;
import com.freeman.mac.citytransporttimetable.interfaces.ISelectedItem;

/**
 * Created by Mac on 18. 3. 2017.
 */

public class StreetName_ViewHolder extends ViewHolder  {

    private ISelectedItem mSelectedStreetListener = null;

    public TextView streetName;

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
    /*Nastavenie indexu aktualne vybratej ulice
    * */
    private void setStreetIndex()
    {
        int index = this.getAdapterPosition();
        this.mSelectedStreetListener.OnSelectedItem(index);
    }


    public  void  setSelectedStreetListener(ISelectedItem listener)
    {
        this.mSelectedStreetListener = listener;
    }


    public void bind(String name)
    {
        this.streetName.setText(name);
    }


}
