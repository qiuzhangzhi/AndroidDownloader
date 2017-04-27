package com.grasp.downloader;

import android.os.SystemClock;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.util.Random;

/**
 * Created by qzz on 2017/4/26.
 */

public class DownloadHelper {

    public static Random sRandom = new Random(SystemClock.uptimeMillis());
//    public static DownloadTask getDownloadTask(DownloadTask downloadTask) {
//        downloadTask.setId(mId.getAndIncrement());
//        if (downloadTask.getListener() == null) {
//            downloadTask.setListener(new DefaultDownloadListener(context));
//        }
//    }

    public static String getUniqueFilename(DownloadTask task, String mimeType) {
        String extension = null;
        String filename = task.getName();
        int dotIndex = filename.indexOf('.');
        if (dotIndex < 0) {
            extension = chooseExtensionFromMimeType(mimeType, true);
        } else {
            extension = chooseExtensionFromFilename(mimeType, filename, dotIndex);
            filename = filename.substring(0, dotIndex);
        }

        if (extension != null && extension.equals(".bin")) {
            String str = task.getUrl();
            int urlLastQuestion = task.getUrl().indexOf("?");
            if (urlLastQuestion >= 0)
                str = task.getUrl().substring(0, urlLastQuestion);

            int urlLastDot = str.lastIndexOf(".");
            int urlLastSlash = str.lastIndexOf("/");

            // 需要.和/同时存在，并且.在/后面
            if (urlLastDot >= 0 && urlLastSlash >= 0 && urlLastDot > urlLastSlash) {
                extension = str.substring(urlLastDot);
            }
        }
        filename =FileUtils.getAddress(filename);
        String fullFilename = filename + extension;
        if (!new File(fullFilename).exists()) {
            return fullFilename;
        } else {
            int sequence = 1;
            for (int magnitude = 1; magnitude < 1000000000; magnitude *= 10) {
                for (int iteration = 0; iteration < 9; ++iteration) {
                    fullFilename = filename + sequence + extension;
                    if (!new File(fullFilename).exists()) {
                        return fullFilename;
                    }
                    sequence += sRandom.nextInt(magnitude) + 1;
                }
            }
        }

        return null;
    }
    private static String chooseExtensionFromMimeType(String mimeType, boolean useDefaults) {
        String extension = null;

        if (mimeType != null) {
            extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
            if (extension != null) {
                extension = "." + extension;
            }
        }
        if (extension == null) {
            if (mimeType != null && mimeType.toLowerCase().startsWith("text/")) {
                if (mimeType.equalsIgnoreCase("text/html")) {

                    extension = Constants.DEFAULT_DL_HTML_EXTENSION;
                } else if (useDefaults) {
                    extension = Constants.DEFAULT_DL_TEXT_EXTENSION;
                }
            } else if (useDefaults) {
                extension = Constants.DEFAULT_DL_BINARY_EXTENSION;
            }
        }
        return extension;
    }

    private static String chooseExtensionFromFilename(String mimeType, String filename, int dotIndex) {
        String extension = null;
        if (mimeType != null) {
            int lastDotIndex = filename.lastIndexOf('.');
            String typeFromExt = MimeTypeMap.getSingleton().getMimeTypeFromExtension(filename.substring(lastDotIndex + 1));
            if (typeFromExt == null || !typeFromExt.equalsIgnoreCase(mimeType)) {
                extension = chooseExtensionFromMimeType(mimeType, false);
            }
        }
        if (extension == null) {
            extension = filename.substring(dotIndex);
        }

        return extension;
    }
}
