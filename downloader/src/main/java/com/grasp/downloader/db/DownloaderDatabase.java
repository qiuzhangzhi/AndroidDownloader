package com.grasp.downloader.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by qzz on 2017/4/27.
 */

public class DownloaderDatabase {
    private static DownloaderDatabase sInstance;

    private DBHelper mDBHelper;

    private SQLiteDatabase database;

    private DownloaderDatabase(Context context) {
        mDBHelper = new DBHelper(context);
        database = mDBHelper.getWritableDatabase();
    }
    public static DownloaderDatabase getsInstance(Context context) {
        if (sInstance == null) {
            synchronized(DownloaderDatabase.class) {
                if (sInstance == null) {
                    sInstance = new DownloaderDatabase(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

}
