package com.freeman.mac.citytransporttimetable.components;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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

    private ColorStateList oldColors;
    private TextView time;
    private ImageView lowVehicleSign;
    private TextView additionalInfo;
    private LinearLayout shapeLayoutView;

    public int Minute = -1;

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
        oldColors = time.getTextColors();
        this.clear();
    }


    public void setTime(MinuteMapping item) {
        this.Minute = item.Minute;
        String textValue = String.format("%02d", this.Minute);
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
            value = value + this.additionalInfo.getText();
            this.additionalInfo.setText(value);
        }

    }


    private void clearAdditionalInfo() {
        this.additionalInfo.setText(StringUtils.Empty);
        this.lowVehicleSign.setVisibility(GONE);
    }


    public void allowBorder() {
        this.shapeLayoutView.setBackgroundResource(R.drawable.shape);
        this.lowVehicleSign.setImageResource(R.drawable.wheelchair_symbol_white);
        this.time.setTextColor(ContextCompat.getColor(this.getContext(), R.color.windowBackground));
        this.additionalInfo.setTextColor(ContextCompat.getColor(this.getContext(), R.color.windowBackground));
    }


    public void disableBorder() {
        this.shapeLayoutView.setBackgroundResource(0);
        this.lowVehicleSign.setImageResource(R.drawable.wheelchair_symbol);
        this.time.setTextColor(this.oldColors);
        this.additionalInfo.setTextColor(this.oldColors);
    }

    private void clear() {
        this.time.setText(StringUtils.Empty);
        this.disableBorder();
        this.clearAdditionalInfo();
    }


}
