package com.freeman.mac.citytransporttimetable.components;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.freeman.mac.citytransporttimetable.R;
import com.freeman.mac.citytransporttimetable.model.MinuteMapping;
import com.freeman.mac.citytransporttimetable.model.StringUtils;

/**
 * Created by Mac on 11. 6. 2017.
 */

public class TimeView extends LinearLayout {

    private TextView time;
    private ImageView lowVehicleSign;
    private TextView additionalInfo;
    private LinearLayout shapeLayoutView;


    public TimeView(Context context) {
        super(context);
        this.init(context);
    }

    public TimeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    public TimeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context);
    }


    public void init(Context context) {
        View.inflate(context, R.layout.time_view, this);
        time = (TextView) findViewById(R.id.TimeViewValue);
        lowVehicleSign = (ImageView) findViewById(R.id.TimeViewLowVehicleSign);
        additionalInfo = (TextView) findViewById(R.id.TimeViewAdditionalInfo);
        shapeLayoutView = (LinearLayout) findViewById(R.id.TimeViewShapeLayout);
        this.clear();
    }


    public void setTime(MinuteMapping item) {
        String textValue = String.format("%02d", item.Minute);
        this.time.setText(textValue);
        this.setAdditionalInfo(item);
    }


    private void setAdditionalInfo(MinuteMapping item) {
        this.clearAdditionalInfo();
        for (String sign : item.Signs) {
            this.addAdditionalInfo(sign);
        }
    }


    private void addAdditionalInfo(String value) {
        if (value.equals("n")) {
            this.lowVehicleSign.setVisibility(VISIBLE);
        } else {
            this.additionalInfo.setText(value);
        }

    }


    private void clearAdditionalInfo() {
        this.additionalInfo.setText(StringUtils.Empty);
        this.lowVehicleSign.setVisibility(GONE);
    }


    public void allowBorder() {
        this.shapeLayoutView.setBackgroundResource(R.drawable.shape);
    }


    public void disableBorder() {
        this.shapeLayoutView.setBackgroundResource(0);
    }

    private void clear() {
        this.time.setText(StringUtils.Empty);
        this.disableBorder();
        this.clearAdditionalInfo();
    }


}
