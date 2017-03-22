package com.freeman.mac.citytransporttimetable.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mac on 12. 3. 2017.
 */

public class TimePeriod {

    public static int DIRECTION_ONE = 0;
    public static int DIRECTION_TWO = 1;
    public Street CureentStreet;
    public String CurrentStreetName;
    public String Name;
    private int currentDirection = DIRECTION_ONE;
    private List<Street> directionOne;
    private List<Street> directionTwo;

    public TimePeriod(String name) {
        this.Name = name;
        this.directionOne = new ArrayList<Street>();
        this.directionTwo = new ArrayList<Street>();

    }

    public boolean HasCurrentDiretionStreet() {
        return !this.getCurrentDirectionStreets().isEmpty();
    }

    public Street getCureentStreet() {

        for (Street item : getCurrentDirectionStreets()) {
            if (item.Name.equals(this.CurrentStreetName)) {
                this.CureentStreet = item;
                break;
            }
        }
        if (this.CureentStreet == null) {
            this.CureentStreet = Street.Empty();
        }
        if (this.CureentStreet.getHours().isEmpty()) {
            this.CureentStreet = Street.Empty();
        }
        return this.CureentStreet;
    }



    public void setCureentStreet(String name) {
        this.CurrentStreetName = name;

    }



    public List<Street> getCurrentDirectionStreets() {
        if (currentDirection == DIRECTION_ONE) {
            return this.directionOne;
        } else {
            return this.directionTwo;
        }
    }



    public List<Street> getDirectionOneStreets() {
        return this.directionOne;
    }



    public List<Street> getDirectionTwoStreets() {
        return this.directionTwo;
    }


}
