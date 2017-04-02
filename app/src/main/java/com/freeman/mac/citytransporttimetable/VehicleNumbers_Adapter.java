package com.freeman.mac.citytransporttimetable;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.freeman.mac.citytransporttimetable.StreetNameActivity.StreetName_ViewHolder;

/**
 * Created by Mac on 2. 4. 2017.
 */

public class VehicleNumbers_Adapter extends RecyclerView.Adapter<VehicleNumbers_ViewHolders> {

    @Override
    public VehicleNumbers_ViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicles_row, parent, false);
        VehicleNumbers_ViewHolders holder = new VehicleNumbers_ViewHolders(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(VehicleNumbers_ViewHolders holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
