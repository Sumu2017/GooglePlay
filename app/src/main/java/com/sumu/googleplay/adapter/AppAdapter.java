package com.sumu.googleplay.adapter;

import android.content.Context;

import com.sumu.googleplay.adapter.holder.BaseViewHolder;
import com.sumu.googleplay.adapter.holder.HomeViewHolder;
import com.sumu.googleplay.bean.AppInfo;

import java.util.List;

/**
 * ==============================
 * 作者：苏幕
 * <p>
 * 时间：2015/11/23   18:29
 * <p>
 * 描述：
 * <p>软件列表ListView适配器
 * ==============================
 */
public abstract class AppAdapter extends DefaultAdapter<AppInfo> {


    public AppAdapter(Context context, List<AppInfo> datas) {
        super(context, datas);
    }

    @Override
    public BaseViewHolder getHolder() {
        return new HomeViewHolder(context);
    }

    @Override
    protected abstract List<AppInfo> getMoreDataFromServer();

}
