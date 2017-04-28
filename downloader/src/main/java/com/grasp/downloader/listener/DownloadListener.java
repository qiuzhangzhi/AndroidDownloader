package com.grasp.downloader.listener;

import com.grasp.downloader.core.DownloadTask;

/**
 * Created by qzz on 2017/4/21.
 */

public interface DownloadListener {

    void onDownloadStart(DownloadTask task);

    void onDownloadProgress(DownloadTask task);

    void onDownloadCompleted(DownloadTask task);

    void onDownloadFailed(DownloadTask task);
}
