package com.grasp.downloader;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

/**
 * Created by qzz on 2017/4/24.
 */

public class DefaultDownloadListener implements DownloadListener {

    private static final String TAG = "DefaultDownloadListener";

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
    public void onDownloadProcess(DownloadTask task) {
        Log.d(TAG,"onDownloadProcess:" + task.getId());
        mBuilder.setContentTitle("Progress running...")
                .setContentText("Now running...")
                .setProgress(task.getSize(), task.getDownloaded(), false)
                .setSmallIcon(R.drawable.download)
                .setContentInfo(task.getPercent() + " %");
        mNotificationManager.notify(task.getId(), mBuilder.getNotification());
    }

    @Override
    public void onDownloadCompleted(DownloadTask task) {
        Log.d(TAG,"onDownloadCompleted:" + task.getId());
    }
}
