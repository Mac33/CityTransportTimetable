package com.freeman.mac.citytransporttimetable.db;

import android.util.Log;

import com.freeman.mac.citytransporttimetable.model.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mac on 30. 7. 2017.
 */

public class DbMinuteMapping {

    public static  String col_ID = "_id";
    public static  String col_VEHICLE_NUMBER = "VEHICLE_NUMBER";
    public static  String col_HOUR = "HOUR";
    public static  String col_MINUTE= "MINUTE";
    public static  String col_DIRECTION_NAME = "DIRECTION_NAME";
    public static  String col_STREET_NAME = "STREET_NAME";
    public static  String col_SIGN_VALUE  = "SIGN_VALUE";

    public int Id;
    public int VehicleNumber;
    public  int Hour;
    public  int Minute;
    public  String DirectionName = StringUtils.Empty;
    public  String StreetName = StringUtils.Empty;
    public List<String> Signs = new ArrayList<>();


}
