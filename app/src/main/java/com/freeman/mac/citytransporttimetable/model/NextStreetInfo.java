package com.freeman.mac.citytransporttimetable.model;

import com.google.gson.annotations.*;

/**
 * Created by Mac on 16. 4. 2017.
 */

public class NextStreetInfo
{
    @Expose
    @SerializedName("Name")
    public String Name;

    @Expose
    @SerializedName("Duration")
    public int Duration;

}