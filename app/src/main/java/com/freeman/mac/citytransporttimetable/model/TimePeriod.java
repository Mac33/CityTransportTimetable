package com.freeman.mac.citytransporttimetable.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mac on 12. 3. 2017.
 */

public class TimePeriod {

    public List<HourMapping> Hours;
    public String Name;


    public TimePeriod(String name) {
        this.Name = name;
       this.Hours = new  ArrayList<HourMapping>();
     }


  /*  public Street getStreet(int index ) {

        if (this.Streets.size() > index)
        {
            return  this.Streets.get(index);
        }
        return  null;
    }


    public void setCureentStreet(int index)
    {
        this.CurrentStreetIndex = index;
    }






    public String getStreetName(int index)
    {
        Street street = this.getStreet(index);
        if (street!=null)
        {
            return  street.Name;
        }
        return StringUtils.Empty;
    }



    public void addStreet(Street item)
    {
        this.Streets.add(item);
    }*/







}
