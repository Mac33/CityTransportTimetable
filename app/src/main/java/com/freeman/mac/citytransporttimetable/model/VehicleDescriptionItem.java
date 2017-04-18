package com.freeman.mac.citytransporttimetable.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mac on 18. 4. 2017.
 */

public class VehicleDescriptionItem {

    @SerializedName("Type")
    public int Type = 0;
    @SerializedName("Text")
    public String Text = StringUtils.Empty;

    public VehicleDescriptionItem(int type, String text )
    {
        this.Type = type;
        this.Text = text;
    }
}
