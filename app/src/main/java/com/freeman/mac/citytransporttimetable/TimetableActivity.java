package com.freeman.mac.citytransporttimetable;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.freeman.mac.citytransporttimetable.StreetNameActivity.StreetNamesActivity;
import com.freeman.mac.citytransporttimetable.interfaces.IRefresh;
import com.freeman.mac.citytransporttimetable.interfaces.ISelectedItem;
import com.freeman.mac.citytransporttimetable.model.TransportTimetables;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

public class TimetableActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == StreetNamesActivity.STREET_POSITION_REQUEST_CODE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                int result = data.getExtras().getInt(StreetNamesActivity.STREET_POSITION_KEY, 0);
                setCurrentStreet(result);
                Log.w("CityTransportTimetable", "result Value " + result);
            }else{
                Log.w("CityTransportTimetable", "Result Activity - CANCEL");
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);


        LinearLayout listView = (LinearLayout) findViewById(R.id.StreetNameLin);
        listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = StreetNamesActivity.createInstance(TimetableActivity.this, 1);
                startActivityForResult(intent, StreetNamesActivity.STREET_POSITION_REQUEST_CODE);

            }
        });

        TransportTimetables.getInstance().getVehicles();


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<String> list = new ArrayList<String>();
        list.add("asdasdasA");
        list.add("Afffffffffffff");

        ArrayAdapter<String> adapterBusinessType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        Spinner spCountries = (Spinner) findViewById(R.id.spinner);
        spCountries.setAdapter(adapterBusinessType);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setCurrentItem(0);


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, list);
        MaterialBetterSpinner materialDesignSpinner = (MaterialBetterSpinner)
                findViewById(R.id.android_material_design_spinner);
        materialDesignSpinner.setAdapter(arrayAdapter);


    }

    TimetableFragment workDays;
    TimetableFragment schoolDays;
    TimetableFragment weekend;


    private void setupViewPager(ViewPager viewPager) {

        TabViewPagerAdapter adapter = new TabViewPagerAdapter(getSupportFragmentManager());

        workDays = new TimetableFragment();
        workDays.setTimePerion(0);
        workDays.setSelectedListener(new ISelectedItem() {
            @Override
            public void OnSelectedItem(int index) {
                setCurrentStreet(index);
            }
        });

        adapter.addFragment(workDays, "Work Days");


        schoolDays = new TimetableFragment();
        schoolDays.setTimePerion(1);
        schoolDays.setSelectedListener(new ISelectedItem() {
            @Override
            public void OnSelectedItem(int index) {
                setCurrentStreet(index);
            }
        });
        adapter.addFragment(schoolDays, "School Holidays");


        weekend = new TimetableFragment();
        weekend .setTimePerion(2);
        weekend.setSelectedListener(new ISelectedItem() {
            @Override
            public void OnSelectedItem(int index) {
                setCurrentStreet(index);
            }
        });
        adapter.addFragment(weekend, "Weekend");

        viewPager.setAdapter(adapter);
    }


    private void setCurrentStreet(int index) {

            TransportTimetables.getInstance().getCurrentVehicle().setCurrentStreetToAllPeriods(index);
            workDays.OnRefresh();
            schoolDays.OnRefresh();
            weekend.OnRefresh();

    }


}



