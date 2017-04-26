package com.grasp.downloader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.golshadi.majid.report.listener.DownloadManagerListener;

public class MainActivity extends AppCompatActivity implements DownloadManagerListener{

    private static final String TAG ="downloadListener";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        DownloadManagerPro dm = new DownloadManagerPro(this);
//
//        //default config
//        dm.init("downloadmanager/",1,this);
////http://139.224.228.34/static/hu_lu_wa1.8.apk http://192.168.10.56/tools/Diagnosetool.apk
//        int id = dm.addTask("huluwa.apk","http://139.224.228.34/static/hu_lu_wa1.8.apk",false,false);
//
//        try {
//            dm.startDownload(id);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // pause
       // dm.pauseDownload(id);


        DownloadTask task = new DownloadTask();
        task.setName("huluwa.apk");
        task.setUrl("http://192.168.10.56/tools/Diagnosetool.apk");
        Downloader.getsInstance(this).addDownloadTask(task);

        DownloadTask task1 = new DownloadTask();
        task1.setName("youmeng-debug.apk");
        task1.setUrl("http://139.224.228.34/static/youmeng-debug.apk");

        Downloader.getsInstance(this).addDownloadTask(task1);
    }

    @Override
    public void OnDownloadStarted(long taskId) {
        Log.d(TAG,"OnDownloadStarted " + taskId);
    }

    @Override
    public void OnDownloadPaused(long taskId) {
        Log.d(TAG,"OnDownloadPaused " + taskId);
    }

    @Override
    public void onDownloadProcess(long taskId, double percent, long downloadedLength) {
        Log.d(TAG,"OnDownloadPaused " + taskId + " perecent:" + percent + "downloadedLength:" + downloadedLength );
    }

    @Override
    public void OnDownloadFinished(long taskId) {
        Log.d(TAG,"OnDownloadFinished " + taskId);
    }

    @Override
    public void OnDownloadRebuildStart(long taskId) {
        Log.d(TAG,"OnDownloadRebuildStart " + taskId);
    }

    @Override
    public void OnDownloadRebuildFinished(long taskId) {
        Log.d(TAG,"OnDownloadRebuildFinished " + taskId);
    }

    @Override
    public void OnDownloadCompleted(long taskId) {
        Log.d(TAG,"OnDownloadCompleted " + taskId);
    }

    @Override
    public void connectionLost(long taskId) {
        Log.d(TAG,"connectionLost " + taskId);
    }
}
