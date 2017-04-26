package com.grasp.downloader;

import java.util.concurrent.atomic.AtomicInteger;

import android.content.Context;

/**
 * Created by qzz on 2017/4/19.
 */

public class Downloader {

    private static final String TAG = "Downloader";

    private static Downloader sInstance;

    private AtomicInteger mId = new AtomicInteger(0);

    private Context context;

    public static Downloader getsInstance(Context context) {
        if (sInstance == null) {
            sInstance = new Downloader(context);
        }
        return sInstance;
    }

    private Downloader(Context context) {
        this.context = context;
    }

    public void addDownloadTask(DownloadTask downloadTask) {
        WorkRunnable work = new WorkRunnable(DownloadHelper.getDownloadTask(downloadTask));
        DownloadThreadPool.getsInstance().execute(work);
    }


}
