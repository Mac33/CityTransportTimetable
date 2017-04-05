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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.freeman.mac.citytransporttimetable.StreetNameActivity.StreetNamesActivity;
import com.freeman.mac.citytransporttimetable.model.StringUtils;
import com.freeman.mac.citytransporttimetable.model.TransportTimetables;



public class TimetableActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView currentStreetName;

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
                String name = data.getExtras().getString(StreetNamesActivity.STREET_NAME, "");
                setCurrentStreet(index);
                Log.w("CityTransportTimetable", "result Value " + name);
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

        Button changeLayout = (Button )this.findViewById(R.id.change_direction);
        changeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w("","");
            }
        });
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        this.setupViewPager(viewPager);
        viewPager.setCurrentItem(0);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        currentStreetName = (TextView) findViewById(R.id.StreetName);

        this.setCurrentStreet(0);

    }


    void initToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(StringUtils.Empty);

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



    private void setupViewPager(ViewPager viewPager) {
        TabViewPagerAdapter adapter = new TabViewPagerAdapter(getSupportFragmentManager());

        workDays = new TimetableFragment();
        workDays.setTimePerion(0);
        adapter.addFragment(workDays, "Pracovné dni");

        schoolDays = new TimetableFragment();
        schoolDays.setTimePerion(1);
        adapter.addFragment(schoolDays, "Školské prázdniny");

        weekend = new TimetableFragment();
        weekend.setTimePerion(2);
        adapter.addFragment(weekend, "Víkend a sviatky");

        viewPager.setAdapter(adapter);
    }


    private void setCurrentStreet(int index) {
        TransportTimetables.getInstance().getCurrentVehicle().setCurrentStreet(index);
        this.workDays.OnRefresh();
        this.schoolDays.OnRefresh();
        this.weekend.OnRefresh();
        this.setCurrentStreetName(TransportTimetables.getInstance().getCurrentVehicle().CurrentStreetName);

    }


    private void setCurrentStreetName(String name) {
        currentStreetName.setText(name);
    }



}



