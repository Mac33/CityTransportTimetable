package com.freeman.mac.citytransporttimetable;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.freeman.mac.citytransporttimetable.StreetNameActivity.StreetName_ViewHolder;
import com.freeman.mac.citytransporttimetable.model.Vehicle;
import com.freeman.mac.citytransporttimetable.model.VehicleCategory;

import java.util.List;
import java.util.Map;

/**
 * Created by Mac on 2. 4. 2017.
 */

public class VehicleNumbers_Adapter extends RecyclerView.Adapter<VehicleNumbers_ViewHolders> {

    List<VehicleCategory> vehicleCategories;



    VehicleNumbers_Adapter(List<VehicleCategory> data)
    {
        this.vehicleCategories = data;
    }

    @Override
    public VehicleNumbers_ViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicles_row, parent, false);
        VehicleNumbers_ViewHolders holder = new VehicleNumbers_ViewHolders(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(VehicleNumbers_ViewHolders holder, int position) {
        holder.bind(vehicleCategories.get(position));
    }

    @Override
    public int getItemCount() {
        return vehicleCategories.size();
    }
}
