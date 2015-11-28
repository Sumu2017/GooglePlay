package com.sumu.googleplay.viewHolder;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sumu.googleplay.R;
import com.sumu.googleplay.adapter.holder.BaseViewHolder;
import com.sumu.googleplay.bean.DetailAppInfo;

/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/11/27   11:13
 * <p/>
 * 描述：
 * <p/>应用详情第五模块 下方三个按钮
 * ==============================
 */
public class DetailBottomHolder extends BaseViewHolder {
    public DetailBottomHolder(Context context) {
        super(context);
    }

    @ViewInject(R.id.bottom_favorites)
    private Button bottom_favorites;
    @ViewInject(R.id.bottom_share)
    private Button bottom_share;
    @ViewInject(R.id.progress_btn)
    private Button progress_btn;

    @Override
    protected View initView() {
        View view = View.inflate(context, R.layout.detail_bottom, null);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    public void setDataToView(Object data) {
        DetailAppInfo detailAppInfo = (DetailAppInfo) data;
    }
}
