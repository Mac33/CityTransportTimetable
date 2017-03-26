package com.freeman.mac.citytransporttimetable.StreetNameActivity;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.freeman.mac.citytransporttimetable.model.Street;

/**
 * Created by Mac on 26. 3. 2017.
 */

public class StreetName_ItemDecoration extends RecyclerView.ItemDecoration {


    private static final int FirstZoneNumberBusStops = 5;

    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();


        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            StreetName_Adapter adapter  = (StreetName_Adapter )parent.getAdapter();
            int adapterPosition =   parent.getChildAdapterPosition(child);
            //Set bold name for selected street
            StreetName_ViewHolder holder = (StreetName_ViewHolder) parent.getChildViewHolder(child);
            holder.setBoldName(adapterPosition==adapter.CurrentStreetIndex);
            // Set first zone layout
            boolean isFirstZone = adapterPosition >= adapter.CurrentStreetIndex && adapter.CurrentStreetIndex + FirstZoneNumberBusStops >= adapterPosition;
            holder.setZoneLayout(isFirstZone);

        }

    }

}
