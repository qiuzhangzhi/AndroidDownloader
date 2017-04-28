package com.grasp.downloader.listener;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.grasp.downloader.core.Constants;
import com.grasp.downloader.core.DownloadTask;
import com.grasp.downloader.db.DownloaderDatabase;

import c.comgraspdownloader.R;

/**
 * Created by qzz on 2017/4/24.
 */

public class DefaultDownloadListener implements DownloadListener {

    private static final String TAG = Constants.TAG_PREFIX + "Listener";

    private Context context;

    private NotificationManager mNotificationManager;

    private final Notification.Builder mBuilder;

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
                .setProgress((int)task.getSize(), (int)task.getDownloaded(), false)
                .setSmallIcon(R.drawable.download);
        mNotificationManager.notify((int)task.getId(), mBuilder.getNotification());
    }

    @Override
    public void onDownloadCompleted(DownloadTask task) {
        Log.d(TAG,"onDownloadCompleted:" + task.getId());
        mBuilder.setContentText("completed")
                .setProgress(0,0,false)
                .setOngoing(false)
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
