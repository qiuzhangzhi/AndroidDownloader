package com.grasp.downloader;

/**
 * Created by qzz on 2017/4/21.
 */

public interface DownloadListener {

    void onDownloadStart(DownloadTask task);

    void onDownloadProgress(DownloadTask task);

    void onDownloadCompleted(DownloadTask task);

    void onDownloadFailed(DownloadTask task);
}
