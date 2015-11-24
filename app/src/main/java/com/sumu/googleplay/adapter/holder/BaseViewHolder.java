package com.sumu.googleplay.adapter.holder;

import android.content.Context;
import android.view.View;

import com.lidroid.xutils.BitmapUtils;
import com.sumu.googleplay.utils.BitmapHelper;

/**
 * ==============================
 * 作者：苏幕
 * <p>
 * 时间：2015/11/24   12:42
 * <p>
 * 描述：
 * <p>所有ViewHolder的基类
 * ==============================
 */
public abstract class BaseViewHolder {
    private View convertView;
    protected Context context;
    protected BitmapUtils bitmapUtils;

    public BaseViewHolder(Context context) {
        this.context=context;
        bitmapUtils= BitmapHelper.getBitmapUtils(context);
        convertView=initView();
        convertView.setTag(this);
    }

    /**
     * 先初始化布局
     * @return
     */
    protected abstract View initView();

    /**
     * 返回复用布局
     * @return
     */
    public View getConvertView() {
        return convertView;
    }

    /**
     * 再将数据设置到布局空间中
     * @param data
     */
    public abstract void setDataToView(Object data);
}
