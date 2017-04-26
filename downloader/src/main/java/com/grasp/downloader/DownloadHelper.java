package com.grasp.downloader;

/**
 * Created by qzz on 2017/4/26.
 */

public class DownloadHelper {

    public static DownloadTask getDownloadTask(DownloadTask downloadTask) {
        downloadTask.setId(mId.getAndIncrement());
        if (downloadTask.getListener() == null) {
            downloadTask.setListener(new DefaultDownloadListener(context));
        }
    }
}
