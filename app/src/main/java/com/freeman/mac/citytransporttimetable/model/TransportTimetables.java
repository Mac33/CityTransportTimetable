package com.freeman.mac.citytransporttimetable.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mac on 7. 3. 2017.
 */

public class TransportTimetables {

    private Context mContext;

    private final static TransportTimetables _instance = new TransportTimetables();

    public static TransportTimetables getInstance() {
        return _instance;
    }

    private List<Vehicle> vehicles;

    public List<Vehicle> getVehicles() {return this.vehicles;}

    public  Vehicle getVehicle(Integer number)
    {
        for (Vehicle item:this.vehicles) {
            if(item.Number==number)
                return  item;
        }
        return  null;
    }


    public List<Vehicle> getVehicles(Vehicle.eVehicleType type)
    {
        List<Vehicle> ret = new ArrayList<Vehicle>();
        for (Vehicle item:getVehicles()) {
            if (item.Type == type)
            {
                ret.add(item);
            }
        }

        return  ret;
    }

    private Vehicle current = null;

    public Vehicle getCurrentVehicle()
    {
        if (this.current == null)
        {
            this.current = this.getVehicles().get(0);
        }
        return this.current;
    }

    public void setCurrentVehicle(int number)
    {
        this.current = null;
        for (Vehicle item:this.getVehicles()) {
            if (item.Number==number)
            {
                this.current = item;
                break;
            }

        }

        if (this.current ==null)
        {
            this.current = this.getVehicles().get(0);
        }

    }



    public  void  setContext(Context context){
        this.mContext = context;
    }


    public  Context getContext()
    {
        return this.mContext;
    }

    private TransportTimetables() {
        this.vehicles = new ArrayList<Vehicle>();
     }





}
