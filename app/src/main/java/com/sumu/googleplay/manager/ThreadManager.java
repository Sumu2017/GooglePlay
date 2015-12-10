package com.sumu.googleplay.manager;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/11/23   14:28
 * <p/>
 * 描述：
 * <p/>  管理线程池
 * ==============================
 */
public class ThreadManager {

    private ThreadPoolProxy longPool;
    private ThreadPoolProxy shortPool;
    private ThreadPoolProxy mDownloadPool;

    private ThreadManager() {
    }

    private static ThreadManager threadManager;

    public static ThreadManager getInstance() {
        if (threadManager == null) {
            threadManager = new ThreadManager();
        }
        return threadManager;
    }

    // 联网比较耗时
    // 最佳效率的线程数cpu的核数*2+1
    public synchronized ThreadPoolProxy createLongPool() {
        if (longPool == null) {
            longPool = new ThreadPoolProxy(5, 5, 5000L);
        }
        return longPool;
    }

    // 操作本地文件
    public synchronized ThreadPoolProxy createShortPool() {
        if (shortPool == null) {
            shortPool = new ThreadPoolProxy(5, 5, 5000L);
        }
        return shortPool;
    }

    /**
     * 获取下载线程
     */
    public synchronized ThreadPoolProxy getDownloadPool() {
        if (mDownloadPool == null) {
            mDownloadPool = new ThreadPoolProxy(3, 3, 5000L);
        }
        return mDownloadPool;
    }

    /**
     * 线程池
     */
    public class ThreadPoolProxy {
        private ThreadPoolExecutor poolExecutor;
        private int corePoolSize;
        private int maximumPoolSize;
        private long keepAliveTime;

        public ThreadPoolProxy(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
            this.corePoolSize = corePoolSize;
            this.maximumPoolSize = maximumPoolSize;
            this.keepAliveTime = keepAliveTime;
        }


        /***
         * 执行任务
         *
         * @param runnable
         */
        public void exexute(Runnable runnable) {
            if (poolExecutor == null) {
                /**
                 * 1.线程池里面管理多少个线程
                 * 2.如果排队满了, 额外的开的线程数
                 * 3.如果线程池没有要执行的任务 存活多久
                 * 4.时间的单位
                 * 5.如果 线程池里管理的线程都已经用了,剩下的任务 临时存到LinkedBlockingQueue对象中 排队
                 */
                poolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime
                        , TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(10));
            }
            poolExecutor.execute(runnable);// 调用线程池 执行异步任务
        }

        /**
         * 取消任务
         *
         * @param runnable
         */
        public synchronized void cancel(Runnable runnable) {
            //不为空，没有崩溃，没有停止
            if (poolExecutor != null && !poolExecutor.isShutdown() && !poolExecutor.isTerminated()) {
                poolExecutor.remove(runnable);// 取消异步任务
            }
        }
    }
}
