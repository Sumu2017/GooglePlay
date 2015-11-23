package com.sumu.googleplay.fragment;

import android.view.View;
import android.widget.TextView;

import com.sumu.googleplay.view.LoadingPage;

/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/11/22   19:39
 * <p/>
 * 描述：
 * <p/>
 * ==============================
 */
public class AppFragment extends BaseFragment {
    @Override
    protected View createSuccessView() {
        TextView textView = new TextView(getActivity());
        textView.setText("加载成功。。。。");
        return textView;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.error;
    }

}
