package com.sumu.googleplay.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Handler;

import com.sumu.googleplay.BaseApplication;

import java.util.Random;

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
        return BaseApplication.getApplication().getResources();
    }

    /**
     * 获取到一个字符数组
     *
     * @param arrayName 字符数组的id
     * @return
     */
    public static String[] getStringArray(int arrayName) {
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

    /**
     * 获取随机的颜色
     *
     * @return
     */
    public static int getColor() {
        Random random = new Random();
        int red = random.nextInt(200)+20;//范围[20,220)，以防出现黑色和白色，看不清的情况
        int green = random.nextInt(200)+20;
        int blue = random.nextInt(200)+20;
        return Color.rgb(red, green, blue);
    }

    /**
     * 获取指定颜色的圆角背景shape
     * @return
     */
    public static Drawable createShape(int color){
        GradientDrawable gradientDrawable=new GradientDrawable();
        gradientDrawable.setCornerRadius(15);
        gradientDrawable.setColor(color);
        return gradientDrawable;
    }

    /**
     * 获取一个状态选择器
     * @param pressed  按下的图案
     * @param normal  正常的图案
     * @return
     */
    public static StateListDrawable getStateListDrawable(Drawable pressed,Drawable normal){
        StateListDrawable stateListDrawable=new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressed);
        stateListDrawable.addState(new int[]{}, normal);
        return stateListDrawable;
    }

    //判断当前的线程是不是在主线程
    public static boolean isRunInMainThread() {
        return android.os.Process.myTid() == getMainThreadId();
    }

    public static void runInMainThread(Runnable runnable) {
        if (isRunInMainThread()) {
            runnable.run();
        } else {
            post(runnable);
        }
    }

    /** 获取主线程的handler */
    public static Handler getHandler() {
        return BaseApplication.getMainThreadHandler();
    }

    /** 延时在主线程执行runnable */
    public static boolean postDelayed(Runnable runnable, long delayMillis) {
        return getHandler().postDelayed(runnable, delayMillis);
    }

    /** 在主线程执行runnable */
    public static boolean post(Runnable runnable) {
        return getHandler().post(runnable);
    }

    public static Thread getMainThread() {
        return BaseApplication.getMainThread();
    }

    public static long getMainThreadId() {
        return BaseApplication.getMainThreadId();
    }

    public static Context getContext() {
        return BaseApplication.getApplication();
    }
}
