package com.freeman.mac.citytransporttimetable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.freeman.mac.citytransporttimetable.components.ObservableScrollView;
import com.freeman.mac.citytransporttimetable.interfaces.*;
import com.freeman.mac.citytransporttimetable.model.HourMapping;
import com.freeman.mac.citytransporttimetable.model.TransportTimetables;
import com.freeman.mac.citytransporttimetable.model.Vehicle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mac on 10. 3. 2017.
 */

    public class TimetableFragment extends Fragment implements IRefresh, IScrollViewListener {

    private ObservableScrollView scrollView;
    private int mTimePeriod = 0;
    private TimetableAdapter mAdapter;
    private List<TimetableRow> items;
    private IChangeScrollVerticalPosition ScrollVerticalPositionListener;
    public int ScrollToPostition = 0;




    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);

    }

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
        scrollView = (ObservableScrollView) view.findViewById(R.id.TimeTableScrollView);
        scrollView.setScrollViewListener(this);
        setAdapter(view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.Scroll();
        return view;

    }

    public void Scroll()
    {
        if (this.scrollView != null)
        {
            this.scrollView.scrollTo(0,this.ScrollToPostition);
        }

    }

    void setAdapter(View view) {

        if (view != null) {
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.RecyclerView);
            this.items = this.getItems();
            mAdapter = new TimetableAdapter(items);
            recyclerView.swapAdapter(mAdapter, true);
        }
    }

    public int getTimePeriod() {
        return this.mTimePeriod;
    }

    public void setTimePeriod(int timePeriod) {
        this.mTimePeriod = timePeriod;
    }

    public List<TimetableRow> getItems() {

        List<TimetableRow> ret = new ArrayList<TimetableRow>();

        Vehicle vehicle = TransportTimetables.getInstance().getCurrentVehicle();
        if (vehicle.getCurrentStreet().getTimePeriods().size() > this.mTimePeriod) {
            for (HourMapping hour : vehicle.getCurrentStreet().getTimePeriods().get(this.mTimePeriod).Hours) {
                TimetableRow mainHour = new TimetableRow();
                mainHour.Name = Integer.toString(hour.Hour);
                mainHour.HourMapping = hour;
                ret.add(mainHour);
            }

        }
        return ret;
    }



    @Override
    public void OnRefresh() {
        this.setAdapter(this.getView());
    }



    @Override
    public void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (this.ScrollVerticalPositionListener!=null)
            this.ScrollVerticalPositionListener.onChangeScrollVerticalPosition(y);
        Log.v("ScrollView", x + " " + y);
    }



    public  void setScrollVerticalPositionListener(IChangeScrollVerticalPosition listener)
    {
        this.ScrollVerticalPositionListener = listener;
    }



    public  void  setVerticalScrollPosition(int value)
    {
        this.ScrollToPostition = value;
        this.Scroll();
    }
}
