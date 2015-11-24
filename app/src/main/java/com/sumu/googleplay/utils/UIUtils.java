package com.sumu.googleplay.utils;

import android.content.Context;
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
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);//加上0.5f 为了四舍五入
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
