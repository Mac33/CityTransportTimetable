package com.freeman.mac.citytransporttimetable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.freeman.mac.citytransporttimetable.interfaces.IRefresh;
import com.freeman.mac.citytransporttimetable.model.HourMapping;
import com.freeman.mac.citytransporttimetable.model.TransportTimetables;
import com.freeman.mac.citytransporttimetable.model.Vehicle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mac on 10. 3. 2017.
 */

public class TimetableFragment extends Fragment implements IRefresh {

    private int mTimePerion = 0;

    private TimetableAdapter mAdapter;

    private List<TimetableRow> items;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.timetable, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.RecyclerView);
        setAdapter(view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;

    }


    void setAdapter(View view) {

        if (view != null) {
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.RecyclerView);
            this.items = this.getItems();
            mAdapter = new TimetableAdapter(items);
            recyclerView.swapAdapter(mAdapter, true);
        }


    }

    public void setTimePerion(int timePerion) {
        this.mTimePerion = timePerion;
    }


    public List<TimetableRow> getItems() {

        List<TimetableRow> ret = new ArrayList<TimetableRow>();
        Vehicle vehicle = TransportTimetables.getInstance().getCurrentVehicle();

        for (HourMapping hour : vehicle.getCurrentDirectionTimePeriods().get(this.mTimePerion).getCurrentStreet().getHours()) {
            TimetableRow mainHour = new TimetableRow();
            mainHour.Name = Integer.toString(hour.Hour);
            mainHour.HourMapping = hour;
            ret.add(mainHour);
        }

        return ret;
    }


    @Override
    public void OnRefresh() {

        this.setAdapter(this.getView());
    }


}
