package com.harambeeapps.simpletextadventuregame;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 2ndgengod on 2/3/2021.
 */

class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "textadventure";
    private static final String TABLE_Details = "roomdetails";
    private static final String KEY_ID = "id";
    private static final String KEY_NORTH = "north";
    private static final String KEY_WEST = "west";
    private static final String KEY_EAST = "east";
    private static final String KEY_SOUTH = "south";
    private static final String KEY_DESCRIPTION = "description";
   /* public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }*/
    public DatabaseHelper(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }
    // Called when no database exists in disk and the helper class needs
    // to create a new one.
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE = "CREATE TABLE " + TABLE_Details + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NORTH + " TEXT,"
                + KEY_WEST + " TEXT,"
                + KEY_EAST + " TEXT,"
                + KEY_SOUTH + " TEXT,"
                + KEY_DESCRIPTION + " TEXT"+ ")";
        db.execSQL(CREATE_TABLE);
    }
    // Called when there is a database version mismatch meaning that the version
    // of the database on disk needs to be upgraded to the current version.
    @Override
    public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion)
    {
        // Log the version upgrade.
        Log.w("TaskDBAdapter", "Upgrading from version " +_oldVersion + " to " +_newVersion + ", which will destroy all old data");
        // Upgrade the existing database to conform to the new version. Multiple
        // previous versions can be handled by comparing _oldVersion and _newVersion
        // values.
        // The simplest case is to drop the old table and create a new one.
        _db.execSQL("DROP TABLE IF EXISTS " + "LOGIN");

        // Create a new one.
        onCreate(_db);
    }

    // **** CRUD (Create, Read, Update, Delete) Operations ***** //

    // Adding new User Details
    void insertRoomDetails(String id, String north, String west, String east, String south, String description){
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_ID, id);
        cValues.put(KEY_NORTH, north);
        cValues.put(KEY_EAST, east);
        cValues.put(KEY_WEST, west);
        cValues.put(KEY_SOUTH, south);
        cValues.put(KEY_DESCRIPTION, description);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_Details,null, cValues);
        db.close();
    }
    // Get User Details
    public ArrayList<HashMap<String, String>> GetUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> roomList = new ArrayList<>();
        String query = "SELECT id, north, west, east, south, description FROM "+ TABLE_Details;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> room = new HashMap<>();
            room.put("id",cursor.getString(cursor.getColumnIndex(KEY_ID)));
            room.put("north",cursor.getString(cursor.getColumnIndex(KEY_NORTH)));
            room.put("west",cursor.getString(cursor.getColumnIndex(KEY_WEST)));
            room.put("east",cursor.getString(cursor.getColumnIndex(KEY_EAST)));
            room.put("south",cursor.getString(cursor.getColumnIndex(KEY_SOUTH)));
            room.put("description",cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)));
            roomList.add(room);
        }
        return  roomList;
    }
    // Get User Details based on userid
    public ArrayList<HashMap<String, String>> GetRoomByRoomId(int roomId){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> roomList = new ArrayList<>();
        String query = "SELECT north, west, east, south, description FROM "+ TABLE_Details;
        Cursor cursor = db.query(TABLE_Details, new String[]{KEY_NORTH, KEY_WEST, KEY_EAST, KEY_SOUTH, KEY_DESCRIPTION}, KEY_ID+ "=?",new String[]{String.valueOf(roomId)},null, null, null, null);
        if (cursor.moveToNext()){
            HashMap<String,String> room = new HashMap<>();
            room.put("north",cursor.getString(cursor.getColumnIndex(KEY_NORTH)));
            room.put("west",cursor.getString(cursor.getColumnIndex(KEY_WEST)));
            room.put("east",cursor.getString(cursor.getColumnIndex(KEY_EAST)));
            room.put("south",cursor.getString(cursor.getColumnIndex(KEY_SOUTH)));
            room.put("description",cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)));
            roomList.add(room);
        }
        return  roomList;
    }
    // Delete User Details
    public void DeleteRoom(int roomId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Details, KEY_ID+" = ?",new String[]{String.valueOf(roomId)});
        db.close();
    }
    // Update User Details
    public int UpdateRoomDetails(int id, String north, String west,String east, String south, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();
        cVals.put(KEY_ID, id);
        cVals.put(KEY_NORTH, north);
        cVals.put(KEY_WEST, west);
        cVals.put(KEY_EAST, east);
        cVals.put(KEY_SOUTH, south);
        cVals.put(KEY_DESCRIPTION, description);
        int count = db.update(TABLE_Details, cVals, KEY_ID+" = ?",new String[]{String.valueOf(id)});
        return  count;
    }
}