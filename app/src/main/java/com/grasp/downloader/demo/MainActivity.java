package com.grasp.downloader.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.grasp.downloader.DownloadTask;
import com.grasp.downloader.Downloader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task = new DownloadTask();
        task.setName("huluwa.apk");
        task.setUrl("http://139.224.228.34/static/hu_lu_wa1.8.apk");
        Downloader.getsInstance(this).addDownloadTask(task);

        DownloadTask task1 = new DownloadTask();
        task1.setName("youmeng-debug.apk");
        task1.setUrl("http://139.224.228.34/static/youmeng-debug.apk");

        Downloader.getsInstance(this).addDownloadTask(task1);
    }
}
