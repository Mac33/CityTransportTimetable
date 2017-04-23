package com.freeman.mac.citytransporttimetable;

import android.app.Activity;
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
        vehicleDescriptions = (TextView) findViewById(R.id.vehicleDescriptions);

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
        TabViewPagerAdapter adapter = new TabViewPagerAdapter(getSupportFragmentManager());

        List<String> timePeriodNames = TransportTimetables.getInstance().getCurrentVehicle().getTimePeriodNames();

        if (timePeriodNames.contains(TimePeriod.ShoolHolidays))
        {
            schoolDays = new TimetableFragment();
            schoolDays.setTimePerion(1);
            adapter.addFragment(schoolDays, "Školské prázdniny");

        }
        if (timePeriodNames.contains(TimePeriod.WorkDays)) {

            workDays = new TimetableFragment();
            workDays.setTimePerion(0);
            adapter.addFragment(workDays, "Pracovné dni");
        }

        if (timePeriodNames.contains(TimePeriod.Weekend)) {
            weekend = new TimetableFragment();
            weekend.setTimePerion(2);
            adapter.addFragment(weekend, "Víkend a sviatky");

        }

        viewPager.setAdapter(adapter);
    }


    private void setCurrentStreet(int index) {
        TransportTimetables.getInstance().getCurrentVehicle().setCurrentStreet(index);
        this.refreshData();
    }


    void refreshData()
    {
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
        String text = StringUtils.Empty;
        List<Integer> streetVehicleDescription = item.getCurrentStreet().getUsedVehicleDescriptions();
        if (!streetVehicleDescription.isEmpty())
        {
            for (VehicleDescriptionItem des:item.Descriptions)
            {

                boolean found = false;
                for (Integer streetVehicleDesValue:streetVehicleDescription)
                {
                    if(streetVehicleDesValue == (streetVehicleDesValue | des.Type))
                    {
                        found = true;
                    }
                }

                if(!found)
                {
                    continue;
                }

                if (text.isEmpty())
                {
                    text = MinuteMapping.getTextSign(des.Type) + " - " + des.Text;

                }
                else
                {
                    text = text + "\n" + MinuteMapping.getTextSign(des.Type) + " - " + des.Text;
                }

            }
         }

        if (text.isEmpty())
        {
            vehicleDescriptions.setVisibility(View.GONE);
        }
        else
        {
            vehicleDescriptions.setVisibility(View.VISIBLE);
            vehicleDescriptions.setText(text);
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



