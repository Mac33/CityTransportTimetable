package com.freeman.mac.citytransporttimetable.SearchVehicleByStreetName;

import com.freeman.mac.citytransporttimetable.db.VehicleSearchItem;
import com.freeman.mac.citytransporttimetable.db.VehicleSearchSignItem;
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

    public List<VehicleSignSearchedByStreetName> Signs = new ArrayList<>();

    public Boolean ShowLowVehicleSign = false;

    public  String AllAdditionalInfo = StringUtils.Empty;

    public VehicleSearchedByStreetName(VehicleSearchItem item)
    {
        this.Number = item.VehicleNumber;
        this.Hour = item.Hour;
        this.Minute = item.Minute;
        this.DirectionName = item.DirectionName;

        for (VehicleSearchSignItem sign:item.Signs) {
            if (sign.Sign.equals("n")) {
                this.ShowLowVehicleSign = true;
            } else
            {
                this.AllAdditionalInfo = this.AllAdditionalInfo + sign.Sign;
            }
            this.Signs.add(new VehicleSignSearchedByStreetName(sign));
        }


    }


}
