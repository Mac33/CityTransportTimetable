package com.freeman.mac.citytransporttimetable;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.freeman.mac.citytransporttimetable.interfaces.ISelectedItemByInteger;
import com.freeman.mac.citytransporttimetable.model.TransportTimetables;
import com.freeman.mac.citytransporttimetable.model.Vehicle;

import java.util.List;

/**
 * Created by Mac on 2. 8. 2017.
 */

public class VehicleNumbersFragment extends Fragment {


    public static final String Tag = "VehicleNumbersFragment";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.vehicles_view, parent, false);


        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.vehicle_numbers_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this.getActivity() ,3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(3,this.dpToPx(1)));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        VehicleNumbers_Adapter mAdapter = new VehicleNumbers_Adapter(this.getVehicles());

        mAdapter.setSelectItemListener(new ISelectedItemByInteger() {
            @Override
            public void OnSelectedItem(int index) {
                startTimeTableActivity(index);
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        return  view;
    }

    private List<Vehicle> getVehicles()
    {
        return TransportTimetables.getInstance().getVehicles();
    }

    private  void startTimeTableActivity(int vehicleNumber)
    {
        TransportTimetables.getInstance().setCurrentVehicle(vehicleNumber);
        Intent intent = TimetableActivity.createInstance(this.getActivity());
        startActivity(intent);
    }


    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


}
