package com.grasp.downloader.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.grasp.downloader.DownloadTask;
import com.grasp.downloader.db.DBContract.DownloadEntry;

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
            synchronized (DownloaderDatabase.class) {
                if (sInstance == null) {
                    sInstance = new DownloaderDatabase(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    public long insertTask(DownloadTask task) {
        long id = -1;
        database.beginTransaction();
        try {
            id = database.insert(DownloadEntry.TABLE_NAME, null, task.toContentValues());
            database.setTransactionSuccessful();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
        return id;
    }

    public void updateTask(DownloadTask task) {
        database.beginTransaction();
        try {
            database.update(DownloadEntry.TABLE_NAME, task.toContentValues(), task.getId() + " = ? ",
                    new String[] {String.valueOf(task.getId())});
            database.setTransactionSuccessful();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
    }

    public Cursor query() {
        Cursor cursor = null;
        database.beginTransaction();
        try {
            cursor = database.query(DownloadEntry.TABLE_NAME, null, null, null, null, null, null);
            database.setTransactionSuccessful();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
        return cursor;
    }


}
