package com.sumu.googleplay.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.BitmapUtils;
import com.sumu.googleplay.utils.BitmapHelper;
import com.sumu.googleplay.utils.ViewUtils;
import com.sumu.googleplay.view.LoadingPage;

import java.util.List;

/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/11/23   12:26
 * <p/>
 * 描述：
 * <p/> 所有Fragment的基类
 * ==============================
 */
public abstract class BaseFragment extends Fragment {

    protected BitmapUtils bitmapUtils ;
    private LoadingPage loadingPage;
    protected Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bitmapUtils = BitmapHelper.getBitmapUtils(getActivity());
        context=getActivity();
        if (loadingPage == null) {
            loadingPage = new LoadingPage(getActivity()){

                @Override
                protected View createSuccessView() {
                    return BaseFragment.this.createSuccessView();
                }

                @Override
                protected LoadResult load() {
                    return BaseFragment.this.load();
                }
            };// 之前的frameLayout 已经记录了一个爹了  爹是之前的ViewPager
        } else {
            ViewUtils.removeParent(loadingPage);// 移除frameLayout之前的爹
        }
        //show();//根据服务器的数据切换不同的状态(因为需要在每次切换的时候都刷新界面，所有将网络请求放在滑动监听中，这里避免重复请求网络数据)
        return loadingPage;
    }

    public void show() {
        if (loadingPage!=null){
            loadingPage.show();
        }
    }


    /**
     * 当数据成功后所展示的界面
     *
     * @return
     */
    protected abstract View createSuccessView();



    /**
     * 网络请求
     *
     * @return
     */
    protected abstract LoadingPage.LoadResult load();

    //校验数据
    protected LoadingPage.LoadResult checkData(List Datas) {
        if (Datas == null) {
            return LoadingPage.LoadResult.error;//  请求服务器失败
        } else if (Datas.size() > 0) {
            return LoadingPage.LoadResult.success;
        } else {
            return LoadingPage.LoadResult.empty;
        }
    }
}
