package com.grasp.downloader;

/**
 * Created by qzz on 2017/4/19.
 */

public class FileUtils {

    private final static String baseFolder = "/sdcard/downloader";
    public static String getAddress(String file){
        return baseFolder +"/"+file;
    }
}
