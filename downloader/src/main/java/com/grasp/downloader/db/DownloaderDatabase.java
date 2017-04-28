package com.grasp.downloader.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.grasp.downloader.core.Constants;
import com.grasp.downloader.core.DownloadTask;
import com.grasp.downloader.db.DBContract.DownloadEntry;

/**
 * Created by qzz on 2017/4/27.
 */

public class DownloaderDatabase {
    private static final String TAG = Constants.TAG_PREFIX + "DownloaderDatabase";

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

    public boolean updateTask(DownloadTask task) {
        int value = 0;
        database.beginTransaction();
        try {
            value = database.update(DownloadEntry.TABLE_NAME, task.toContentValues(), DownloadEntry.COLUMN_ID + " = ? ",
                    new String[] {String.valueOf(task.getId())});
            database.setTransactionSuccessful();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
        if (value != 0) {
            return true;
        }
        return false;
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

    public boolean delete(DownloadTask task) {
        long value = 0;
        database.beginTransaction();
        try {
            value = database.delete(DownloadEntry.TABLE_NAME, DownloadEntry.COLUMN_ID + " = ? ",
                    new String[] {String.valueOf(task.getId())});
            database.setTransactionSuccessful();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }

        if (value != 0) {
            return true;
        }
        return false;
    }

}
