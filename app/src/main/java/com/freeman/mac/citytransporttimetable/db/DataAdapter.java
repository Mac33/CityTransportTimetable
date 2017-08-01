package com.freeman.mac.citytransporttimetable.db;

import java.io.IOException;
import java.util.ArrayList;
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



    private Cursor getCursorForVehicleInfo()
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
                "    DB_MINUTE_MAPPING.HOUR = 05 and \n" +
                "    STREET_NAME = \"Štefánikovo námestie\";\n";


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





