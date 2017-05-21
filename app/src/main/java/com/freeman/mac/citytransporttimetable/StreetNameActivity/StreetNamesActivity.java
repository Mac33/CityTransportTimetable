package com.freeman.mac.citytransporttimetable.StreetNameActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.freeman.mac.citytransporttimetable.R;
import com.freeman.mac.citytransporttimetable.interfaces.ISelectedItemByInteger;
import com.freeman.mac.citytransporttimetable.model.Street;
import com.freeman.mac.citytransporttimetable.model.StringUtils;
import com.freeman.mac.citytransporttimetable.model.TransportTimetables;

import java.util.List;



public class StreetNamesActivity extends AppCompatActivity {

    public static String STREET_INDEX_POSITION = "STREET_INDEX_POSITION";
    public static int STREET_POSITION_REQUEST_CODE = 1;
    private RecyclerView view;



    public static Intent createInstance(Activity activity, int index) {
        Intent intent = new Intent(activity, StreetNamesActivity.class);
        intent.putExtra(STREET_INDEX_POSITION, index);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_street_names);
        this.initToolbar();
        this.initDataAdapter();



    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(StringUtils.Empty);

    }


    @Override
    public boolean onSupportNavigateUp() {
        this.onBackPressed();
        return true;
    }


    private void initDataAdapter() {
        view = (RecyclerView) findViewById(R.id.RecycleViewStreetNames);

        List<Street> streets = TransportTimetables.getInstance().
                getCurrentVehicle().
                getCurrentDirectionStreets();

        final int position = TransportTimetables.getInstance().getCurrentVehicle().CurrentStreetIndex;
        StreetName_Adapter adapter = new StreetName_Adapter(streets, position);
        adapter.setStreetSelectedListener(new ISelectedItemByInteger() {
            @Override
            public void OnSelectedItem(int index) {
                setCurrentStreet(index);
                view.postInvalidate();
            }


        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        view.setLayoutManager(mLayoutManager);
        view.setAdapter(adapter);
        view.addItemDecoration(new StreetName_ItemDecoration(streets));

        view.post(new Runnable(){
            @Override
            public void run() {
                // If child is invisible then scroll to selected position
                if (position > 0 && view.getChildCount() > 0)
                {
                    NestedScrollView scrollView =  (NestedScrollView)findViewById(R.id.ScrollViewStreetNames);
                    int height = view.getChildAt(0).getBottom();
                    int y = height * (position + 1);
                    if (scrollView.getBottom() < y) {
                         scrollView.scrollBy(0,y - scrollView.getBottom());
                    }
                }
            }
        });
    }


    void setCurrentStreet(int index) {
        setDataResult(index);
    }


    void setDataResult(int index) {
        Intent iData = new Intent();
        iData.putExtra(STREET_INDEX_POSITION, index);
        setResult(android.app.Activity.RESULT_OK, iData);
    }
}
