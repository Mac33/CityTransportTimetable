package com.freeman.mac.citytransporttimetable.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mac on 7. 3. 2017.
 */

public class TransportTimetables {

    private final static TransportTimetables _instance = new TransportTimetables();

    public static TransportTimetables getInstance() {
        return _instance;
    }

    private List<Vehicle> vehicles;

    public List<Vehicle> getVehicles() {return this.vehicles;}

    private Vehicle current = null;

    public Vehicle getCurrentVehicle()
    {
        if (this.current == null)
        {
            this.current = this.getVehicles().get(0);
        }
        return this.current;
    }

    public void setCurrentVehicle(int index)
    {
        this.current = this.getVehicles().get(index);
    }



    private TransportTimetables() {
        this.vehicles = new ArrayList<Vehicle>();
        Vehicle vehicle;
        Street street;
        HourMapping hour;

        vehicle = new Vehicle(4);
        vehicle.addTimePeriod("WorkDays");
        vehicle.addTimePeriod("SchoolDays");
        vehicle.addTimePeriod("Holydays");

        street = new Street("Cyrila a Metoda");
        hour  = new HourMapping(0);
        street.getHours().add(hour);
        hour  = new HourMapping(1);
        street.getHours().add(hour);
        hour  = new HourMapping(2);
        street.getHours().add(hour);
        hour  = new HourMapping(3);
        street.getHours().add(hour);
        hour  = new HourMapping(4);
        street.getHours().add(hour);
        hour  = new HourMapping(5);
        hour.addMinute (10,1);
        hour.addMinute (15,0);
        hour.addMinute (30,1);
        hour.addMinute (50,1);
        hour.addMinute (57,1);
        hour.addMinute (59,1);
        street.getHours().add(hour);
        hour  = new HourMapping(6);
        hour.addMinute (20,1);
        hour.addMinute (35,0);
        hour.addMinute (40,0);
        hour.addMinute (50,0);
        hour.addMinute (59,1);
        street.getHours().add(hour);

        hour  = new HourMapping(7);
        street.getHours().add(hour);
        hour  = new HourMapping(8);
        street.getHours().add(hour);
        hour  = new HourMapping(9);
        street.getHours().add(hour);
        hour  = new HourMapping(10);
        street.getHours().add(hour);
        hour  = new HourMapping(11);
        street.getHours().add(hour);
        hour  = new HourMapping(12);
        street.getHours().add(hour);
        hour  = new HourMapping(13);
        street.getHours().add(hour);
        hour  = new HourMapping(14);
        street.getHours().add(hour);
        hour  = new HourMapping(15);
        street.getHours().add(hour);
        hour  = new HourMapping(16);
        hour.addMinute (2,1);
        hour.addMinute (8,0);
        hour.addMinute (9,1);
        hour.addMinute (10,1);
        hour.addMinute (26,1);
        hour.addMinute (36,1);
        street.getHours().add(hour);
        hour  = new HourMapping(17);
        street.getHours().add(hour);
        hour  = new HourMapping(18);
        street.getHours().add(hour);
        hour  = new HourMapping(19);
        street.getHours().add(hour);
        hour  = new HourMapping(20);
        street.getHours().add(hour);
        hour  = new HourMapping(21);
        street.getHours().add(hour);
        hour  = new HourMapping(22);
        street.getHours().add(hour);
        hour  = new HourMapping(23);
        street.getHours().add(hour);

        vehicle.getTimePeriods().get(0).getDirectionOneStreets().add(street);


        street = new Street("Kosicka");
        hour  = new HourMapping(0);
        street.getHours().add(hour);
        hour  = new HourMapping(1);
        street.getHours().add(hour);
        hour  = new HourMapping(2);
        street.getHours().add(hour);
        hour  = new HourMapping(3);
        street.getHours().add(hour);
        hour  = new HourMapping(4);
        street.getHours().add(hour);
        hour  = new HourMapping(5);
        street.getHours().add(hour);
        hour  = new HourMapping(6);
        hour.addMinute (5,1);
        hour.addMinute (10,0);
        hour.addMinute (25,1);
        hour.addMinute (32,0);
        hour.addMinute (46,1);

        street.getHours().add(hour);

        hour  = new HourMapping(4);
        hour.addMinute (25,1);
        hour.addMinute (35,0);
        hour.addMinute (38,1);
        hour.addMinute (32,0);
        hour.addMinute (56,1);
        hour.addMinute (58,1);
        street.getHours().add(hour);
        vehicle.getTimePeriods().get(0).getDirectionOneStreets().add(street);

        this.vehicles.add(vehicle);

        vehicle = new Vehicle(199);
        vehicle.addTimePeriod("Period 1");
        vehicle.addTimePeriod("Period 2");
        vehicle.addTimePeriod("Period 3");

        street = new Street("Zeleznicna stanica");
        hour  = new HourMapping(0);
        street.getHours().add(hour);
        hour  = new HourMapping(5);
        street.getHours().add(hour);
        hour  = new HourMapping(10);
        street.getHours().add(hour);
        hour  = new HourMapping(12);
        hour.addMinute (1,1);
        hour.addMinute (25,0);
        hour.addMinute (30,2);
        hour.addMinute (40,0);
        hour.addMinute (57,1);
        hour.addMinute (58,2);
        street.getHours().add(hour);
        vehicle.getTimePeriods().get(0).getDirectionOneStreets().add(street);


        street = new Street("Policia");
        hour  = new HourMapping(0);
        hour.addMinute (1,1);
        hour.addMinute (5,0);
        hour.addMinute (20,2);
        hour.addMinute (33,0);
        hour.addMinute (35,1);
        hour.addMinute (57,2);
        hour.addMinute (58,2);
        street.getHours().add(hour);
        vehicle.getTimePeriods().get(0).getDirectionOneStreets().add(street);


        this.vehicles.add(vehicle);

    }





}
