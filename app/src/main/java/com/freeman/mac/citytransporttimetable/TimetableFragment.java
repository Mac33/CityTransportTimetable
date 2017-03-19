package com.freeman.mac.citytransporttimetable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.freeman.mac.citytransporttimetable.interfaces.IRefresh;
import com.freeman.mac.citytransporttimetable.interfaces.ISelectedItem;
import com.freeman.mac.citytransporttimetable.model.HourMapping;
import com.freeman.mac.citytransporttimetable.model.Street;
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

    private ISelectedItem mSelectedListener = null;

    private List<MainItem> items;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.timetable, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        setAdapter(view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;

    }


    void setAdapter(View view) {

        if (view!=null)
        {
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
            this.items = this.getItems();

            mAdapter = new TimetableAdapter(getActivity(), items);
            mAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
                @Override
                public void onParentExpanded(int parentPosition) {
                }

                @Override
                public void onParentCollapsed(int parentPosition) {
                }

                @Override
                public void onFlatParentCollapsed(int flatPosition) {
                    mSelectedListener.OnSelectedItem(flatPosition);
                }
            });

            recyclerView.swapAdapter(mAdapter,true);
        }




    }

    public void setTimePerion(int timePerion) {
        this.mTimePerion = timePerion;
    }


    public List<MainItem> getItems() {

        List<MainItem> ret = new ArrayList<MainItem>();
        Vehicle vehicle = TransportTimetables.getInstance().getCurrentVehicle();
        if (vehicle.hasTimePeriod(this.mTimePerion)) {
            MainItem mainStreet = new MainItem();
            if (vehicle.getTimePeriod(this.mTimePerion).HasCurrentDiretionStreet())
            {
                mainStreet.Name = vehicle.getTimePeriod(this.mTimePerion).getCureentStreet().Name;
                for (Street item : vehicle.getTimePeriod(this.mTimePerion).getCurrentDirectionStreets()) {
                    StreetItem streetItem = new StreetItem();
                    streetItem.Name = item.Name;
                    mainStreet.getChildList().add(streetItem);
                }
                ret.add(mainStreet);

                for (HourMapping hour : vehicle.getTimePeriod(this.mTimePerion).getCureentStreet().getHours()) {
                    MainItem mainHour = new MainItem();
                    mainHour.Name = Integer.toString(hour.Hour);
                    mainHour.HourMapping = hour;
                    ret.add(mainHour);
                }

            }
        }

        return ret;
    }


    public void setSelectedListener(ISelectedItem listener) {

        this.mSelectedListener = listener;
    }


    @Override
    public void OnRefresh() {

        this.setAdapter(this.getView());
    }


}
