package com.freeman.mac.citytransporttimetable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Mac on 4. 8. 2017.
 */

public class VehicleSearchFragment extends Fragment {


    public static final String Tag = "VehicleSearchFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.vehicles_search_result, parent, false);
        return view;
    }
}
