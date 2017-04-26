package com.grasp.downloader;//package com.getui.download;
//
//import android.app.NotificationManager;
//import android.content.Context;
//import android.os.AsyncTask;
//import android.support.v4.app.NotificationCompat;
//
//import java.util.Random;
//
///**
// * Created by qzz on 2017/4/24.
// */
//
//public class NotificationHelper {
//    private static NotificationHelper nHandler;
//    private static NotificationManager mNotificationManager;
//
//
//    private NotificationHelper () {}
//
//
//    /**
//     * Singleton pattern implementation
//     * @return
//     */
//    public static  NotificationHelper getInstance(Context context) {
//        if(nHandler == null) {
//            nHandler = new NotificationHelper();
//            mNotificationManager =
//                    (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//        }
//
//        return nHandler;
//    }
//
//    /**
//     * Show a determinate and undeterminate progress notification
//     * @param context, activity context
//     */
//    public void createProgressNotification (final Context context, DownloadTask task) {
//
//        // used to update the progress notification
//        final int progresID = new Random().nextInt(1000);
//
//        // building the notification
//        final NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context)
//                .setUsesChronometer(true)
//                .setContentTitle("Progress running...")
//                .setContentText("Now running...")
//                .setProgress(100, i, false)
//                .setSmallIcon(R.drawable.download)
//                .setContentInfo(i + " %");
//
//        // use the same id for update instead created another one
//        mNotificationManager.notify(progresID, nBuilder.build());
//
//        AsyncTask<Integer, Integer, Integer> downloadTask = new AsyncTask<Integer, Integer, Integer>() {
//            @Override
//            protected void onPreExecute () {
//                super.onPreExecute();
//                mNotificationManager.notify(progresID, nBuilder.build());
//            }
//
//            @Override
//            protected Integer doInBackground (Integer... params) {
//                try {
//                    // Sleeps 2 seconds to show the undeterminated progress
//                    Thread.sleep(5000);
//
//                    // update the progress
//                    for (int i = 0; i < 101; i+=5) {
//                        nBuilder
//                                .setContentTitle("Progress running...")
//                                .setContentText("Now running...")
//                                .setProgress(100, i, false)
//                                .setSmallIcon(R.drawable.download)
//                                .setContentInfo(i + " %");
//
//                        // use the same id for update instead created another one
//                        mNotificationManager.notify(progresID, nBuilder.build());
//                        Thread.sleep(500);
//                    }
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                return null;
//            }
//
//
//            @Override
//            protected void onPostExecute (Integer integer) {
//                super.onPostExecute(integer);
//
//                nBuilder.setContentText("Progress finished :D")
//                        .setContentTitle("Progress finished !!")
//                        .setTicker("Progress finished !!!")
//                        .setSmallIcon(R.drawable.accept)
//                        .setUsesChronometer(false);
//
//                mNotificationManager.notify(progresID, nBuilder.build());
//            }
//        };
//
//        // Executes the progress task
//        downloadTask.execute();
//
//        mNotificationManager.notify(progresID, nBuilder.build());
//    }
//}
