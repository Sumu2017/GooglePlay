package com.sumu.googleplay.utils;

import android.os.Environment;

import java.io.File;

/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/11/23   16:46
 * <p/>
 * 描述：
 * <p/>
 * ==============================
 */
public class FileUtils {
    private static final String ROOT = "GooglePlay";
    private static final String CACHE = "cache";
    private static final String ICON = "icon";

    /**
     * 获取图片路径
     *
     * @return
     */
    public static File getIconDir() {
        return getDir(ICON);
    }

    /**
     * 获取缓存路径
     *
     * @return
     */
    public static File getCacheDir() {
        return getDir(CACHE);
    }

    public static File getDir(String dir) {
        StringBuilder buffer = new StringBuilder();
        if (isHaveSd()) {
            buffer.append(Environment.getExternalStorageDirectory().getAbsolutePath());
            buffer.append(File.separator);
            buffer.append(ROOT);
            buffer.append(File.separator);//"/"
            buffer.append(dir);
        } else {
            System.out.println("找不到SD卡");
        }
        File file = new File(buffer.toString());
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();//创建文件夹
        }
        return file;
    }

    /**
     * 判断当前是否有SD卡
     *
     * @return
     */
    public static boolean isHaveSd() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
}
