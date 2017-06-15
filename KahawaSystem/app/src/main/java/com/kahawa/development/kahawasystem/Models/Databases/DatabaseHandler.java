package com.kahawa.development.kahawasystem.Models.Databases;

/**
 * Created by Mwatha on 08-Aug-16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.kahawa.development.kahawasystem.Models.Beans.FavModel;

import java.util.ArrayList;
import java.util.List;

public  class DatabaseHandler extends SQLiteOpenHelper {


    //database creation
    private static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "my_orderDB.db";

    public static final String TABLE_FAVORITE = "favorites";
    public static final String COLUMN_FAV_NAME = "FAV_NAME";
    public static final String COLUMN_FAV_DATE = "FAV_DATE";

    public static  final String TABLE_USERS="users";
    public static  final String COLUMN_USER_FNAME="firstname";
    public static  final String COLUMN_USER_LNAME="lastname";
    public static  final String COLUMN_USER_NAME="username";
    public static  final String COLUMN_USER_PASSWORD="password";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FAVS_TABLE = "CREATE TABLE "+TABLE_FAVORITE+"(_id INTEGER PRIMARY KEY autoincrement,FAV_NAME TEXT,FAV_DATE TEXT);";
        String CREATE_USER_TABLE="CREATE TABLE "+TABLE_USERS+"(_id INTEGER PRIMARY KEY autoincrement ,firstname TEXT,lastname TEXT,username TEXT,password TEXT);";
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_FAVS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);
        onCreate(db);
    }


    public void addUser(String firstname, String username, String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
       ContentValues values = new ContentValues();
        values.put(COLUMN_USER_FNAME,firstname);
        values.put(COLUMN_USER_NAME,username);
        values.put(COLUMN_USER_PASSWORD,password);
        // Inserting Row
        db.insert(TABLE_USERS, null, values);
        db.close(); // Closing database connection
    }

    public void insertFavorite(String fav_name,String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FAV_NAME,fav_name);
        values.put(COLUMN_FAV_DATE,date);
        db.insert(TABLE_FAVORITE, null, values);
        db.close(); // Closing database connection
    }


    public Integer delete(String id)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        return  db.delete(TABLE_USERS,"KEY_NAME = ?",new String[] {id});
    }

    public Integer deleteFav()
    {
        SQLiteDatabase db= this.getWritableDatabase();
        return  db.delete(TABLE_FAVORITE,null,null);
    }




    public List<FavModel> getFav(){
        List<FavModel> favModelList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FAVORITE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext())
            {
                FavModel favModel= new FavModel();
                favModel.setFav_foodname(cursor.getString(1));
                favModel.setDate(cursor.getString(2));
                favModelList.add(favModel);
            }
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return favModelList;
    }
}