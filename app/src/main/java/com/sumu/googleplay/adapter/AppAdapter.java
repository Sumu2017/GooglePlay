package com.sumu.googleplay.adapter;

import android.content.Context;
import android.content.Intent;
import android.widget.ListView;

import com.sumu.googleplay.activity.DetailActivity;
import com.sumu.googleplay.adapter.holder.BaseViewHolder;
import com.sumu.googleplay.adapter.holder.HomeViewHolder;
import com.sumu.googleplay.bean.AppInfo;
import com.sumu.googleplay.bean.DownloadInfo;
import com.sumu.googleplay.manager.DownloadManager;
import com.sumu.googleplay.utils.UIUtils;

import java.util.List;


/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/11/23   18:29
 * <p/>
 * 描述：
 * <p>软件列表ListView适配器
 * ==============================
 */
public abstract class AppAdapter extends DefaultAdapter<AppInfo> implements DownloadManager.DownloadObserver{


    public AppAdapter(Context context, List<AppInfo> datas, ListView listView) {
        super(context, datas, listView);
    }

    @Override
    public BaseViewHolder getHolder() {
        return new HomeViewHolder(context);
    }

    @Override
    protected abstract List<AppInfo> getMoreDataFromServer();

    @Override
    public void onMyItemClick(int position) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("packageName", datas.get(position).getPackageName());
        context.startActivity(intent);
    }

    /**
     * 开始监听
     */
    public void startObserver() {
        DownloadManager.getInstance().registerObserver(this);
    }

    /**
     * 停止监听
     */
    public void stopObserver() {
        DownloadManager.getInstance().unRegisterObserver(this);
    }

    @Override
    public void onDownloadProgressed(DownloadInfo info) {
        refreshHolder(info);
    }


    @Override
    public void onDownloadStateChanged(DownloadInfo info) {
        refreshHolder(info);
    }

    /**
     * 刷新界面进度
     * @param info
     */
    private void refreshHolder(final DownloadInfo info) {
        List<BaseViewHolder> displayedHolders=getDisplayedHolders();
        for (int i=0;i<displayedHolders.size();i++){
            BaseViewHolder holder=displayedHolders.get(i);
            if (holder instanceof HomeViewHolder){
                final HomeViewHolder homeViewHolder= (HomeViewHolder) holder;
                AppInfo appInfo=homeViewHolder.getHomeAppInfo();
                if (appInfo.getId()==info.getId()){
                    UIUtils.post(new Runnable() {
                        @Override
                        public void run() {
                            homeViewHolder.refreshState(info.getDownloadState(), info.getProgress());
                        }
                    });
                }
            }
        }
    }
}
