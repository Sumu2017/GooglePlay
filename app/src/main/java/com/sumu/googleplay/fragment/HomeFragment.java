package com.sumu.googleplay.fragment;

import android.os.Bundle;
import android.view.View;

import com.lidroid.xutils.bitmap.PauseOnScrollListener;
import com.sumu.googleplay.adapter.AppAdapter;
import com.sumu.googleplay.viewHolder.HomePicTitleHolder;
import com.sumu.googleplay.bean.AppInfo;
import com.sumu.googleplay.protocol.HomeProtocol;
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
 * <p>首页界面
 * ==============================
 */
public class HomeFragment extends BaseFragment {

    private List<AppInfo> homeAppInfos;
    private HomeProtocol homeProtocol;
    private AppAdapter homeAppAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        show();//因为第一个Fragment进来时不会走onPageSelected方法，所以需要手动调用请求网络数据
    }

    @Override
    protected LoadingPage.LoadResult load() {
        homeProtocol = new HomeProtocol();
        homeAppInfos = homeProtocol.load(0);
        return checkData(homeAppInfos);
    }


    @Override
    protected View createSuccessView() {
        BaseListView listView = new BaseListView(context);
        HomePicTitleHolder homeImageViewHolder=new HomePicTitleHolder(context);
        homeImageViewHolder.setDataToView(homeProtocol.getImageUrl());//将数据传入holder中
        View topView=homeImageViewHolder.getConvertView();//得到holder里面管理的View对象
        listView.addHeaderView(topView);//把holder里的view对象，添加到listView的上面

        homeAppAdapter = new AppAdapter(context, homeAppInfos,listView) {
            @Override
            protected List<AppInfo> getMoreDataFromServer() {
                return homeProtocol.load(homeAppInfos.size());
            }
        };
        homeAppAdapter.startObserver();
        // 第二个参数 慢慢滑动的时候是否加载图片 false  加载   true 不加载
        //  第三个参数  飞速滑动的时候是否加载图片  true 不加载
        listView.setOnScrollListener(new PauseOnScrollListener(bitmapUtils, false, true));
        listView.setAdapter(homeAppAdapter);
        return listView;
    }

    /** 可见时，需要启动监听，以便随时根据下载状态刷新页面 */
    @Override
    public void onResume() {
        super.onResume();
        if(homeAppAdapter!=null){
            homeAppAdapter.startObserver();
            homeAppAdapter.notifyDataSetChanged();
        }
    }
    /** 不可见时，需要关闭监听 */
    @Override
    public void onPause() {
        super.onPause();
        if (homeAppAdapter != null) {
            homeAppAdapter.stopObserver();
        }
    }
}
