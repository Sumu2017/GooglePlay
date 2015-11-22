package com.sumu.googleplay;

import android.app.Application;

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

    private static BaseApplication baseApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication=this;
    }

    public static BaseApplication getBaseApplication() {
        return baseApplication;
    }
}
