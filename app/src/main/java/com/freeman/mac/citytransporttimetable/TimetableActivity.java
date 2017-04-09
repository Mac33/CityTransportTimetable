package com.freeman.mac.citytransporttimetable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.freeman.mac.citytransporttimetable.StreetNameActivity.StreetNamesActivity;
import com.freeman.mac.citytransporttimetable.model.Street;
import com.freeman.mac.citytransporttimetable.model.StringUtils;
import com.freeman.mac.citytransporttimetable.model.TransportTimetables;



public class TimetableActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView currentStreetName;
    private TextView currentStreetDescription;

    private TimetableFragment workDays;
    private TimetableFragment schoolDays;
    private TimetableFragment weekend;



    public static Intent createInstance(Activity activity) {
        Intent intent = new Intent(activity, TimetableActivity.class);
        return intent;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == StreetNamesActivity.STREET_POSITION_REQUEST_CODE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                int index = data.getExtras().getInt(StreetNamesActivity.STREET_INDEX_POSITION, 0);
                setCurrentStreet(index);
                Log.w("CityTransportTimetable", "result Value " + index);
            } else {
                Log.w("CityTransportTimetable", "Result Activity - CANCEL");
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_timetable);

        LinearLayout listView = (LinearLayout) this.findViewById(R.id.StreetNameMainContainer);
        listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = StreetNamesActivity.createInstance(TimetableActivity.this,
                        TransportTimetables.getInstance().getCurrentVehicle().CurrentStreetIndex);

                startActivityForResult(intent, StreetNamesActivity.STREET_POSITION_REQUEST_CODE);
            }
        });

        this.initToolbar();
        this.initChangeDirection();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        this.setupViewPager(viewPager);
        viewPager.setCurrentItem(1);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        currentStreetName = (TextView) findViewById(R.id.StreetName);
        currentStreetDescription = (TextView) findViewById(R.id.StreetNameDescription);

        this.setCurrentStreet(0);

    }



    void initToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(StringUtils.Empty);
        this.setToolbarVehicleNumber();

    }



    private  void  setToolbarVehicleNumber()
    {
        ImageView vehicleNumberView  = (ImageView)findViewById(R.id.imgViewVehicleNumber);
        if(TransportTimetables.getInstance().getCurrentVehicle().IconToolBarId > 0)
        {
            vehicleNumberView.setImageResource( TransportTimetables.getInstance().getCurrentVehicle().IconToolBarId);
        }else{
            vehicleNumberView.setImageResource(android.R.color.transparent);
        }
    }



    private void initChangeDirection()
    {
        Button btnChangeDirection = (Button) this.findViewById(R.id.change_direction);
        btnChangeDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeDirection();
            }
        });
    }



    private  void  changeDirection()
    {
        TransportTimetables.getInstance().getCurrentVehicle().swapDirection();
        refreshData();

    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



    private void setupViewPager(ViewPager viewPager) {
        TabViewPagerAdapter adapter = new TabViewPagerAdapter(getSupportFragmentManager());


        schoolDays = new TimetableFragment();
        schoolDays.setTimePerion(1);
        adapter.addFragment(schoolDays, "Školské prázdniny");

        workDays = new TimetableFragment();
        workDays.setTimePerion(0);
        adapter.addFragment(workDays, "Pracovné dni");


        weekend = new TimetableFragment();
        weekend.setTimePerion(2);
        adapter.addFragment(weekend, "Víkend a sviatky");

        viewPager.setAdapter(adapter);
    }


    private void setCurrentStreet(int index) {
        TransportTimetables.getInstance().getCurrentVehicle().setCurrentStreet(index);
        this.refreshData();
    }


    void refreshData()
    {
        this.workDays.OnRefresh();
        this.schoolDays.OnRefresh();
        this.weekend.OnRefresh();
        this.setCurrentStreet(TransportTimetables.getInstance().getCurrentVehicle().getCurrentStreet());

    }


    private void setCurrentStreet(Street item) {
        if (item.RequestStop)
        {
            currentStreetDescription.setText("Zástavka na znamenie");
        }else
        {
            currentStreetDescription.setText("Zástavka");
        }
        currentStreetName.setText(item.Name);
    }



}



