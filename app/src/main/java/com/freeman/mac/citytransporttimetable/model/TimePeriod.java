package com.freeman.mac.citytransporttimetable.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mac on 12. 3. 2017.
 */

public class TimePeriod {


    private Street CureentStreet;
    private int CurrentStreetIndex =0;
    public String Name;

    private List<Street> Streets = new ArrayList<Street>();

    public TimePeriod(String name) {
        this.Name = name;
     }


    public Street getStreet(int index ) {

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
    public Street getCurrentStreet()
    {
        if  (this.getStreets().size() > this.CurrentStreetIndex ) {
            this.CureentStreet = this.getStreets().get(this.CurrentStreetIndex);
        }
        if (this.CureentStreet == null) {
            this.CureentStreet = Street.Empty();
        }
        if (this.CureentStreet.getHours().isEmpty()) {
            this.CureentStreet = Street.Empty();
        }
        return this.CureentStreet;
    }


    public  List<Street> getStreets()
    {
        return  this.Streets;
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
    }







}
