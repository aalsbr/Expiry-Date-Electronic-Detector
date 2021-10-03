package com.eded.androidap.listvieweded.mDataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.eded.androidap.listvieweded.mDataObject.Spacecraft;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

//set the connection "Constuctor "
public class DBAdapter {

    Context c;
    SQLiteDatabase db;
    DBHelper helper;

    public DBAdapter(Context c) {
        this.c = c;
        helper=new DBHelper(c);
    }

    //OPEN Connection for the DB
    public void openDB()
    {
        try
        {
            db=helper.getWritableDatabase();
        }catch (SQLException e)
        {

        }
    }
    //CLOSE DB
    public void closeDB()
    {
        try
        {

            helper.close();
        }catch (SQLException e)
        {

        }
    }

    //SAVE
    public boolean add(String name, String date,String state,String finishnum,String epirenum,String ontime,String total,String radio  )
    {
        try
        {
            ContentValues cv=new ContentValues();
            cv.put(Constants.NAME,name);
            cv.put(Constants.DATE,date);
            cv.put(Constants.STATE,state);
            cv.put(Constants.FINISHNUMBER,finishnum);
            cv.put(Constants.EPIRENUMBER,epirenum);
            cv.put(Constants.ONTIMENUMBER,ontime);
            cv.put(Constants.TOTALPRSENTAGE,total);
            cv.put(Constants.RADIO_VALUE,radio);

            long added=db.insert(Constants.TB_NAME,Constants.ROW_ID,cv);
            if(added>0)
            {
                return true;
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    //SELECT A Row from DB
    public Cursor retrieve()
    {
        String[] columns= new String[]{Constants.ROW_ID,Constants.NAME,Constants.DATE, Constants.STATE,
                Constants.FINISHNUMBER,Constants.EPIRENUMBER,Constants.ONTIMENUMBER,Constants.TOTALPRSENTAGE
                ,Constants.RADIO_VALUE};

        Cursor c=db.query(Constants.TB_NAME,columns,null,null,null,null,null);
        return c;
    }

    public Cursor retrieve2()
    {
        String[] columns= new String[]{Constants.ROW_ID2,
                Constants.PRODUCT_NAME,Constants.PRODUCT_NEW_OLD,Constants.PRODUCT_DATE};

        Cursor c=db.query(Constants.TB_NAME2,columns,null,null,null,null,null);
        return c;
    }

    //UPDATE DATE
    public boolean savetotalprsentage(int id,String presentage)
    {
        try
        {
            ContentValues cv=new ContentValues();
            cv.put(Constants.TOTALPRSENTAGE,presentage);
            int updated=db.update(Constants.TB_NAME,cv, Constants.ROW_ID + " =?", new String[]{String.valueOf(id)});
            if(updated>0)
            {
                return true;
            }
        }catch (SQLException e)
        {
             e.printStackTrace();
        }
        return false;
        }


    public boolean updatstatenumbers(int id,String finish ,String expire , String ontime)
    {
        try
        {
            ContentValues cv=new ContentValues();
            cv.put(Constants.FINISHNUMBER,finish);
            cv.put(Constants.EPIRENUMBER,expire);
            cv.put(Constants.ONTIMENUMBER,ontime);


            int updated=db.update(Constants.TB_NAME,cv, Constants.ROW_ID + " =?", new String[]{String.valueOf(id)});
            if(updated>0)
            {
                return true;
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }
    public boolean updatedate(int id,String newdate)
    {
        try
        {
            ContentValues cv=new ContentValues();
            cv.put(Constants.DATE,newdate);

            int updated=db.update(Constants.TB_NAME,cv, Constants.ROW_ID + " =?", new String[]{String.valueOf(id)});
            if(updated>0)
            {
                return true;
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }


    public boolean updatestate(int id,String state)
    {
        try
        {
            ContentValues cv=new ContentValues();
            cv.put(Constants.STATE,state);

            int updated=db.update(Constants.TB_NAME,cv, Constants.ROW_ID + " =?", new String[]{String.valueOf(id)});
            if(updated>0)
            {
                return true;
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    //DELETE DATA
    public boolean delete(int id)
    {
        try
        { int result=db.delete(Constants.TB_NAME,Constants.ROW_ID+" =?",new String[]{String.valueOf(id)});
            if(result>0)
            {
                return true;
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }
    public boolean delete2(int id)
    {
        try
        { int result=db.delete(Constants.TB_NAME2,Constants.ROW_ID2+" =?",new String[]{String.valueOf(id)});
            if(result>0)
            {
                return true;
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }
//add ordered product
    public boolean addorderd(String name, String state_yes_no ,String date )
    {
        try
        {
            ContentValues cv=new ContentValues();
            cv.put(Constants.PRODUCT_NAME,name);
            cv.put(Constants.PRODUCT_DATE,date);
            cv.put(Constants.PRODUCT_NEW_OLD,state_yes_no);
            long added=db.insert(Constants.TB_NAME2,Constants.ROW_ID2,cv);
            if(added>0)
            {
                return true;
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }




}












