package com.sumu.googleplay.adapter;

import android.content.Context;

import com.sumu.googleplay.adapter.holder.BaseViewHolder;
import com.sumu.googleplay.adapter.holder.SubjectViewHolder;
import com.sumu.googleplay.bean.SubjectInfo;

import java.util.List;

/**
 * ==============================
 * 作者：苏幕
 * <p>
 * 时间：2015/11/23   22:59
 * <p>
 * 描述：
 * <p>专题ListView适配器
 * ==============================
 */
public abstract class SubjectAdapter extends DefaultAdapter<SubjectInfo> {


    public SubjectAdapter(Context context, List<SubjectInfo> subjectInfos) {
        super(context, subjectInfos);
    }

    @Override
    public BaseViewHolder getHolder() {
        return new SubjectViewHolder(context);
    }

    @Override
    protected  abstract List<SubjectInfo> getMoreDataFromServer();
}
