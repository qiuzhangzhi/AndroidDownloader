package com.grasp.downloader;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import com.grasp.downloader.db.DBContract.*;
/**
 * Created by qzz on 2017/4/19.
 */

public class DownloadTask {

    private long id;

    private String name;

    private long size = -1;

    private long downloaded;

    private int state;

    private String url;

    private int percent;

    private String saveAddress;

    private DownloadListener listener;

    private String ETag;

    // TODO ADD TYPE

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getDownloaded() {
        return downloaded;
    }

    public void setDownloaded(long downloaded) {
        this.downloaded += downloaded;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public String getSaveAddress() {
        return saveAddress;
    }

    public void setSaveAddress(String saveAddress) {
        this.saveAddress = saveAddress;
    }

    public DownloadListener getListener() {
        return listener;
    }

    public void setListener(DownloadListener listener) {
        this.listener = listener;
    }

    public String getETag() {
        return ETag;
    }

    public void setETag(String ETag) {
        this.ETag = ETag;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        try {
            values.put(DownloadEntry.COLUMN_NAME, this.getName());
            values.put(DownloadEntry.COLUMN_URL, this.getUrl());
            values.put(DownloadEntry.COLUMN_DOWNLOADED,this.getDownloaded());
            values.put(DownloadEntry.COLUMN_ETAG, this.getETag());
            values.put(DownloadEntry.COLUMN_PERCENT, this.getPercent());
            values.put(DownloadEntry.COLUMN_SIZE, this.getSize());
            values.put(DownloadEntry.COLUMN_STATE, this.getState());
            values.put(DownloadEntry.COLUMN_SAVE_ADDRESS, this.getSaveAddress());
        } catch (Throwable e) {
            e.printStackTrace();
        }

       return values;
    }

    public static List<DownloadTask> cursorToTasks(Cursor cr) {

        List<DownloadTask> tasks = new ArrayList<DownloadTask>();
        while (cr.moveToNext()) {
            DownloadTask task = new DownloadTask();
            task.id = cr.getInt(cr.getColumnIndex(DownloadEntry.COLUMN_ID));
            task.name = cr.getString(cr.getColumnIndex(DownloadEntry.COLUMN_NAME));
            task.size = cr.getLong(cr.getColumnIndex(DownloadEntry.COLUMN_SIZE));
            task.state = cr.getInt(cr.getColumnIndex(DownloadEntry.COLUMN_STATE));
            task.url = cr.getString(cr.getColumnIndex(DownloadEntry.COLUMN_URL));
            task.percent = cr.getInt(cr.getColumnIndex(DownloadEntry.COLUMN_PERCENT));
            task.ETag = cr.getString(cr.getColumnIndex(DownloadEntry.COLUMN_ETAG));
            task.saveAddress = cr.getString(cr.getColumnIndex(DownloadEntry.COLUMN_SAVE_ADDRESS));
            task.downloaded = cr.getLong(cr.getColumnIndex(DownloadEntry.COLUMN_DOWNLOADED));
            tasks.add(task);
        }
        return tasks;

    }
}
