package com.capam.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by acer on 7/11/13.
 */

public class UdacityDB extends SQLiteOpenHelper
{

    public static final String KEY_ROWID = "_id";
    public static final String KEY_CODE = "code";
    public static final String KEY_TITLE = "title";
    public static final String KEY_VIDEO = "video";
    public static final String KEY_PARENT = "parent";
    public static final String KEY_WATCHED = "watched";
    public static final String KEY_METADATA = "metadata";
    public static final String KEY_REQUEST_STATUS = "status";
    private static final String TAG = "DBAdapter";

    public static final String DATABASE_NAME = "udacity";
    public static final String DATABASE_TABLE = "lectures";
    private static final int DATABASE_VERSION = 1;

    private static final class RequestStatus {
        private String GET_STARTED = "GET_STARTED";
        private String GET_DONE = "GET_DONE";
    };

    private static final String DATABASE_CREATE =
            "create table lectures (_id integer primary key autoincrement, "
                    + "code text not null, title text not null, video text "
                    + "parent text not null, watched integer not null default 0"
                    + "status text, metadata text not null);";
    UdacityDB(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion)
    {
        Log.w(TAG, "Upgrading database from version " + oldVersion
                + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS lectures");
        onCreate(db);
    }
}

/*    //---opens the database---
    public UdacityDB open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }

    //---insert a title into the database---
    public long insertTitle(String code, String title, String parent, String video,
                            Integer watched, String metaData, String status)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_CODE, code);
        initialValues.put(KEY_TITLE, title);
        initialValues.put(KEY_VIDEO, video);
        initialValues.put(KEY_PARENT, parent);
        initialValues.put(KEY_WATCHED, watched);
        initialValues.put(KEY_METADATA, metaData);
        initialValues.put(KEY_REQUEST_STATUS, status);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular title---
    public boolean deleteTitle(long rowId)
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID +
                "=" + rowId, null) > 0;
    }

    //---retrieves all the titles---
    public Cursor getAllTitles()
    {
        return db.query(DATABASE_TABLE, new String[] {
                KEY_ROWID,
                KEY_CODE,
                KEY_TITLE,
                KEY_VIDEO,
                KEY_PARENT,
                KEY_WATCHED,
                KEY_METADATA,
                KEY_REQUEST_STATUS},
                null,
                null,
                null,
                null,
                null);
    }

    //---retrieves a particular title---
    public Cursor getTitle(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {
                        KEY_ROWID,
                        KEY_CODE,
                        KEY_TITLE,
                        KEY_VIDEO,
                        KEY_PARENT,
                        KEY_WATCHED,
                        KEY_METADATA,
                        KEY_REQUEST_STATUS
                },
                        KEY_ROWID + "=" + rowId,
                        null,
                        null,
                        null,
                        null,
                        null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a title---
    public boolean updateTitle(long rowId, String code,String title, String video,
                               String parent, Integer watched, String metaData, String status)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_CODE, code);
        args.put(KEY_TITLE, title);
        args.put(KEY_VIDEO, video);
        args.put(KEY_PARENT, parent);
        args.put(KEY_WATCHED, watched);
        args.put(KEY_METADATA, metaData);
        args.put(KEY_REQUEST_STATUS, status);
        return db.update(DATABASE_TABLE, args,
                KEY_ROWID + "=" + rowId, null) > 0;
    }*/
//}