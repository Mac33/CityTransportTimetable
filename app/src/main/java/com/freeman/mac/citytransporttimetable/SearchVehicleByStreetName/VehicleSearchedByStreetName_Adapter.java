package com.freeman.mac.citytransporttimetable.SearchVehicleByStreetName;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.freeman.mac.citytransporttimetable.R;

import java.util.List;

/**
 * Created by Mac on 8. 8. 2017.
 */

public class VehicleSearchedByStreetName_Adapter extends RecyclerView.Adapter<VehicleSearchedByStreetName_ViewHolder>{

    private List<VehicleSearchedByStreetName> dataSet;

    @Override
    public VehicleSearchedByStreetName_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_search_item, parent, false);
        VehicleSearchedByStreetName_ViewHolder myViewHolder = new VehicleSearchedByStreetName_ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(VehicleSearchedByStreetName_ViewHolder holder, int position) {
        holder.bind(dataSet.get(position));
    }

    public VehicleSearchedByStreetName_Adapter(List<VehicleSearchedByStreetName> data) {
        this.dataSet = data;
    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
