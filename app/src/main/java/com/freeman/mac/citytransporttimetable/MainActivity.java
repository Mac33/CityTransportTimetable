package com.freeman.mac.citytransporttimetable;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.freeman.mac.citytransporttimetable.StreetNameActivity.StreetNamesActivity;
import com.freeman.mac.citytransporttimetable.interfaces.ISelectedItemByInteger;
import com.freeman.mac.citytransporttimetable.model.StringUtils;
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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.initVehicles();
        setContentView(R.layout.activity_main);
        this.initToolbar();

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.vehicle_numbers_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        VehicleNumbers_Adapter mAdapter = new VehicleNumbers_Adapter(this.getVehicleCategories());
        mAdapter.setSelectItemListener(new ISelectedItemByInteger() {
            @Override
            public void OnSelectedItem(int index) {
                startTimeTableActivity(index);
            }
        });
        mRecyclerView.setAdapter(mAdapter);

    }

    void initToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(StringUtils.Empty);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private  void startTimeTableActivity(int vehicleNumber)
    {
        TransportTimetables.getInstance().setCurrentVehicle(vehicleNumber);
        Intent intent = TimetableActivity.createInstance(MainActivity.this);
        startActivity(intent);
    }

    public void initVehicles()
    {
        TransportTimetables.getInstance().getVehicles().clear();
        this.addVehicle(1, Vehicle.eVehicleType.Trolleybus,R.mipmap.number_01_blue,R.drawable.number_01_toolbar ,R.raw.vechicle_04);
        this.addVehicle(3,Vehicle.eVehicleType.Trolleybus,R.mipmap.number_03_blue ,R.drawable.number_03_toolbar ,R.raw.vehicle_03);
        this.addVehicle(4,Vehicle.eVehicleType.Trolleybus,R.mipmap.number_04_blue ,R.drawable.number_04_toolbar ,R.raw.vechicle_04);
        this.addVehicle(5, Vehicle.eVehicleType.Trolleybus,R.mipmap.number_05_blue,R.drawable.number_05_toolbar  ,R.raw.vechicle_04);
        this.addVehicle(6,Vehicle.eVehicleType.Trolleybus,R.mipmap.number_06_blue ,R.drawable.number_06_toolbar ,R.raw.vehicle_06);
        this.addVehicle(7,Vehicle.eVehicleType.Trolleybus,R.mipmap.number_07_blue ,R.drawable.number_07_toolbar ,R.raw.vechicle_04);
        this.addVehicle(14,Vehicle.eVehicleType.Trolleybus,R.mipmap.number_14_blue ,R.drawable.number_14_toolbar ,R.raw.vehicle_14);
        this.addVehicle(16,Vehicle.eVehicleType.Trolleybus,R.mipmap.number_16_blue,R.drawable.number_16_toolbar ,R.raw.vechicle_04);

        this.addVehicle(20,Vehicle.eVehicleType.CityBus,R.mipmap.number_14_blue,R.drawable.number_01_toolbar  ,R.raw.vechicle_04);
        this.addVehicle(21,Vehicle.eVehicleType.CityBus,R.mipmap.number_14_blue ,R.drawable.number_01_toolbar ,R.raw.vechicle_04);
        this.addVehicle(22,Vehicle.eVehicleType.CityBus,R.mipmap.number_14_blue ,R.drawable.number_01_toolbar ,R.raw.vechicle_04);
        this.addVehicle(24,Vehicle.eVehicleType.CityBus,R.mipmap.number_14_blue,R.drawable.number_01_toolbar  ,R.raw.vechicle_04);
        this.addVehicle(26,Vehicle.eVehicleType.CityBus,R.mipmap.number_14_blue,R.drawable.number_01_toolbar  ,R.raw.vechicle_04);
        this.addVehicle(27,Vehicle.eVehicleType.CityBus,R.mipmap.number_14_blue,R.drawable.number_01_toolbar  ,R.raw.vechicle_04);
        this.addVehicle(29,Vehicle.eVehicleType.CityBus,R.mipmap.number_14_blue,R.drawable.number_01_toolbar  ,R.raw.vechicle_04);
        this.addVehicle(30,Vehicle.eVehicleType.CityBus,R.mipmap.number_14_blue,R.drawable.number_01_toolbar  ,R.raw.vechicle_04);
        this.addVehicle(31,Vehicle.eVehicleType.CityBus,R.mipmap.number_14_blue,R.drawable.number_01_toolbar  ,R.raw.vechicle_04);

        this.addVehicle(25,Vehicle.eVehicleType.BusForSelectedPassenger,R.mipmap.number_14_blue ,R.drawable.number_01_toolbar ,R.raw.vechicle_04);
        this.addVehicle(35,Vehicle.eVehicleType.BusForSelectedPassenger,R.mipmap.number_14_blue ,R.drawable.number_01_toolbar ,R.raw.vechicle_04);

        this.addVehicle(50,Vehicle.eVehicleType.NightBus,R.mipmap.number_14_blue ,R.drawable.number_01_toolbar ,R.raw.vechicle_04);

    }



    private void addVehicle(int number,
                            Vehicle.eVehicleType type,
                            int iconId,
                            int iconToolBarId,
                            int dataId  ) {
        Vehicle vehicle = new Vehicle(number, type, iconId, iconToolBarId);


        switch (number ) {
            case 4:
                List<String> data = this.loadData(dataId);
                vehicle.load(data);
                break;
            case 6:
            case 3:
            case 14:
                String jsonData =  loadDataJson(dataId);
                vehicle = Vehicle.Deserialize(jsonData);
                vehicle.Type = type;
                vehicle.IconResId = iconId;
                vehicle.IconToolBarId = iconToolBarId;
                break;
            default:
                vehicle.generate();
                break;


        }

        TransportTimetables.getInstance().getVehicles().add(vehicle);
    }

    public String loadDataJson(int id)
    {
        Context cx = this.getApplicationContext();
        InputStream inputStream = cx.getResources().openRawResource(id);
        InputStreamReader inputReader = new InputStreamReader(inputStream);
        BufferedReader buffReader = new BufferedReader(inputReader );

        String line;
        StringBuilder sb = new StringBuilder();
        try {
            while (( line = buffReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {

        }
        return  sb.toString() ;
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
        item.Description= "Trolejbusy";
        item.Vehicles = TransportTimetables.getInstance().getVehicles(Vehicle.eVehicleType.Trolleybus);
        ret.add(item);

        item = new VehicleCategory();
        item.Type = Vehicle.eVehicleType.CityBus;
        item.Description= "Autobusy";
        item.Vehicles = TransportTimetables.getInstance().getVehicles(Vehicle.eVehicleType.CityBus);
        ret.add(item);

        item = new VehicleCategory();
        item.Type = Vehicle.eVehicleType.BusForSelectedPassenger;
        item.Description= "Autobusy pre vymedzený okruh cestujúcich";
        item.Vehicles = TransportTimetables.getInstance().getVehicles(Vehicle.eVehicleType.BusForSelectedPassenger);
        ret.add(item);

        item = new VehicleCategory();
        item.Type = Vehicle.eVehicleType.NightBus;
        item.Description= "Nočné linky";
        item.Vehicles = TransportTimetables.getInstance().getVehicles(Vehicle.eVehicleType.NightBus);
        ret.add(item);

        return  ret;

    }




}
