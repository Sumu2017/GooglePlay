package com.sumu.googleplay;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/11/22   20:58
 * <p/>
 * 描述：
 * <p/>代表当前程序
 * ==============================
 */
public class BaseApplication extends Application{
    /** 全局Context，原理是因为Application类是应用最先运行的，所以在我们的代码调用时，该值已经被赋值过了 */
    private static BaseApplication mInstance;
    /** 主线程ID */
    private static int mMainThreadId = -1;
    /** 主线程ID */
    private static Thread mMainThread;
    /** 主线程Handler */
    private static Handler mMainThreadHandler;
    /** 主线程Looper */
    private static Looper mMainLooper;
    @Override
    public void onCreate() {
        super.onCreate();
        //android.os.Process.myTid()  获取调用进程的id
        //android.os.Process.myUid() 获取 该进程的用户id
        //android.os.Process.myPid() 获取进程的id
        mMainThreadId = android.os.Process.myTid();
        mMainThread = Thread.currentThread();
        mMainThreadHandler = new Handler();
        mMainLooper = getMainLooper();
        mInstance = this;
    }

    public static BaseApplication getApplication() {
        return mInstance;
    }

    /** 获取主线程ID */
    public static int getMainThreadId() {
        return mMainThreadId;
    }

    /** 获取主线程 */
    public static Thread getMainThread() {
        return mMainThread;
    }

    /** 获取主线程的handler */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    /** 获取主线程的looper */
    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }
}
