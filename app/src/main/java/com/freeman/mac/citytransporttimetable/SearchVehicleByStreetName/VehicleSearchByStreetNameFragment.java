package com.freeman.mac.citytransporttimetable.SearchVehicleByStreetName;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.freeman.mac.citytransporttimetable.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mac on 4. 8. 2017.
 */

public class VehicleSearchByStreetNameFragment extends Fragment {


    public static final String Tag = "VehicleSearchByStreetNameFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.vehicles_search_result, parent, false);
        RecyclerView rv = (RecyclerView)view.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        rv.setLayoutManager(llm);

        List<VehicleSearchedByStreetName> data = getData();
        VehicleSearchedByStreetName_Adapter  adapter = new VehicleSearchedByStreetName_Adapter(data);
        rv.setAdapter(adapter);
        return view;
    }


    private List<VehicleSearchedByStreetName> getData()
    {
        List<VehicleSearchedByStreetName> data = new ArrayList<>();

        data.add(new VehicleSearchedByStreetName());
        data.add(new VehicleSearchedByStreetName());
        data.add(new VehicleSearchedByStreetName());
        data.add(new VehicleSearchedByStreetName());
        data.add(new VehicleSearchedByStreetName());
        data.add(new VehicleSearchedByStreetName());
        data.add(new VehicleSearchedByStreetName());
        data.add(new VehicleSearchedByStreetName());
        data.add(new VehicleSearchedByStreetName());
        data.add(new VehicleSearchedByStreetName());

        return  data;
    }
}
