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

    @Override
    protected LoadingPage.LoadResult load() {
        appProtocol = new AppProtocol();
        appInfos = appProtocol.load(0);
        return checkData(appInfos);
    }


    @Override
    protected View createSuccessView() {
        BaseListView listView = new BaseListView(context);
        AppAdapter appAdapter = new AppAdapter(context, appInfos,listView) {
            @Override
            protected List<AppInfo> getMoreDataFromServer() {
                return appProtocol.load(appInfos.size());
            }
        };
        listView.setOnScrollListener(new PauseOnScrollListener(bitmapUtils, false, true));
        listView.setAdapter(appAdapter);
        return listView;
    }


}
