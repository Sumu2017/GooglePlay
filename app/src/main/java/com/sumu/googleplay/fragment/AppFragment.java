package com.sumu.googleplay.fragment;

import android.view.View;

import com.lidroid.xutils.bitmap.PauseOnScrollListener;
import com.sumu.googleplay.adapter.AppAdapter;
import com.sumu.googleplay.bean.AppInfo;
import com.sumu.googleplay.protocol.AppProtocol;
import com.sumu.googleplay.view.BaseListView;
import com.sumu.googleplay.view.LoadingPage;

import java.util.List;

/**
 * ==============================
 * 作者：苏幕
 * <p>
 * 时间：2015/11/22   19:39
 * <p>
 * 描述：
 * <p/>应用界面
 * ==============================
 */
public class AppFragment extends BaseFragment {

    private List<AppInfo> appInfos;
    private AppProtocol appProtocol;
    private AppAdapter appAdapter;

    @Override
    protected LoadingPage.LoadResult load() {
        appProtocol = new AppProtocol();
        appInfos = appProtocol.load(0);
        return checkData(appInfos);
    }


    @Override
    protected View createSuccessView() {
        BaseListView listView = new BaseListView(context);
        appAdapter = new AppAdapter(context, appInfos,listView) {
            @Override
            protected List<AppInfo> getMoreDataFromServer() {
                return appProtocol.load(appInfos.size());
            }
        };
        appAdapter.startObserver();
        listView.setOnScrollListener(new PauseOnScrollListener(bitmapUtils, false, true));
        listView.setAdapter(appAdapter);
        return listView;
    }
    /** 可见时，需要启动监听，以便随时根据下载状态刷新页面 */
    @Override
    public void onResume() {
        super.onResume();
        if(appAdapter!=null){
            appAdapter.startObserver();
            appAdapter.notifyDataSetChanged();
        }
    }
    /** 不可见时，需要关闭监听 */
    @Override
    public void onPause() {
        super.onPause();
        if (appAdapter != null) {
            appAdapter.stopObserver();
        }
    }
}
