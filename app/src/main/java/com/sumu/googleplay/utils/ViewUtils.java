package com.sumu.googleplay.utils;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/11/22   23:24
 * <p/>
 * 描述：
 * <p/>
 * ==============================
 */
public class ViewUtils {
    /**
     * 移除布局中的子布局
     * @param view
     */
    public static void removeParent(View view) {
        //  先找到爹 在通过爹去移除孩子
        ViewParent parent = view.getParent();
        //所有的控件 都有爹  爹一般情况下 就是ViewGroup
        if (parent instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) parent;
            group.removeView(view);
        }
    }
}
