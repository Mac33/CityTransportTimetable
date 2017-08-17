package com.freeman.mac.citytransporttimetable.SearchVehicleByStreetName;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.freeman.mac.citytransporttimetable.R;
import com.freeman.mac.citytransporttimetable.db.DataAdapter;
import com.freeman.mac.citytransporttimetable.db.VehicleSearchItem;
import com.freeman.mac.citytransporttimetable.model.TimePeriod;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Mac on 4. 8. 2017.
 */

public class VehicleSearchByStreetNameFragment extends Fragment implements  ISearchVehicleByStreetName {


    public static final String Tag = "VehicleSearchByStreetNameFragment";


    public String StreetName;



    RecyclerView mainView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.vehicles_search_result, parent, false);
        mainView = (RecyclerView)view.findViewById(R.id.rv);
        mainView .setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        mainView.setLayoutManager(llm);

        this.search(this.StreetName);
        return view;
    }



    private List<VehicleSearchedByStreetName> getData(String streetName)
    {

        Context urContext = getActivity().getApplicationContext();
        DataAdapter mDbHelper = new DataAdapter(urContext);
        mDbHelper.createDatabase();
        mDbHelper.open();

        Calendar time = Calendar.getInstance();
        int timePeriod = TimePeriod.getDatabaseTimePeriod(time);;

        List<VehicleSearchedByStreetName> data = new ArrayList<>();


        for (VehicleSearchItem item:mDbHelper.getSearchVehiclesByStreetAndTime(time,streetName,timePeriod)) {

            data.add(new VehicleSearchedByStreetName(item));
        }

        return  data;
    }




    public void search(String streetName) {
        List<VehicleSearchedByStreetName> data = getData(streetName);
        VehicleSearchedByStreetName_Adapter  adapter = new VehicleSearchedByStreetName_Adapter(data);
        this.mainView.setAdapter(adapter);
    }


    @Override
    public void setStreetName(String streetName) {
        this.StreetName = streetName;
    }
}
