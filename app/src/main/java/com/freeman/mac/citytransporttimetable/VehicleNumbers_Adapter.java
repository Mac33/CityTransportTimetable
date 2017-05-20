package com.freeman.mac.citytransporttimetable;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.freeman.mac.citytransporttimetable.interfaces.ISelectedItemByInteger;
import com.freeman.mac.citytransporttimetable.model.Vehicle;
import com.freeman.mac.citytransporttimetable.model.VehicleCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mac on 2. 4. 2017.
 */

public class VehicleNumbers_Adapter extends RecyclerView.Adapter<VehicleNumbers_ViewHolders> {

    List<VehicleCategory> vehicleCategories =  new ArrayList<>();
    List<Vehicle> vehicles;


    private  ISelectedItemByInteger mSelectItemListener = null;

/*
    VehicleNumbers_Adapter(List<VehicleCategory> data)
    {
        this.vehicleCategories = data;
    }*/

    VehicleNumbers_Adapter(List<Vehicle> data)
    {
        this.vehicles = data;
    }

    @Override
    public VehicleNumbers_ViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_number_card, parent, false);
        VehicleNumbers_ViewHolders holder = new VehicleNumbers_ViewHolders(itemView);

        holder.setSelectVehicle(new ISelectedItemByInteger() {
            @Override
            public void OnSelectedItem(int index) {
                if (mSelectItemListener != null)
                {
                    mSelectItemListener.OnSelectedItem(index);
                }

            }
        });

        return holder;
    }



    public void setSelectItemListener(ISelectedItemByInteger listener)
    {
        this.mSelectItemListener = listener;
    }


    @Override
    public void onBindViewHolder(VehicleNumbers_ViewHolders holder, int position) {
        holder.bind(vehicles.get(position));
    }

    @Override
    public int getItemCount() {
        return vehicles.size();
    }
}
