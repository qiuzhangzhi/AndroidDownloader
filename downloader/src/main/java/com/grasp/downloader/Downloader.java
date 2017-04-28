package com.grasp.downloader;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import android.content.Context;

import com.grasp.downloader.core.Constants;
import com.grasp.downloader.core.DownloadTask;
import com.grasp.downloader.core.DownloadThreadPool;
import com.grasp.downloader.core.WorkRunnable;
import com.grasp.downloader.db.DownloaderDatabase;

/**
 * Created by qzz on 2017/4/19.
 */

public class Downloader {

    private static final String TAG = Constants.TAG_PREFIX + "Downloader";

    private static Downloader sInstance;

    private Context context;

    private AtomicBoolean isStarted = new AtomicBoolean(false);

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
        init();
        List<DownloadTask> tasks = DownloadTask.cursorToTasks(DownloaderDatabase.getsInstance(context).query());
        for (DownloadTask task : tasks) {
            WorkRunnable work = new WorkRunnable(context, task);
            DownloadThreadPool.getsInstance().execute(work);
        }
    }

    private void init() {
        File baseFolder = new File(Constants.baseFolder);
        if (!baseFolder.exists()) {
            baseFolder.mkdirs();
        }
    }

    public boolean hasDownloadTask() {
        try {
            List<DownloadTask> tasks = DownloadTask.cursorToTasks(DownloaderDatabase.getsInstance(context).query());
            if (tasks != null && tasks.size() > 0) {
                return true;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }

}
