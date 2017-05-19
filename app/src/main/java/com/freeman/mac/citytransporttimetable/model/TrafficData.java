package com.freeman.mac.citytransporttimetable.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mac on 2. 5. 2017.
 */

public class TrafficData {

    @SerializedName("DirectionOne")
    public List<Street> DirectionOne = new ArrayList<>();

    @SerializedName("DirectionTwo")
    public List<Street> DirectionTwo = new ArrayList<>();

    @SerializedName("Descriptions")
    public List<VehicleDescriptionItem> Descriptions = new ArrayList<>();

    @SerializedName("DirectionOneName")
    public String DirectionOneName ;

    @SerializedName("DirectionTwoName")
    public String DirectionTwoName;



}
