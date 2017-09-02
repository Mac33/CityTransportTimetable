package com.freeman.mac.citytransporttimetable.db;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.freeman.mac.citytransporttimetable.db.*;


/**
 * Created by Mac on 30. 7. 2017.
 */



public class DataAdapter
{
    protected static final String TAG = "DataAdapter";

    private final Context mContext;
    private SQLiteDatabase mDb;
    private DataBaseHelper mDbHelper;

    public DataAdapter(Context context)
    {
        this.mContext = context;
        mDbHelper = new DataBaseHelper(mContext);
    }

    public DataAdapter createDatabase() throws SQLException
    {
        try
        {
            mDbHelper.createDataBase();
        }
        catch (IOException mIOException)
        {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public DataAdapter open() throws SQLException
    {
        try
        {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "open >>"+ mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close()
    {
        mDbHelper.close();
    }



/*
    public HashMap<Integer,DbMinuteMapping>  getCurrentVehicles()
    {
        HashMap<Integer,DbMinuteMapping> ret = new HashMap<>();

        Cursor cursor = this.getCursorForVehicleInfo();
        if (cursor.moveToFirst()) {
         do{

             DbMinuteMapping item;

             int id = cursor.getInt(cursor.getColumnIndex(DbMinuteMapping.col_ID));
             int vehicleNumber = cursor.getInt(cursor.getColumnIndex(DbMinuteMapping.col_VEHICLE_NUMBER));
             int hour = cursor.getInt(cursor.getColumnIndex(DbMinuteMapping.col_HOUR ));
             int minute = cursor.getInt(cursor.getColumnIndex(DbMinuteMapping.col_MINUTE));
             String directionName = cursor.getString(cursor.getColumnIndex(DbMinuteMapping.col_DIRECTION_NAME ));
             String streetName = cursor.getString(cursor.getColumnIndex(DbMinuteMapping.col_STREET_NAME));
             String sign = cursor.getString(cursor.getColumnIndex(DbMinuteMapping.col_SIGN_VALUE));


             if (ret.containsKey(id))
             {
                 item = ret.get(id);
             }else
             {
                 item = new DbMinuteMapping();
                 item.Id = id;
                 item.VehicleNumber = vehicleNumber;
                 item.Hour = hour;
                 item.Minute = minute;
                 item.DirectionName = directionName;
                 item.StreetName = streetName;
                 ret.put(item.Id,item);

             }

             item.Signs.add(sign);

         }while (cursor.moveToNext());
        }
        cursor.close();

        return ret ;
    }
*/


    public Collection<VehicleSearchItem> getSearchVehiclesByStreetAndTime(Calendar time, String streetName, int timePeriodType)
    {
        HashMap<Integer,VehicleSearchItem> hasMap = new HashMap<>();
        List<VehicleSearchItem>  ret = new ArrayList<>();

        Cursor cursor = this.getSearchVehiclesCursorByStreetAndTime(time,streetName,timePeriodType);
        if (cursor.moveToFirst()) {
            do{

                VehicleSearchItem item = new VehicleSearchItem(cursor);
                if (!hasMap.containsKey(item.Id))
                {
                    hasMap.put(item.Id, item);
                    ret.add(item);
                }
                hasMap.get(item.Id).addSign(cursor);

            }while (cursor.moveToNext());
        }
        cursor.close();
        return ret ;
    }


    private Cursor getSearchVehiclesCursorByStreetAndTime(Calendar time, String streetName, int timePeriodType) {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String starTime = sdf.format(time.getTime());
        time.add(Calendar.HOUR,1);
        String endTime = sdf.format(time.getTime());

        String sql = "select \n"+
                "    DB_MINUTE_MAPPING._id, \n" +
                "    DB_MINUTE_MAPPING.VEHICLE_NUMBER,\n" +
                "    DB_MINUTE_MAPPING.HOUR,\n" +
                "    DB_MINUTE_MAPPING.MINUTE,\n" +
                "    DB_MINUTE_MAPPING.DIRECTION_NAME,\n" +
                "    DB_MINUTE_MAPPING.STREET_NAME,\n" +
                "    DB_SIGN.VALUE AS SIGN_VALUE,\n" +
                "    DB_VEHICLE_DESCRIPTION_ITEM.TEXT AS SIGN_TEXT\n"+
                "from \n" +
                "    DB_MINUTE_MAPPING\n" +
                "Left Join DB_SIGN on DB_MINUTE_MAPPING._id = DB_SIGN.ID_MINUTE_MAPPING\n" +
                "Left Join DB_VEHICLE_DESCRIPTION_ITEM on  DB_MINUTE_MAPPING.VEHICLE_NUMBER = DB_VEHICLE_DESCRIPTION_ITEM.ID_TRAFFIC_DATA and DB_VEHICLE_DESCRIPTION_ITEM.SIGN = SIGN_VALUE\n" +
                "where \n" +
                "    DB_MINUTE_MAPPING.TIME >= '"+ starTime + "' and \n" +
                "    DB_MINUTE_MAPPING.TIME <= '"+ endTime + "' and \n" +
                "    DB_MINUTE_MAPPING.TIME_PERIOD_TYPE = "+ timePeriodType + " and \n" +
                "    DB_MINUTE_MAPPING.STREET_NAME = \"" + streetName + "\" \n" +
                "order by TIME asc;";


        return  this.getCursor(sql);
    }


    private Cursor getCursorForVehicleInfo(int hour, String streetName)
    {
        String sql ="select \n" +
                "    DB_MINUTE_MAPPING._id, \n" +
                "    DB_MINUTE_MAPPING.VEHICLE_NUMBER,\n" +
                "    DB_MINUTE_MAPPING.HOUR,\n" +
                "    DB_MINUTE_MAPPING.MINUTE,\n" +
                "    DB_MINUTE_MAPPING.DIRECTION_NAME,\n" +
                "    DB_MINUTE_MAPPING.STREET_NAME,\n" +
                "    DB_SIGN.VALUE AS SIGN_VALUE\n" +
                "from \n" +
                "    DB_MINUTE_MAPPING, DB_SIGN \n" +
                "where \n" +
                "    DB_MINUTE_MAPPING._id = DB_SIGN.ID_MINUTE_MAPPING and \n" +
                "    DB_MINUTE_MAPPING.HOUR = "+ Integer.toString(hour) + "and \n" +
                "    STREET_NAME = \"" + streetName + "\";\n";


        return  this.getCursor(sql);
    }


    public ArrayList<String> getStreetNames()
    {
        ArrayList<String> ret = new ArrayList<>();

        Cursor cursor = this.getCursorForStreetNames();
        if (cursor.moveToFirst()) {
            do{

                String streetName = cursor.getString(cursor.getColumnIndex("STREET_NAME"));
                ret.add(streetName);

            }while (cursor.moveToNext());
        }
        cursor.close();

        return ret ;
    }


    private Cursor getCursorForStreetNames()
    {
        String sql ="select DISTINCT \n" +
                    "    DB_MINUTE_MAPPING.STREET_NAME\n" +
                    "from \n" +
                    "    DB_MINUTE_MAPPING\n" +
                    "order by DB_MINUTE_MAPPING.STREET_NAME;";

        return  this.getCursor(sql);
    }



    private  Cursor getCursor(String sql)
    {
        try
        {
            return mDb.rawQuery(sql, null);
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }
}





