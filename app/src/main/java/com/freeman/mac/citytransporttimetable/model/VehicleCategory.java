package com.freeman.mac.citytransporttimetable.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mac on 4. 4. 2017.
 */

public class VehicleCategory {
    public Vehicle.eVehicleType Type;
    public  String Description;
    public  List<Vehicle> Vehicles;


    void VehicleCategory()
    {
        this.Vehicles = new ArrayList<Vehicle>();
    }

}
