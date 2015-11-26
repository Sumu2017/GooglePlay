package com.sumu.googleplay.viewHolder;

import android.content.Context;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sumu.googleplay.Contacts;
import com.sumu.googleplay.R;
import com.sumu.googleplay.adapter.holder.BaseViewHolder;
import com.sumu.googleplay.bean.DetailAppInfo;

/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/11/26   13:43
 * <p/>
 * 描述：
 * <p/> 应用详情第一模块 应用信息
 * ==============================
 */
public class DetailInfoHolder extends BaseViewHolder {
    public DetailInfoHolder(Context context) {
        super(context);
    }

    @ViewInject(R.id.item_icon)
    private ImageView item_icon;
    @ViewInject(R.id.item_title)
    private TextView item_title;
    @ViewInject(R.id.item_rating)
    private RatingBar item_rating;
    @ViewInject(R.id.item_download)
    private TextView item_download;
    @ViewInject(R.id.item_version)
    private TextView item_version;
    @ViewInject(R.id.item_date)
    private TextView item_date;
    @ViewInject(R.id.item_size)
    private TextView item_size;

    @Override
    protected View initView() {
        View view = View.inflate(context, R.layout.detail_app_info, null);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    public void setDataToView(Object data) {
        DetailAppInfo detailAppInfo= (DetailAppInfo) data;
        bitmapUtils.display(item_icon, Contacts.HOME_IMAGE_URL+detailAppInfo.getIconUrl());
        item_title.setText(detailAppInfo.getName());
        item_rating.setRating(detailAppInfo.getStars());
        item_download.setText(context.getString(R.string.item_download)+detailAppInfo.getDownloadNum());
        item_version.setText(context.getString(R.string.item_version)+detailAppInfo.getVersion());
        item_date.setText(context.getString(R.string.item_date)+detailAppInfo.getDate());
        item_size.setText(context.getString(R.string.item_size)+Formatter.formatFileSize(context,detailAppInfo.getSize()));
    }
}
