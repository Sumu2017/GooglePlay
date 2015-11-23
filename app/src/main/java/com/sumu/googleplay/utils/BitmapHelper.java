package com.sumu.googleplay.utils;

import android.content.Context;

import com.lidroid.xutils.BitmapUtils;
import com.sumu.googleplay.R;

/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/11/23   17:40
 * <p/>
 * 描述：
 * <p/>
 * ==============================
 */
public class BitmapHelper {
    private BitmapHelper() {
    }

    private static BitmapUtils bitmapUtils;

    /**
     * BitmapUtils不是单例的 根据需要重载多个获取实例的方法
     * @param context
     * @return
     */
    public static BitmapUtils getBitmapUtils(Context context) {
        if (bitmapUtils == null) {
            // 第二个参数 缓存图片的路径 // 加载图片 最多消耗多少比例的内存 0.05-0.8f
            bitmapUtils = new BitmapUtils(context, FileUtils
                    .getIconDir().getAbsolutePath(), 0.3f);
            bitmapUtils.configDefaultLoadingImage(R.drawable.ic_default);  // 设置如果图片加载中显示的图片
            bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_default);// 加载失败显示的图片
        }
        return bitmapUtils;
    }
}
