package com.sumu.googleplay.fragment;

import android.view.View;

import com.lidroid.xutils.bitmap.PauseOnScrollListener;
import com.sumu.googleplay.adapter.CategoryAdapter;
import com.sumu.googleplay.bean.CategoryInfo;
import com.sumu.googleplay.protocol.CategoryProtocol;
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
 * <p/>分类界面
 * ==============================
 */
public class CategoryFragment extends BaseFragment{

    private List<CategoryInfo> categoryInfos;

    @Override
    protected LoadingPage.LoadResult load() {
        CategoryProtocol categoryProtocol= new CategoryProtocol();
        categoryInfos = categoryProtocol.load(0);
        return checkData(categoryInfos);
    }

    @Override
    protected View createSuccessView() {
        BaseListView listView=new BaseListView(context);
        CategoryAdapter categoryAdapter=new CategoryAdapter(context,categoryInfos, listView);
        categoryAdapter.setIsMoreHolder(false);
        listView.setOnScrollListener(new PauseOnScrollListener(bitmapUtils, false, true));
        listView.setAdapter(categoryAdapter);
        return listView;
    }


}
