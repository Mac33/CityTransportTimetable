package com.freeman.mac.citytransporttimetable.StreetNameActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.freeman.mac.citytransporttimetable.R;
import com.freeman.mac.citytransporttimetable.interfaces.*;
import com.freeman.mac.citytransporttimetable.model.Street;
import com.freeman.mac.citytransporttimetable.model.TransportTimetables;

import java.util.List;


public class StreetNamesActivity extends AppCompatActivity {

    public static String STREET_NAME = "STREET_NAME";
    public static int STREET_POSITION_REQUEST_CODE = 1;


    public static Intent createInstance(Activity activity, String name) {
        Intent intent = new Intent(activity, StreetNamesActivity.class);
        intent.putExtra(STREET_NAME, name);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_names);
        String name = getIntent().getStringExtra(STREET_NAME);
        Log.w("CityTransportTimetable", "onCreate Value " + name);
        this.initDataAdapter();

    }


    private void initDataAdapter() {
        RecyclerView view = (RecyclerView) findViewById(R.id.RecycleViewStreetNames);
        List<Street> streets = TransportTimetables.getInstance().
                getCurrentVehicle().
                getCurrentTimePeriod().
                getCurrentDirectionStreets();

        StreetName_Adapter adapter = new StreetName_Adapter(streets);
        adapter.setStreetSelectedListener(new ISelectedItemByString()
        {

            @Override
            public void OnSelectedItem(String str) {
                setCurrentStreet(str);
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        view.setLayoutManager(mLayoutManager);
        view.setItemAnimator(new DefaultItemAnimator());
        view.setAdapter(adapter);

    }


    void setCurrentStreet(String name) {
        setDataResult(name);
    }

    void setDataResult(String name) {
        Intent iData = new Intent();
        iData.putExtra(STREET_NAME, name);
        setResult(android.app.Activity.RESULT_OK, iData);
    }
}
