package com.freeman.mac.citytransporttimetable;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.freeman.mac.citytransporttimetable.StreetNameActivity.StreetNamesActivity;
import com.freeman.mac.citytransporttimetable.model.TransportTimetables;


public class TimetableActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView currentStreetName;

    private TimetableFragment workDays;
    private TimetableFragment schoolDays;
    private TimetableFragment weekend;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == StreetNamesActivity.STREET_POSITION_REQUEST_CODE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                String name = data.getExtras().getString(StreetNamesActivity.STREET_NAME, "");
                setCurrentStreet(name);
                Log.w("CityTransportTimetable", "result Value " + name);
            } else {
                Log.w("CityTransportTimetable", "Result Activity - CANCEL");
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TransportTimetables.getInstance().getVehicles();

        setContentView(R.layout.activity_timetable);



        LinearLayout listView = (LinearLayout) findViewById(R.id.StreetNameSubContainer);
        listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = StreetNamesActivity.createInstance(TimetableActivity.this, TransportTimetables.getInstance().getCurrentVehicle().CurrentStreetName);
                startActivityForResult(intent, StreetNamesActivity.STREET_POSITION_REQUEST_CODE);
            }
        });


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        viewPager.setCurrentItem(0);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        currentStreetName = (TextView) findViewById(R.id.StreetName);

        this.setCurrentStreet("Košická");


    }


    private void setupViewPager(ViewPager viewPager) {
        TabViewPagerAdapter adapter = new TabViewPagerAdapter(getSupportFragmentManager());

        workDays = new TimetableFragment();
        workDays.setTimePerion(0);
        adapter.addFragment(workDays, "Work Days");

        schoolDays = new TimetableFragment();
        schoolDays.setTimePerion(1);
        adapter.addFragment(schoolDays, "School Holidays");

        weekend = new TimetableFragment();
        weekend.setTimePerion(2);
        adapter.addFragment(weekend, "Weekend");

        viewPager.setAdapter(adapter);
    }


    private void setCurrentStreet(String name) {
        TransportTimetables.getInstance().getCurrentVehicle().setCurrentStreetToAllPeriods(name);
        this.workDays.OnRefresh();
        this.schoolDays.OnRefresh();
        this.weekend.OnRefresh();
        this.setCurrentStreetName(name);

    }


    private void setCurrentStreetName(String name) {
        currentStreetName.setText(name);
    }


}



