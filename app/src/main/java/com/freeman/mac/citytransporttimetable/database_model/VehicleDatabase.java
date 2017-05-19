package com.freeman.mac.citytransporttimetable.database_model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.freeman.mac.citytransporttimetable.model.*;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Mac on 14. 5. 2017.
 */

public class VehicleDatabase {

    private static final String TAG = "citytransporttimetable";

    private  VehicleDaoHelper masterHelper;

    private DbTrafficDataDao trafficDataDao;
    private DbVehicleDescriptionItemDao vehicleDescriptionItemDao;
    private DbStreetDao streetDao;
    private DbNextStreetInfoDao nextStreetInfoDao;
    private DbTimePeriodDao timePeriodDao;
    private DbHourMappingDao hourMappingDao;
    private DbMinuteMappingDao minuteMappingDao;
    private DbSignDao signsDao;



    public boolean getNeedToRefill (){
        return  masterHelper.getNeedToRefill();}

    public  void setupDb(Context context)
    {

        masterHelper = new VehicleDaoHelper(context,VehicleDaoHelper.DB_NAME, null); //create database db file if not exist
        SQLiteDatabase db = masterHelper.getWritableDatabase();  //get the created database db file
        DaoMaster master = new DaoMaster(db);//create masterDao
        DaoSession masterSession =master.newSession(); //Creates Session session

        trafficDataDao = masterSession.getDbTrafficDataDao();
        vehicleDescriptionItemDao = masterSession.getDbVehicleDescriptionItemDao();
        streetDao = masterSession.getDbStreetDao();
        nextStreetInfoDao = masterSession.getDbNextStreetInfoDao();
        timePeriodDao = masterSession.getDbTimePeriodDao();
        hourMappingDao = masterSession.getDbHourMappingDao();
        minuteMappingDao = masterSession.getDbMinuteMappingDao();
        signsDao = masterSession.getDbSignDao();

        if (this.getNeedToRefill())
        {
            Log.w(TAG, "setupDb: NeedToRefill");
        }


    }


    public  void SaveToDatabase(TrafficData data, Integer vehicleNumber)
    {
        masterHelper.getWritableDatabase().beginTransaction();
        Log.v(TAG, "Start SaveToDatabase: " + vehicleNumber.toString() );
        DbTrafficData dbTrafficData  = new DbTrafficData();
        dbTrafficData.setId((long)vehicleNumber);
        dbTrafficData.setDirectionOneName(data.DirectionOneName);
        dbTrafficData.setDirectionTwoName(data.DirectionTwoName);
        trafficDataDao.insert(dbTrafficData);

        this.SaveVehicleDescriptionItemToDataBase(data,dbTrafficData);
        this.SaveDirectionToDataBase(data.DirectionOne, Vehicle.DIRECTION_ONE, dbTrafficData, vehicleNumber, data.DirectionOneName);
        this.SaveDirectionToDataBase(data.DirectionTwo, Vehicle.DIRECTION_TWO, dbTrafficData,vehicleNumber, data.DirectionTwoName);
        Log.v(TAG, "End SaveToDatabase: " + vehicleNumber.toString() );
        masterHelper.getWritableDatabase().setTransactionSuccessful();
        masterHelper.getWritableDatabase().endTransaction();
    }


    private  void  SaveVehicleDescriptionItemToDataBase(TrafficData trafficData, DbTrafficData dbTrafficData)
    {
        for (VehicleDescriptionItem des :trafficData.Descriptions) {
            DbVehicleDescriptionItem dbDes = new DbVehicleDescriptionItem();
            dbDes.setDbTrafficData(dbTrafficData);
            dbDes.setText(des.Text);
            dbDes.setSign(des.Sign);
            vehicleDescriptionItemDao.insert(dbDes);
        }

    }



    private  void  SaveDirectionToDataBase(List<Street> streets,
                                           Integer directionType,
                                           DbTrafficData trafficData,
                                           int vehicleNumber,
                                           String directionName)
    {


        for (Street street:streets) {
            DbStreet dbStreet = new DbStreet();
            dbStreet.setDbTrafficData(trafficData);

            dbStreet.setCircleDirectionName(StringUtils.Empty);
            dbStreet.setName(street.Name);
            dbStreet.setDirectionType(directionType);
            dbStreet.setRequestStop(street.RequestStop);
            dbStreet.setRequestStop(street.RequestStop);
            streetDao.insert(dbStreet);

            if (!StringUtils.isNullOrEmpty(street.CircleDirectionName))
            {
                directionName = street.CircleDirectionName;
            }

            this.SaveNextStreetInfoToDatabase(street, dbStreet);
            this.SaveTimePeriodToDatabase(street, dbStreet, vehicleNumber, directionName, street.Name);


        }
    }



    private  void  SaveNextStreetInfoToDatabase(Street street, DbStreet dbStreet)
    {

        for (NextStreetInfo info : street.nextStreets) {
            DbNextStreetInfo dbNextStreetInfo = new DbNextStreetInfo();
            dbNextStreetInfo.setDbStreet(dbStreet);
            dbNextStreetInfo.setName(info.Name);
            dbNextStreetInfo.setDuration(info.Duration);
            nextStreetInfoDao.insert(dbNextStreetInfo);
        }


    }



    private  void  SaveTimePeriodToDatabase(Street street,
                                            DbStreet dbStreet,
                                            int vehicleNumber,
                                            String directionName,
                                            String streetName)
    {
        for (TimePeriod timePeriod:street.TimePeriods) {
            DbTimePeriod dbTimePeriod = new DbTimePeriod();
            dbTimePeriod.setName(timePeriod.Name);
            dbTimePeriod.setType(TimePeriod.getTimePeriodType(timePeriod.Name));
            dbTimePeriod.setDbStreet(dbStreet);
            timePeriodDao.insert(dbTimePeriod);

            this.SaveHourMappingToDatabase(timePeriod,
                                           dbTimePeriod,
                                           vehicleNumber,
                                           dbTimePeriod.getType(),
                                           directionName,
                                           streetName);
        }

    }



    private void SaveHourMappingToDatabase(TimePeriod timePeriod,
                                           DbTimePeriod dbTimePeriod,
                                           int vehicleNumber,
                                           int timePeriodType,
                                           String directionName,
                                           String streetName)
    {
        for (HourMapping hourMapping: timePeriod.Hours) {
            DbHourMapping dbHourMapping  = new DbHourMapping();
            dbHourMapping.setHour(hourMapping.Hour);
            dbHourMapping.setDbTimePeriod(dbTimePeriod);
            hourMappingDao.insert(dbHourMapping);

            this.SaveMinuteMappingToDatabase(hourMapping,
                                             dbHourMapping,
                                             vehicleNumber,
                                             timePeriodType,
                                             directionName,
                                             streetName );
        }
    }



    private void SaveMinuteMappingToDatabase(HourMapping hourMapping,
                                             DbHourMapping dbHourMapping,
                                             int vehicleNumber,
                                             int timePeriodType,
                                             String directionName,
                                             String streetName)
    {
        for (MinuteMapping minuteMapping:hourMapping.getMinutes())
        {
            DbMinuteMapping dbMinuteMapping = new DbMinuteMapping();
            dbMinuteMapping.setMinute(minuteMapping.Minute);
            dbMinuteMapping.setHour(hourMapping.Hour);
            dbMinuteMapping.setDirectionName(directionName);
            dbMinuteMapping.setVehicleNumber(vehicleNumber);
            dbMinuteMapping.setTimePeriodType(timePeriodType);
            dbMinuteMapping.setStreetName(streetName);
            dbMinuteMapping.setDbHourMapping(dbHourMapping);
            minuteMappingDao.insert(dbMinuteMapping);
            Log.w(TAG, "SaveMinuteMappingToDatabase: " + vehicleNumber + " " + streetName + " hour " + hourMapping.Hour + " min " + minuteMapping.Minute);
            this.SaveSignsToDatabase(minuteMapping,dbMinuteMapping);

        }

    }




    private void SaveSignsToDatabase(MinuteMapping minuteMapping, DbMinuteMapping dbMinuteMapping)
    {
        for (String sign:minuteMapping.Signs) {
            DbSign dbSign = new DbSign();
            dbSign.setValue(sign);
            dbSign.setDbMinuteMapping(dbMinuteMapping);
            signsDao.insert(dbSign);
        }

    }





    public void LoadFromDatabase(Vehicle vehicle, TrafficData data ,Integer vehicleNumber){

        masterHelper.getWritableDatabase().beginTransaction();
        List<DbTrafficData>  dbTrafficDataList  = trafficDataDao.queryBuilder().where(DbTrafficDataDao.Properties.Id.eq(vehicleNumber)).list();

        if (!dbTrafficDataList.isEmpty())
        {

            data.DirectionOneName = dbTrafficDataList.get(0).getDirectionOneName();
            data.DirectionTwoName = dbTrafficDataList.get(0).getDirectionTwoName();
            this.loadDescriptions(data,dbTrafficDataList.get(0));
            this.loadStreets(vehicle,data,dbTrafficDataList.get(0));

        }
        masterHelper.getWritableDatabase().endTransaction();


    }


    private  void  loadDescriptions(TrafficData data, DbTrafficData dbData)
    {

        for (DbVehicleDescriptionItem dbDes :dbData.getDbVehicleDescriptionItemList()) {

            VehicleDescriptionItem des = new VehicleDescriptionItem(dbDes.getSign(),dbDes.getText());
            data.Descriptions.add(des);
        }
    }

    private  void  loadStreets(Vehicle vehicle,TrafficData data, DbTrafficData dbData)
    {
        for (DbStreet dbStreet:dbData.getDbStreetList()) {

            Street street = new Street(vehicle,dbStreet.getName());
            street.RequestStop = dbStreet.getRequestStop();
            street.CircleDirectionName = dbStreet.getCircleDirectionName();

            if(dbStreet.getDirectionType() == Vehicle.DIRECTION_ONE)
            {
                data.DirectionOne.add(street);
            }else
            {
                data.DirectionTwo.add(street);
            }
            this.loadTimePeriods(street,dbStreet);
            this.loadNextStreetInfo(street,dbStreet);
        }

    }

    private  void  loadNextStreetInfo(Street street,DbStreet dbStreet)
    {
        for (DbNextStreetInfo dbNextStreetInfo:dbStreet.getDbNextStreetInfoList()) {
            NextStreetInfo nextStreetInfo = new NextStreetInfo();
            nextStreetInfo.Duration = dbNextStreetInfo.getDuration();
            nextStreetInfo.Name = dbNextStreetInfo.getName();
            street.nextStreets.add(nextStreetInfo);
        }
    }


    private  void  loadTimePeriods(Street street,DbStreet dbStreet)
    {
        street.TimePeriods.clear();
        List<DbTimePeriod> list = dbStreet.getDbTimePeriodList();
        for (DbTimePeriod dbTimePeriod:list) {
            TimePeriod timePeriod = new TimePeriod(dbTimePeriod.getName());
            street.TimePeriods.add(timePeriod);
            this.loadHourMapping(timePeriod,dbTimePeriod);
        }
    }

    private void loadHourMapping(TimePeriod timePeriod, DbTimePeriod dbTimePeriod)
    {
        List<DbHourMapping> list = dbTimePeriod.getDbHourMappingList();
        for (DbHourMapping dbHourMapping:list) {
            HourMapping hourMapping = new HourMapping();
            hourMapping.Hour = dbHourMapping.getHour();
            timePeriod.Hours.add(hourMapping);
            this.loadMinuteMapping(hourMapping, dbHourMapping);
        }
    }

    private  void   loadMinuteMapping( HourMapping hourMapping, DbHourMapping dbHourMapping)
    {
        List<DbMinuteMapping> list = dbHourMapping.getDbMinuteMappingList();
        for (DbMinuteMapping dbMinuteMapping:list) {
            MinuteMapping minuteMapping =  new MinuteMapping();
            minuteMapping.Minute = dbMinuteMapping.getMinute();
            hourMapping.getMinutes().add(minuteMapping);
           // Log.w("DATA",dbMinuteMapping.getStreetName() + "T " +  dbMinuteMapping.getTimePeriodType().toString() + "H "  + dbMinuteMapping.getHour().toString() + "M "+ dbMinuteMapping.getMinute().toString() + "V " + dbMinuteMapping.getVehicleNumber().toString() );
            this.loadSigns(minuteMapping,dbMinuteMapping);
        }
    }

    private  void loadSigns(MinuteMapping minuteMapping, DbMinuteMapping dbMinuteMapping)
    {
        for (DbSign dbSign:dbMinuteMapping.getDbSignList()) {
            minuteMapping.Signs.add(dbSign.getValue());
        }
    }














}
