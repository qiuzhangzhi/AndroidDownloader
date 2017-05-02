package com.grasp.downloader.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.grasp.downloader.db.DBContract.*;
/**
 * Created by qzz on 2017/4/26.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;

    private static final String DB_NAME = "download.db";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS "+ DownloadEntry.TABLE_NAME + " ("
                    + DownloadEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + DownloadEntry.COLUMN_NAME + " VARCHAR( 128 ) NOT NULL, "
                    + DownloadEntry.COLUMN_SIZE + " INTEGER, "
                    + DownloadEntry.COLUMN_DOWNLOADED + " INTEGER, "
                    + DownloadEntry.COLUMN_STATE + " INT( 3 ), "
                    + DownloadEntry.COLUMN_URL + " VARCHAR( 256 ), "
                    + DownloadEntry.COLUMN_PERCENT + " INT( 3 ), "
                    + DownloadEntry.COLUMN_SAVE_ADDRESS + " VARCHAR( 256 ),"
                    + DownloadEntry.COLUMN_ETAG + " VARCHAR( 32 )"
                    + " ); ");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
