package com.sumu.googleplay.fragment;

import android.view.View;

import com.lidroid.xutils.bitmap.PauseOnScrollListener;
import com.sumu.googleplay.adapter.AppAdapter;
import com.sumu.googleplay.bean.AppInfo;
import com.sumu.googleplay.protocol.GameProtocol;
import com.sumu.googleplay.view.BaseListView;
import com.sumu.googleplay.view.LoadingPage;

import java.util.List;

/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/11/22   19:39
 * <p/>
 * 描述：
 * <p/>游戏界面
 * ==============================
 */
public class GameFragment extends BaseFragment{

    private List<AppInfo> gameAppInfos;
    private GameProtocol gameProtocol;
    private AppAdapter gameAppAdapter;

    @Override
    protected LoadingPage.LoadResult load() {
        gameProtocol = new GameProtocol();
        gameAppInfos = gameProtocol.load(0);
        return checkData(gameAppInfos);
    }

    @Override
    protected View createSuccessView() {
        BaseListView listView=new BaseListView(context);
        gameAppAdapter = new AppAdapter(context, gameAppInfos,listView) {
            @Override
            protected List<AppInfo> getMoreDataFromServer() {
                return gameProtocol.load(gameAppInfos.size());
            }
        };
        gameAppAdapter.startObserver();
        listView.setOnScrollListener(new PauseOnScrollListener(bitmapUtils,false,true));
        listView.setAdapter(gameAppAdapter);
        return listView;
    }

    /** 可见时，需要启动监听，以便随时根据下载状态刷新页面 */
    @Override
    public void onResume() {
        super.onResume();
        if(gameAppAdapter!=null){
            gameAppAdapter.startObserver();
            gameAppAdapter.notifyDataSetChanged();
        }
    }
    /** 不可见时，需要关闭监听 */
    @Override
    public void onPause() {
        super.onPause();
        if (gameAppAdapter != null) {
            gameAppAdapter.stopObserver();
        }
    }



}
