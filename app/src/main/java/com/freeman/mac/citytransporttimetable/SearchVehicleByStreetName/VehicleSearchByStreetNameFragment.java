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
import com.freeman.mac.citytransporttimetable.SignsRowItem;
import com.freeman.mac.citytransporttimetable.components.AdditionalInfoView;
import com.freeman.mac.citytransporttimetable.db.DataAdapter;
import com.freeman.mac.citytransporttimetable.db.VehicleSearchItem;
import com.freeman.mac.citytransporttimetable.model.TimePeriod;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mac on 4. 8. 2017.
 */

public class VehicleSearchByStreetNameFragment extends Fragment implements ISearchVehicleByStreetName {


    public static final String Tag = "VehicleSearchByStreetNameFragment";


    public String StreetName;
    private RecyclerView mainView;
    private AdditionalInfoView additionalInfoView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.vehicles_search_result, parent, false);
        mainView = (RecyclerView) view.findViewById(R.id.rv);
        mainView.setHasFixedSize(true);
        mainView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        this.additionalInfoView = (AdditionalInfoView ) view.findViewById(R.id.view_vehicle_descriptions);
        this.search();

        return view;
    }


    private List<VehicleSearchedByStreetName> getData(String streetName) {

        Context urContext = getActivity().getApplicationContext();
        DataAdapter mDbHelper = new DataAdapter(urContext);
        mDbHelper.createDatabase();
        mDbHelper.open();

        Calendar time = Calendar.getInstance();
        int timePeriod = TimePeriod.getDatabaseTimePeriod(time);


        List<VehicleSearchedByStreetName> data = new ArrayList<>();


        for (VehicleSearchItem item : mDbHelper.getSearchVehiclesByStreetAndTime(time, streetName, timePeriod)) {

            data.add(new VehicleSearchedByStreetName(item));
        }

        return data;
    }


    private void search() {
        this.search(this.StreetName);
    }


    public void search(String streetName) {
        List<VehicleSearchedByStreetName> data = getData(streetName);
        VehicleSearchedByStreetName_Adapter adapter = new VehicleSearchedByStreetName_Adapter(data);
        this.mainView.setAdapter(adapter);
        this.setStreetsAdditionalInfo(data);
    }


    private void setStreetsAdditionalInfo(List<VehicleSearchedByStreetName> data) {
        HashMap<String, SignsRowItem> info = new HashMap<>();

        boolean showLowVehicleSign = false;
        for (VehicleSearchedByStreetName vehicleItem : data) {
            for (VehicleSignSearchedByStreetName sign : vehicleItem.Signs) {
                if(!sign.Sign.equals("n")) {
                    if (!info.containsKey(sign.Sign)) {

                        SignsRowItem signsRowItem = new SignsRowItem(sign.Sign, sign.Text);
                        info.put(sign.Sign, signsRowItem);
                    } else {
                        info.get(sign.Sign).Description = info.get(sign.Sign).Description + "\n" + sign.Text;
                    }
                }else {
                    showLowVehicleSign = true;
                }
            }
        }

        this.additionalInfoView.setData(info.values().toArray(new SignsRowItem[info.size()]), showLowVehicleSign);
    }


    @Override
    public void setStreetName(String streetName) {
        this.StreetName = streetName;

        if (this.isVisible())
            search();
    }
}
