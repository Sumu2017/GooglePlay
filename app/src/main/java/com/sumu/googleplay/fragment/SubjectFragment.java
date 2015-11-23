package com.sumu.googleplay.fragment;

import android.view.View;

import com.lidroid.xutils.bitmap.PauseOnScrollListener;
import com.sumu.googleplay.adapter.SubjectAdapter;
import com.sumu.googleplay.bean.SubjectInfo;
import com.sumu.googleplay.protocol.SubjectProtocol;
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
 * <p/>
 * ==============================
 */
public class SubjectFragment extends BaseFragment{

    private List<SubjectInfo> subjectInfos;

    @Override
    protected LoadingPage.LoadResult load() {
        SubjectProtocol subjectProtocol=new SubjectProtocol();
        subjectInfos = subjectProtocol.load(0);
        return checkData(subjectInfos);
    }

    @Override
    protected View createSuccessView() {
        BaseListView listView=new BaseListView(getActivity());
        SubjectAdapter subjectAdapter=new SubjectAdapter(getActivity(),subjectInfos,bitmapUtils);
        listView.setOnScrollListener(new PauseOnScrollListener(bitmapUtils,false,true));
        listView.setAdapter(subjectAdapter);
        return listView;
    }


}
