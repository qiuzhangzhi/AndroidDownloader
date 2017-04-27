package com.grasp.downloader.db;

/**
 * Created by qzz on 2017/4/26.
 */

public class DBContract {
    public static abstract class DownloadEntry {
        public static final String TABLE_NAME = "download";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SIZE = "size";
        public static final String COLUMN_DOWNLOADED = "downloaded";
        public static final String COLUMN_STATE = "state";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_PERCENT = "percent";
        public static final String COLUMN_SAVE_ADDRESS = "save_address";
        public static final String COLUMN_ETAG  = "etag";

    }

}
