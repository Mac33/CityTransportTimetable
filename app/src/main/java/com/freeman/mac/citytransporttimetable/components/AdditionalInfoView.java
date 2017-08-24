package com.freeman.mac.citytransporttimetable.components;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.freeman.mac.citytransporttimetable.R;
import com.freeman.mac.citytransporttimetable.SignsAdapter;
import com.freeman.mac.citytransporttimetable.SignsRowItem;


/**
 * Created by Mac on 24. 8. 2017.
 */

public class AdditionalInfoView extends LinearLayout {

    private LinearLayout lowVehicleDescriptionView;
    private RecyclerView vehicleDescriptions;


    public AdditionalInfoView(Context context) {
        super(context);
        this.init(context);
    }

    public AdditionalInfoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    public AdditionalInfoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context);
    }

    public void init(Context context) {
        View.inflate(context, R.layout.descriptions_view, this);
        this.lowVehicleDescriptionView = (LinearLayout) findViewById(R.id.lowVehicleDescriptionTextAndSign);
        this.vehicleDescriptions = (RecyclerView) findViewById(R.id.vehicleDescriptions);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        this.vehicleDescriptions.setLayoutManager(mLayoutManager);
    }


    public void setData(SignsRowItem[] allDescriptions, Boolean showLowVehicleDescription)
    {
        if (showLowVehicleDescription)
        {
            lowVehicleDescriptionView.setVisibility(View.VISIBLE);
        }else
        {
            lowVehicleDescriptionView.setVisibility(View.GONE);
        }

        if (allDescriptions.length == 0) {
            vehicleDescriptions.setVisibility(View.GONE);
        } else {
            vehicleDescriptions.setVisibility(View.VISIBLE);
            vehicleDescriptions.setAdapter(new SignsAdapter(allDescriptions));
        }
    }
}
