package com.freeman.mac.citytransporttimetable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NavUtils;
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
import com.freeman.mac.citytransporttimetable.model.MinuteMapping;
import com.freeman.mac.citytransporttimetable.model.Street;
import com.freeman.mac.citytransporttimetable.model.StringUtils;
import com.freeman.mac.citytransporttimetable.model.TimePeriod;
import com.freeman.mac.citytransporttimetable.model.TransportTimetables;
import com.freeman.mac.citytransporttimetable.model.Vehicle;
import com.freeman.mac.citytransporttimetable.model.VehicleDescriptionItem;

import java.util.List;


public class TimetableActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView currentStreetName;
    private TextView currentStreetDescription;

    private TimetableFragment workDays;
    private TimetableFragment schoolDays;
    private TimetableFragment weekend;

    private TextView vehicleDescriptions;
    private List<String> oldTimePeriodNames;

    private TabViewPagerAdapter adapter;

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
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        currentStreetName = (TextView) findViewById(R.id.StreetName);
        currentStreetDescription = (TextView) findViewById(R.id.StreetNameDescription);
        vehicleDescriptions = (TextView) findViewById(R.id.vehicleDescriptions);

        this.setCurrentStreet(0);
        viewPager.setCurrentItem(1);


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
        if(TransportTimetables.getInstance().getCurrentVehicle().IconResId > 0)
        {
            vehicleNumberView.setImageResource( TransportTimetables.getInstance().getCurrentVehicle().IconResId);
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
        if ( TransportTimetables.getInstance().getCurrentVehicle().HasTwoDirections() )
        {
            btnChangeDirection.setVisibility(View.VISIBLE);
        }
        else
        {
            btnChangeDirection.setVisibility(View.GONE);
        }
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

        List<String> timePeriodNames;
        if (TransportTimetables.getInstance().getCurrentVehicle().getCurrentStreet()!=null) {
            timePeriodNames = TransportTimetables.getInstance().getCurrentVehicle().getCurrentStreetPeriodNames();
        }
        else{
            timePeriodNames = TransportTimetables.getInstance().getCurrentVehicle().getTimePeriodNames();
        }

        int oldPosition = -1;

        if (oldTimePeriodNames!=null)
        {
            boolean needToRefresh = false;

            if(oldTimePeriodNames.size() != timePeriodNames.size())
                needToRefresh = true;

            for (String name:oldTimePeriodNames) {
                if(!timePeriodNames.contains(name))
                    needToRefresh = true;
            }

            if (!needToRefresh)
                return;

            oldPosition = viewPager.getCurrentItem();
        }

        adapter = new TabViewPagerAdapter(getSupportFragmentManager());

        schoolDays = null;
        workDays = null;
        weekend = null;

        if (timePeriodNames.contains(TimePeriod.ShoolHolidays))
        {
            schoolDays = new TimetableFragment();
            schoolDays.setTimePeriod(1);
            adapter.addFragment(schoolDays, "Školské prázdniny");

        }
        if (timePeriodNames.contains(TimePeriod.WorkDays)) {

            workDays = new TimetableFragment();
            workDays.setTimePeriod(0);
            adapter.addFragment(workDays, "Pracovné dni");
        }

        if (timePeriodNames.contains(TimePeriod.Weekend)) {
            weekend = new TimetableFragment();
            weekend.setTimePeriod(2);
            adapter.addFragment(weekend, "Víkend a sviatky");

        }

        oldTimePeriodNames = timePeriodNames;
        viewPager.setAdapter(adapter);

        if (oldPosition > -1 && oldPosition < viewPager.getChildCount())
        {
            viewPager.setCurrentItem(oldPosition);
        }
    }


    private void setCurrentStreet(int index) {
        TransportTimetables.getInstance().getCurrentVehicle().setCurrentStreet(index);
        this.refreshData();
    }


    void refreshData()
    {
        this.setupViewPager(viewPager);
        this.refreshTimeTableFragmets();
        Vehicle currentVehicle = TransportTimetables.getInstance().getCurrentVehicle();
        String currentDirectionName = currentVehicle.getCurrentDirectionName();
        Street curretStreet = currentVehicle.getCurrentStreet();
        this.setCurrentStreet(curretStreet, currentDirectionName);
        this.setVehicleDescriptions(currentVehicle);

    }



    void  refreshTimeTableFragmets()
    {
        this.refreshTimeTableFragmet(this.workDays);
        this.refreshTimeTableFragmet(this.schoolDays);
        this.refreshTimeTableFragmet(this.weekend);

    }



    void  refreshTimeTableFragmet(TimetableFragment item)
    {
        if (item != null)
        {
            item.OnRefresh();
        }
    }



    private void setVehicleDescriptions(Vehicle item) {
        String allDescriptions = StringUtils.Empty;
        List<String> streetVehicleDescriptions = item.getCurrentStreet().getUsedVehicleDescriptions();
        if (item.hasAdditionalInformation() || !streetVehicleDescriptions.isEmpty())
        {
            for (VehicleDescriptionItem des:item.Descriptions )
            {

                boolean found = streetVehicleDescriptions.contains(des.Sign);

                if(des.Sign.equals(MinuteMapping.AdditionalInfromation))
                    found = true;

                if(!found)
                    continue;

                String oneDescription;
                if (des.Sign.equals(MinuteMapping.AdditionalInfromation))
                {
                    oneDescription = des.Text;
                }
                else
                {
                    oneDescription = des.Sign + " - " + des.Text;
                }

                if (allDescriptions.isEmpty())
                {
                    allDescriptions = oneDescription;

                }
                else
                {
                    allDescriptions = allDescriptions + "\n" + oneDescription;
                }

            }
         }

        if (allDescriptions.isEmpty())
        {
            vehicleDescriptions.setVisibility(View.GONE);
        }
        else
        {
            vehicleDescriptions.setVisibility(View.VISIBLE);
            vehicleDescriptions.setText(allDescriptions);
        }
    }


    private void setCurrentStreet(Street item, String directionName) {

        if (item.RequestStop)
        {
            currentStreetDescription.setText("Zástavka na znamenie");
        }else
        {
            currentStreetDescription.setText("Zástavka");
        }

        if (!StringUtils.isNullOrEmpty(directionName))
        {
            currentStreetDescription.setText(currentStreetDescription.getText() + " - " + directionName);
        }
        currentStreetName.setText(item.Name);
    }



}



