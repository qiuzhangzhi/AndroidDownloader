package com.grasp.downloader.listener;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.grasp.downloader.core.Constants;
import com.grasp.downloader.core.DownloadTask;
import com.grasp.downloader.core.WorkRunnable;
import com.grasp.downloader.db.DownloaderDatabase;

import c.comgraspdownloader.R;

/**
 * Created by qzz on 2017/4/24.
 */

public class DefaultDownloadListener implements DownloadListener {

    private static final String TAG = Constants.TAG_PREFIX + "Listener";

    private Context context;

    private NotificationManager mNotificationManager;

    private Notification.Builder mBuilder;

    public DefaultDownloadListener(Context context) {
        this.context = context;
        mNotificationManager = (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new Notification.Builder(context);
    }

    @Override
    public void onDownloadStart(DownloadTask task) {
        Log.d(TAG,"onDownloadStart:" + task.getId());
    }

    @Override
    public void onDownloadProgress(DownloadTask task) {
        Log.d(TAG,"onDownloadProcess:" + task.getId());
        mBuilder.setContentTitle(task.getName())
                .setContentText("downloading...")
                .setSmallIcon(R.drawable.download);
        if (task.getSize() == Constants.FILE_SIZE_CHUNKED) {
            mBuilder.setProgress(0, 0, true);
        } else {
            mBuilder.setProgress((int)task.getSize(), (int)task.getDownloaded(), false);
        }
        mNotificationManager.notify((int)task.getId(), mBuilder.getNotification());
    }

    @Override
    public void onDownloadCompleted(DownloadTask task) {
        Log.d(TAG,"onDownloadCompleted:" + task.getId());
        // 如果不加延时在一些手机上不会更新通知（vivi 华为等）
        try {
            Thread.sleep(1000,0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mBuilder.setContentTitle(task.getName())
                .setContentText("completed")
                .setProgress(0,0,false)
                .setSmallIcon(R.drawable.download);
        mNotificationManager.notify((int)task.getId(), mBuilder.getNotification());
        DownloaderDatabase.getsInstance(context).delete(task);
    }

    @Override
    public void onDownloadFailed(DownloadTask task) {
        mBuilder.setContentTitle(task.getName())
                .setContentText("download failed")
                .setProgress((int)task.getSize(), (int)task.getDownloaded(), false)
                .setSmallIcon(R.drawable.download);
        mNotificationManager.notify((int)task.getId(), mBuilder.getNotification());
    }
}
