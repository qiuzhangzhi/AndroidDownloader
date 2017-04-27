package com.grasp.downloader;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import android.content.Context;

import com.grasp.downloader.db.DownloaderDatabase;

/**
 * Created by qzz on 2017/4/19.
 */

public class Downloader {

    private static final String TAG = "Downloader";

    private static Downloader sInstance;

    private AtomicInteger mId = new AtomicInteger(0);

    private Context context;

    private AtomicBoolean isStarted;

    public static Downloader getsInstance(Context context) {
        if (sInstance == null) {
            sInstance = new Downloader(context);
        }
        return sInstance;
    }

    private Downloader(Context context) {
        this.context = context;
    }

    public long addDownloadTask(DownloadTask downloadTask) {
        launchDownloader();

        downloadTask.setId(DownloaderDatabase.getsInstance(context).insertTask(downloadTask));
        WorkRunnable work = new WorkRunnable(context, downloadTask);
        DownloadThreadPool.getsInstance().execute(work);

        return downloadTask.getId();
    }

    public void launchDownloader() {
        if (isStarted.getAndSet(true)) {
            return;
        }

        List<DownloadTask> tasks = DownloadTask.cursorToTasks(DownloaderDatabase.getsInstance(context).query());
        for (DownloadTask task : tasks) {
            WorkRunnable work = new WorkRunnable(context, task);
            DownloadThreadPool.getsInstance().execute(work);
        }
    }


}
