package com.grasp.downloader.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.grasp.downloader.core.Constants;
import com.grasp.downloader.core.DownloadTask;
import com.grasp.downloader.Downloader;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = Constants.TAG_PREFIX + "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        DownloadTask task = new DownloadTask();
//        task.setName("huluwa.apk");
//        task.setUrl("http://139.224.228.34/static/hu_lu_wa1.8.apk");
//        Downloader.getsInstance(this).addDownloadTask(task);
//
//        DownloadTask task1 = new DownloadTask();
//        task1.setName("youmeng-debug.apk");
//        task1.setUrl("http://139.224.228.34/static/youmeng-debug.apk");
//
//        Downloader.getsInstance(this).addDownloadTask(task1);
        //http://192.168.10.56/tools/Diagnosetool.apk
        if (Downloader.getsInstance(this).hasDownloadTask()) {
            Downloader.getsInstance(this).launchDownloader();
            Log.d(TAG, "has download task");
        } else {
            Log.d(TAG, "no download task");
        }
    }

    public void startDownload(View view) {
        DownloadTask task ;
        switch (view.getId()) {
            case R.id.download1:
                task = new DownloadTask();
                task.setName("Diagnosetool");
                task.setUrl("http://192.168.10.56/tools/Diagnosetool.apk");
                Downloader.getsInstance(this).addDownloadTask(task);
                break;
            case R.id.download2:
                task = new DownloadTask();
                task.setName("youmeng-debug");
                task.setUrl("http://139.224.228.34/static/youmeng-debug.apk");
                Downloader.getsInstance(this).addDownloadTask(task);
                break;
            case R.id.download3:

                break;
            case R.id.download4:
                break;
        }
    }

    public void removeFile(View view) {
        switch (view.getId()) {
            case R.id.delete1:
                File file = new File(Constants.baseFolder);
                for (File sfile : file.listFiles()) {
                    sfile.delete();
                }
                break;
            case R.id.delete2:
                break;
            case R.id.delete3:
                break;
            case R.id.delete4:
                break;
        }
    }
}
