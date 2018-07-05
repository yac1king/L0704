package com.example.administrator.l0704;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import javax.sql.StatementEvent;

public class dbadapter {
    public static final String KEY_ID = "_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_DATE = "date";
    public static final String KEY_COLOR = "color";
    private dbHelper mDbHelper;
    private SQLiteDatabase mdb;
    private final Context mCtx;
    private static final String TABLE_NAME = "memoTable4";
    private Intent i;
    private ContentValues values;
    public dbadapter(Context mCtx) {
        this.mCtx = mCtx;
        open();
    }

    public void open(){
        mDbHelper = new dbHelper(mCtx);
        mdb = mDbHelper.getWritableDatabase();
        Log.i("DB=",mdb.toString());
    }
    public void close(){
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }
    public Cursor listContacts(){
        Cursor mCursor = mdb.query(TABLE_NAME, new String[] {KEY_ID, KEY_TITLE, KEY_CONTENT, KEY_DATE ,KEY_COLOR},null,null,null,null,null);
        if(mCursor != null){
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    public long createMemo(String title,String content,String date, String color) {
        try{

            values = new ContentValues();
            values.put(KEY_TITLE, title);
            values.put(KEY_CONTENT, content);
            values.put(KEY_DATE, date);
            values.put(KEY_COLOR, color);


        }catch(Exception e){
            e.printStackTrace();
        }finally {
            Toast.makeText(mCtx,"新增成功!", Toast.LENGTH_SHORT).show();

        }

        return mdb.insert(TABLE_NAME,null,values);


    }
    public long updateMemo(int id, String title, String content, String date ,String color){
        long update = 0;
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, title);
        values.put(KEY_CONTENT, content);
        values.put(KEY_DATE, date);
        values.put(KEY_COLOR ,color);
        Toast.makeText(mCtx,"更新成功!", Toast.LENGTH_SHORT).show();
        update =mdb.update(TABLE_NAME, values, "_id=" + id,null);
        return update;
    }
    public boolean deleteMemo(int id){
        String[] args = {Integer.toString(id)};
        mdb.delete(TABLE_NAME, "_id= ?",args);
        return true;
    }
    public Cursor queryByTitle(String item_name){
        Cursor mCursor = null;
        mCursor = mdb.query(true, TABLE_NAME, new String[] {KEY_ID, KEY_TITLE, KEY_CONTENT},
                KEY_TITLE + " LIKE '%" + item_name + "%'", null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        return mCursor;
    }
    public Cursor queryById(int item_id){
        Cursor mCursor = null;
        mCursor = mdb.query(true, TABLE_NAME, new String[] {KEY_ID, KEY_TITLE, KEY_CONTENT, KEY_DATE ,KEY_COLOR},
                KEY_ID + " == " + item_id , null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        return mCursor;
    }
}
