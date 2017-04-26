package com.grasp.downloader;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

/**
 * Created by qzz on 2017/4/19.
 */

public class WorkRunnable implements Runnable{

    private final String TAG = "WorkRunnable";

    private final int CONNECT_TIME_OUT = 10000;

    private final int FILE_SIZE_NOT_ASSIGN = -1;

    private final int FILE_SIZE_CHUNKED = 0;

    private final int BUFFER_SIZE = 1024;

    private final DownloadTask task;

    private final DownloadListener mListener;

    private byte[] buffer;

    private  HttpURLConnection connection;

    private InputStream inputStream;

    private FileOutputStream outputStream;

    public WorkRunnable(DownloadTask task) {
        this.task = task;
        mListener = task.getListener();
        buffer = new byte[BUFFER_SIZE];
    }

    @Override
    public void run() {
        //TODO wifi chunk 重试次数 相同文件名称重置 重定向 后缀
        try {
            mListener.onDownloadStart(task);

            long bytesSoFar = 0;
            File file = new File(FileUtils.getAddress(task.getName()));
            file.delete();
            if (file.exists()) {
                long fileLength = file.length();
                if (fileLength == 0) {
                    file.delete();
                }
                bytesSoFar = fileLength;
            }

            URL url = new URL(task.getUrl());
            connection = (HttpURLConnection)url.openConnection();

            connection.setConnectTimeout(CONNECT_TIME_OUT);
            if (task.getETag() != null) {
                connection.setRequestProperty("If-Match", task.getETag());
            }
            connection.setRequestProperty("Range", "bytes=" + bytesSoFar + "-");
            connection.connect();

            Log.d(TAG, "ResponseCode:" + connection.getResponseCode());

            if (isResponseSuccess(connection.getResponseCode())) {
                int fileSize = task.getSize();
                String transferEncoding = connection.getHeaderField("Transfer-Encoding");

                if (fileSize == FILE_SIZE_NOT_ASSIGN) {
                    if (transferEncoding != null && transferEncoding.equals("chunked")) {
                        fileSize = FILE_SIZE_CHUNKED;
                    } else {
                        // 版本兼容getContentLengthLong
                        fileSize = connection.getContentLength();
                    }
                    task.setSize(fileSize);
                }

                if (fileSize == FILE_SIZE_NOT_ASSIGN) {
                    mListener.onDownloadFailed(task);
                    return;
                }

                Log.d(TAG,"fileSize:" + fileSize);

                InputStream inputStream = connection.getInputStream();
                FileOutputStream outputStream = new FileOutputStream(file,true);

                int len = 0;
                while ((len = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, len);
                    processIncrement(len);
                }
                outputStream.flush();
                mListener.onDownloadCompleted(task);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            release();
        }
    }

    private boolean isResponseSuccess(int code) {
        if (code == HttpURLConnection.HTTP_OK || code == HttpURLConnection.HTTP_PARTIAL) {
            return true;
        }
        return false;
    }

    private void processIncrement(int len) {
        task.setDownloaded(len);
        mListener.onDownloadProgress(task);
    }

    private void release() {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (outputStream != null) {
                outputStream.close();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

        if (connection != null) {
            connection.disconnect();
        }
    }
}
