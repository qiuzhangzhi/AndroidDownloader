package com.grasp.downloader.core;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by qzz on 2017/4/21.
 */

public class DownloadThreadPool {
    private static final int THREAD_NUMS = 3;
    private final static int DEFAULT_IDLE_SECOND = 5;
    private static DownloadThreadPool sInstance;
    private ThreadPoolExecutor pool;

    public static DownloadThreadPool getsInstance() {
        if (sInstance == null) {
            sInstance = new DownloadThreadPool();
        }
        return sInstance;
    }

    private DownloadThreadPool() {
        pool = new ThreadPoolExecutor(THREAD_NUMS, THREAD_NUMS,
                DEFAULT_IDLE_SECOND, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new DefaultThreadFactory());
        pool.allowCoreThreadTimeOut(true);
    }

    private static class DefaultThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        DefaultThreadFactory() {
            group = Thread.currentThread().getThreadGroup();
            namePrefix = "download-" +
                    poolNumber.getAndIncrement() + "-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }

    public void execute(Runnable task) {
        pool.execute(task);
    }
}
