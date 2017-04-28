package com.grasp.downloader.core;

/**
 * Created by qzz on 2017/4/27.
 */

public class Constants {
    public final static String TAG_PREFIX = "download-";

    public final static String baseFolder = "/sdcard/downloader/";

    /**
     * The default extension for html files if we can't get one at the HTTP level
     */
    public static final String DEFAULT_DL_HTML_EXTENSION = ".html";

    /**
     * The default extension for text files if we can't get one at the HTTP level
     */
    public static final String DEFAULT_DL_TEXT_EXTENSION = ".txt";

    /**
     * The default extension for binary files if we can't get one at the HTTP level
     */
    public static final String DEFAULT_DL_BINARY_EXTENSION = ".bin";

    /**
     * When a number has to be appended to the filename, this string is used to separate the base
     * filename from the sequence number
     */
    public static final String FILENAME_SEQUENCE_SEPARATOR = "-";
}
