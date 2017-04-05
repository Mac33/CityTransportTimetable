package com.freeman.mac.citytransporttimetable;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.freeman.mac.citytransporttimetable.StreetNameActivity.StreetNamesActivity;
import com.freeman.mac.citytransporttimetable.interfaces.ISelectedItemByInteger;
import com.freeman.mac.citytransporttimetable.model.TransportTimetables;
import com.freeman.mac.citytransporttimetable.model.Vehicle;
import com.freeman.mac.citytransporttimetable.model.VehicleCategory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private VehicleNumbers_Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initVehicles();
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.vehicle_numbers_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new VehicleNumbers_Adapter(this.getVehicleCategories());
        mAdapter.setSelectItemListener(new ISelectedItemByInteger() {
            @Override
            public void OnSelectedItem(int index) {
                startTimeTableActivity(index);
            }
        });
        mRecyclerView.setAdapter(mAdapter);

    }

    private  void startTimeTableActivity(int vehicleNumber)
    {
        Intent intent = TimetableActivity.createInstance(MainActivity.this);
        startActivityForResult(intent, StreetNamesActivity.STREET_POSITION_REQUEST_CODE);

    }

    public void initVehicles()
    {
        TransportTimetables.getInstance().getVehicles().clear();
        this.addVehicle(4, Vehicle.eVehicleType.Trolleybus,R.mipmap.number_04_blue ,R.raw.vechicle_04);
        this.addVehicle(6,Vehicle.eVehicleType.Trolleybus,R.mipmap.number_06_blue ,R.raw.vechicle_04);
        this.addVehicle(14,Vehicle.eVehicleType.Trolleybus,R.mipmap.number_14_blue ,R.raw.vechicle_04);

    }



    private void addVehicle(int number,Vehicle.eVehicleType type, int iconId, int dataId )
    {
        Vehicle vehicle = new Vehicle(number, type,iconId);
        List<String> data = this.loadData(dataId);
        vehicle.load(data);
        TransportTimetables.getInstance().getVehicles().add(vehicle);
    }


    public List<String> loadData(int id)
    {
        Context cx = this.getApplicationContext();
        InputStream inputStream = cx.getResources().openRawResource(id);
        InputStreamReader inputReader = new InputStreamReader(inputStream);
        BufferedReader buffReader = new BufferedReader(inputReader );
        String line;
        ArrayList<String> text = new ArrayList<String> ();
        try {
            while (( line = buffReader .readLine()) != null) {
                text.add(line);
            }
        } catch (IOException e) {

        }
        return  text;

    }


    private List<VehicleCategory> getVehicleCategories()
    {
        List<VehicleCategory> ret = new ArrayList<VehicleCategory>();
        VehicleCategory item = new VehicleCategory();
        item.Type = Vehicle.eVehicleType.Trolleybus;
        item.Description= "Trolejbus";
        item.Vehicles = TransportTimetables.getInstance().getVehicles(Vehicle.eVehicleType.Trolleybus);
        ret.add(item);
        return  ret;

    }




}
