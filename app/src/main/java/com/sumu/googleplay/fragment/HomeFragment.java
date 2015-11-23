package com.sumu.googleplay.fragment;

import android.os.Bundle;
import android.view.View;

import com.lidroid.xutils.bitmap.PauseOnScrollListener;
import com.sumu.googleplay.adapter.HomeAppAdapter;
import com.sumu.googleplay.bean.HomeAppInfo;
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
 * <p>
 * ==============================
 */
public class HomeFragment extends BaseFragment {

    private List<HomeAppInfo> homeAppInfos;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        show();//因为第一个Fragment进来时不会走onPageSelected方法，所以需要手动调用请求网络数据
    }

    @Override
    protected LoadingPage.LoadResult load() {
        HomeProtocol homeProtocol = new HomeProtocol();
        homeAppInfos = homeProtocol.load(0);
        return checkData(homeAppInfos);
    }


    @Override
    protected View createSuccessView() {
        BaseListView listView = new BaseListView(getActivity());
        HomeAppAdapter homeAppAdapter = new HomeAppAdapter(getActivity(), homeAppInfos, bitmapUtils);
        // 第二个参数 慢慢滑动的时候是否加载图片 false  加载   true 不加载
        //  第三个参数  飞速滑动的时候是否加载图片  true 不加载
        listView.setOnScrollListener(new PauseOnScrollListener(bitmapUtils, false, true));
        listView.setAdapter(homeAppAdapter);
        return listView;
    }

}
