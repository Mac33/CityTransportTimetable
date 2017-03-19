package com.freeman.mac.citytransporttimetable;

import com.bignerdranch.expandablerecyclerview.model.Parent;
import com.freeman.mac.citytransporttimetable.model.HourMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mac on 11. 3. 2017.
 */

public class MainItem implements Parent<StreetItem> {


    public HourMapping HourMapping;
    public String Name;


    private List<StreetItem> mStreeItems = new ArrayList<StreetItem>();

    @Override
    public List<StreetItem> getChildList() {
        return this.mStreeItems;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }


    public  boolean isStreetView()
    {
        return !getChildList().isEmpty();
    }



}
