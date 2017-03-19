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
import com.freeman.mac.citytransporttimetable.interfaces.ISelectedItem;
import com.freeman.mac.citytransporttimetable.model.Street;
import com.freeman.mac.citytransporttimetable.model.TransportTimetables;

import java.util.List;


public class StreetNamesActivity extends AppCompatActivity {

    public static String STREET_POSITION_KEY = "STREET_POSITION";
    public static int STREET_POSITION_REQUEST_CODE = 1;


    public static Intent createInstance(Activity activity, int streetPosition) {
        Intent intent = new Intent(activity, StreetNamesActivity.class);
        intent.putExtra(STREET_POSITION_KEY, streetPosition);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_names);
        int streetIndex = getIntent().getIntExtra(STREET_POSITION_KEY, 0); // here 0 is the default value
        Log.w("CityTransportTimetable", "onCreate Value " + streetIndex);
        this.initDataAdapter();

    }


    private void initDataAdapter() {
        RecyclerView view = (RecyclerView) findViewById(R.id.RecycleViewStreetNames);
        List<Street> streets = TransportTimetables.getInstance().
                getCurrentVehicle().
                getCurrentTimePeriod().
                getCurrentDirectionStreets();

        StreetName_Adapter adapter = new StreetName_Adapter(streets);
        adapter.setStreetSelectedListener(new ISelectedItem() {
            @Override
            public void OnSelectedItem(int index) {
                setCurrentStreet(index);
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        view.setLayoutManager(mLayoutManager);
        view.setItemAnimator(new DefaultItemAnimator());
        view.setAdapter(adapter);

    }


    void setCurrentStreet(int index) {
        setDataResult(index);
    }

    void setDataResult(int index) {
        Intent iData = new Intent();
        iData.putExtra(STREET_POSITION_KEY, index);
        setResult(android.app.Activity.RESULT_OK, iData);
    }
}
