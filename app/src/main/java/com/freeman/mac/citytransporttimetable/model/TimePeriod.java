package com.freeman.mac.citytransporttimetable.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mac on 12. 3. 2017.
 */

public class TimePeriod {

    public static int DIRECTION_ONE  = 0;
    public static int DIRECTION_TWO  = 1;

    private int currentDirection = DIRECTION_ONE;

    private List<Street> directionOne;
    private List<Street> directionTwo;

    public Street CureentStreet;


    public  boolean HasCurrentDiretionStreet()
    {
        return !this.getCurrentDirectionStreets().isEmpty();
    }


    public Street getCureentStreet()
    {
        if (this.CureentStreet==null)
        {
            this.CureentStreet = this.getCurrentDirectionStreets().get(0);
        }
        return  this.CureentStreet;
    }



    public void setCureentStreet(int index )
    {
        if(this.getCurrentDirectionStreets().size() > index ) {
            this.CureentStreet = this.getCurrentDirectionStreets().get(index);
        }
    }

    public List<Street> getCurrentDirectionStreets() {
        if (currentDirection == DIRECTION_ONE) {
            return  this.directionOne;
        }else{
            return  this.directionTwo;
        }
    }

    public String Name;

    public TimePeriod(String name)
    {
        this.Name = name;
        this.directionOne = new ArrayList<Street>();
        this.directionTwo  = new ArrayList<Street>();

    }

    public List<Street> getDirectionOneStreets(){ return  this.directionOne;}
    public List<Street> getDirectionTwoStreets(){ return  this.directionTwo;}






}
