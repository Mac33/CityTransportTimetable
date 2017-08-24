package com.freeman.mac.citytransporttimetable.SearchVehicleByStreetName;

import com.freeman.mac.citytransporttimetable.db.VehicleSearchSignItem;

/**
 * Created by Mac on 24. 8. 2017.
 */

public class VehicleSignSearchedByStreetName {
    public String Sign;
    public  String Text;


    public  VehicleSignSearchedByStreetName(VehicleSearchSignItem item)
    {
        this.Sign = item.Sign;
        this.Text = item.Text;
    }

}
