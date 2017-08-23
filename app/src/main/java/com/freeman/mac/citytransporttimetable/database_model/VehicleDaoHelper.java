package com.freeman.mac.citytransporttimetable.database_model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


/**
 * Created by Mac on 14. 5. 2017.
 */


public class  VehicleDaoHelper extends DaoMaster.DevOpenHelper {

    public static String DB_NAME = "trafficData-db_v2";  //Name of Db file in the Device
    public static String OLD_DB_NAME = "trafficData-db_v1";  //Old name of Db file in the Device


    private Boolean needToRefill = false;

    public boolean getNeedToRefill (){
        return  this.needToRefill;}


    public VehicleDaoHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);
        this.needToRefill= true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        this.needToRefill = true;
    }
}