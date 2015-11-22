package com.sumu.googleplay.utils;

import android.content.res.Resources;

import com.sumu.googleplay.BaseApplication;

/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/11/22   21:01
 * <p/>
 * 描述：
 * <p/>
 * ==============================
 */
public class UIUtils {

    public static Resources getResources() {
        return BaseApplication.getBaseApplication().getResources();
    }

    /**
     * 获取到一个字符数组
     * @param arrayName 字符数组的id
     * @return
     */
    public static String[] getStringArray(int arrayName){
        return getResources().getStringArray(arrayName);
    }


    /**
     * dip转px
     * @param dip
     * @return
     */
    public static int dip2px(int dip){
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dip*scale+0.5f);
    }

    /**
     * px转dip
     * @param px
     * @return
     */
    public static int px2dip(int px){
        float scale = getResources().getDisplayMetrics().density;
        return (int) (px/scale+0.5f);
    }
}
