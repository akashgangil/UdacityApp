package com.capam.database;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by acer on 7/11/13.
 */
public class LectureProvider extends ContentProvider {

    private static final String AUTHORITY = "com.capam.udacity.LectureProvider";
    public static final int LECTURES = 0;
    public static final int LECTURE_CODE = 1;
    private static final String LECTURES_BASE_PATH = "lectures";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + LECTURES_BASE_PATH);

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY, LECTURES_BASE_PATH, LECTURES);
        sURIMatcher.addURI(AUTHORITY, LECTURES_BASE_PATH + "/#", LECTURE_CODE);
    }

    private UdacityDB mDB;

    @Override
    public String getType(Uri uri){
        return null;
    }

    @Override
    public boolean onCreate() {
        mDB = new UdacityDB(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(UdacityDB.DATABASE_TABLE);
        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case LECTURE_CODE:
                queryBuilder.appendWhere(UdacityDB.KEY_CODE + "="
                        + uri.getLastPathSegment());
                break;
            case LECTURES:
                // no filter
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }
        Cursor cursor = queryBuilder.query(mDB.getReadableDatabase(),
                projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        if (uriType != LECTURES) {
            throw new IllegalArgumentException("Invalid URI for insert");
        }
        SQLiteDatabase sqlDB = mDB.getWritableDatabase();
        try {
            long newID = sqlDB.insertOrThrow(UdacityDB.DATABASE_TABLE,
                    null, values);
            if (newID > 0) {
                Uri newUri = ContentUris.withAppendedId(uri, newID);
                getContext().getContentResolver().notifyChange(uri, null);
                return newUri;
            } else {
                throw new SQLException("Failed to insert row into " + uri);
            }
        } catch (SQLiteConstraintException e) {
            Log.i("udacity", "Ignoring constraint failure.");

        }
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = mDB.getWritableDatabase();

        int rowsAffected;

        switch (uriType) {
            case LECTURE_CODE:
                String id = uri.getLastPathSegment();
                StringBuilder modSelection = new StringBuilder(UdacityDB.KEY_CODE
                        + "=" + id);

                if (!TextUtils.isEmpty(selection)) {
                    modSelection.append(" AND " + selection);
                }

                rowsAffected = sqlDB.update(UdacityDB.DATABASE_TABLE,
                        values, modSelection.toString(), null);
                break;
            case LECTURES:
                rowsAffected = sqlDB.update(UdacityDB.DATABASE_TABLE,
                        values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsAffected;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = mDB.getWritableDatabase();
        int rowsAffected = 0;
        switch (uriType) {
            case LECTURES:
                rowsAffected = sqlDB.delete(UdacityDB.DATABASE_TABLE,
                        selection, selectionArgs);
                break;
            case LECTURE_CODE:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsAffected = sqlDB.delete(UdacityDB.DATABASE_TABLE,
                            UdacityDB.KEY_CODE+ "=" + id, null);
                } else {
                    rowsAffected = sqlDB.delete(UdacityDB.DATABASE_TABLE,
                            selection + " and " + UdacityDB.KEY_CODE + "=" + id,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown or Invalid URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsAffected;
    }
}
