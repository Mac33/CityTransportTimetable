package com.freeman.mac.citytransporttimetable;


import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;

import com.freeman.mac.citytransporttimetable.database_model.VehicleDatabase;
import com.freeman.mac.citytransporttimetable.interfaces.ISelectedItemByInteger;
import com.freeman.mac.citytransporttimetable.model.StringUtils;
import com.freeman.mac.citytransporttimetable.model.TransportTimetables;
import com.freeman.mac.citytransporttimetable.model.Vehicle;
import com.freeman.mac.citytransporttimetable.model.VehicleCategory;


import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {



    private  VehicleDatabase Database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ///this.initDb();
        this.initVehicles();
        setContentView(R.layout.activity_main);
        this.initToolbar();

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.vehicle_numbers_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(3,this.dpToPx(1)));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        VehicleNumbers_Adapter mAdapter = new VehicleNumbers_Adapter(this.getVehicles());
        mAdapter.setSelectItemListener(new ISelectedItemByInteger() {
            @Override
            public void OnSelectedItem(int index) {
                startTimeTableActivity(index);
            }
        });
        mRecyclerView.setAdapter(mAdapter);


    }


    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
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
        this.addVehicle(1, Vehicle.eVehicleType.Trolleybus,R.color.colorVehicleTrolleybus,R.drawable.number_01,R.raw.vehicle_01);
        this.addVehicle(3, Vehicle.eVehicleType.Trolleybus,R.color.colorVehicleTrolleybus,R.drawable.number_03,R.raw.vehicle_03);
        this.addVehicle(4, Vehicle.eVehicleType.Trolleybus,R.color.colorVehicleTrolleybus,R.drawable.number_04,R.raw.vehicle_04);
        this.addVehicle(5, Vehicle.eVehicleType.Trolleybus,R.color.colorVehicleTrolleybus,R.drawable.number_05,R.raw.vehicle_05);
        this.addVehicle(6, Vehicle.eVehicleType.Trolleybus,R.color.colorVehicleTrolleybus,R.drawable.number_06,R.raw.vehicle_06);
        this.addVehicle(7, Vehicle.eVehicleType.Trolleybus,R.color.colorVehicleTrolleybus,R.drawable.number_07,R.raw.vehicle_07);
        this.addVehicle(14,Vehicle.eVehicleType.Trolleybus,R.color.colorVehicleTrolleybus,R.drawable.number_14,R.raw.vehicle_14);
        this.addVehicle(16,Vehicle.eVehicleType.Trolleybus,R.color.colorVehicleTrolleybus,R.drawable.number_16,R.raw.vehicle_16);

        this.addVehicle(20,Vehicle.eVehicleType.CityBus,R.color.colorVehicleCityBus,R.drawable.number_20 ,R.raw.vehicle_20);
        this.addVehicle(21,Vehicle.eVehicleType.CityBus,R.color.colorVehicleCityBus,R.drawable.number_21 ,R.raw.vehicle_21);
        this.addVehicle(22,Vehicle.eVehicleType.CityBus,R.color.colorVehicleCityBus,R.drawable.number_22 ,R.raw.vehicle_22);
        this.addVehicle(24,Vehicle.eVehicleType.CityBus,R.color.colorVehicleCityBus,R.drawable.number_24 ,R.raw.vehicle_24);
        this.addVehicle(26,Vehicle.eVehicleType.CityBus,R.color.colorVehicleCityBus,R.drawable.number_26 ,R.raw.vehicle_26);
        this.addVehicle(27,Vehicle.eVehicleType.CityBus,R.color.colorVehicleCityBus,R.drawable.number_27 ,R.raw.vehicle_27);
        this.addVehicle(29,Vehicle.eVehicleType.CityBus,R.color.colorVehicleCityBus,R.drawable.number_29 ,R.raw.vehicle_29);
        this.addVehicle(30,Vehicle.eVehicleType.CityBus,R.color.colorVehicleCityBus,R.drawable.number_30 ,R.raw.vehicle_30);
        this.addVehicle(31,Vehicle.eVehicleType.CityBus,R.color.colorVehicleCityBus,R.drawable.number_31 ,R.raw.vehicle_31);
        this.addVehicle(67,Vehicle.eVehicleType.CityBus,R.color.colorVehicleCityBus,R.drawable.number_67 ,R.raw.vehicle_67);

        this.addVehicle(25,Vehicle.eVehicleType.BusForSelectedPassenger,R.color.colorVehicleSelectedPassenger,R.drawable.number_25 ,R.raw.vehicle_25);
        this.addVehicle(35,Vehicle.eVehicleType.BusForSelectedPassenger,R.color.colorVehicleSelectedPassenger,R.drawable.number_35 ,R.raw.vehicle_35);

        this.addVehicle(50,Vehicle.eVehicleType.NightBus,R.color.colorVehicleNightBus,R.drawable.number_50 ,R.raw.vehicle_50);

    }



    private void addVehicle(int number,
                            Vehicle.eVehicleType type,
                            int colorId,
                            int iconResId,
                            int dataId  )

    {
        TransportTimetables.getInstance().setContext(this.getApplicationContext());
        Vehicle vehicle = new Vehicle();
        vehicle.Database = this.Database;
        vehicle.Type = type;
        vehicle.ColorResId = colorId;
        vehicle.IconResId = iconResId;
        vehicle.Number = number;
        vehicle.DataResId = dataId;

        /*if (this.Database.getNeedToRefill())
        {
            vehicle.getData();
        }*/

        TransportTimetables.getInstance().getVehicles().add(vehicle);
    }




    private List<Vehicle> getVehicles()
    {
        return TransportTimetables.getInstance().getVehicles();
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


    public  void initDb()
    {
        this.Database= new VehicleDatabase();
        this.Database.setupDb(this);
    }







}
