package com.freeman.mac.citytransporttimetable.SearchVehicleByStreetName;

import com.freeman.mac.citytransporttimetable.db.VehicleSearchItem;
import com.freeman.mac.citytransporttimetable.model.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mac on 8. 8. 2017.
 */

public class VehicleSearchedByStreetName {

    public int Number;
    public int Hour;
    public int Minute;
    public String DirectionName = StringUtils.Empty;
    public List<String> Signs = new ArrayList<>();


    public VehicleSearchedByStreetName(VehicleSearchItem item)
    {
        this.Number = item.VehicleNumber;
        this.Hour = item.Hour;
        this.Minute = item.Minute;
        this.DirectionName = item.DirectionName;
        this.Signs.addAll(item.Signs);

    }


    public  String getAdditionalInfo()
    {
        String ret = StringUtils.Empty;
        for (String item:this.Signs) {
            if (!item.equals("n"))
            {
                ret = ret + item;
            }
        }
        return ret;
    }



}
