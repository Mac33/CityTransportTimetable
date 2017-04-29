package com.freeman.mac.citytransporttimetable.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mac on 18. 4. 2017.
 */

public class VehicleDescriptionItem {

    @SerializedName("Text")
    public String Text = StringUtils.Empty;
    @SerializedName("Sign")
    public String Sign = StringUtils.Empty;


    public VehicleDescriptionItem(String sign, String text )
    {
        this.Sign = sign;
        this.Text = text;
    }
}
