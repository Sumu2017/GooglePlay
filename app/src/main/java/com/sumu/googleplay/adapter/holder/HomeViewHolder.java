package com.sumu.googleplay.adapter.holder;

import android.content.Context;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.sumu.googleplay.Contacts;
import com.sumu.googleplay.R;
import com.sumu.googleplay.bean.AppInfo;

/**
 * ==============================
 * 作者：苏幕
 * <p>
 * 时间：2015/11/24   13:05
 * <p>
 * 描述：
 * <p>HomeAppAdapter的ViewHolder
 * ==============================
 */
public class HomeViewHolder extends BaseViewHolder{
    private ImageView item_icon;
    private TextView item_title,item_size,item_bottom;
    private RatingBar item_rating;

    public HomeViewHolder(Context context) {
        super(context);
    }

    @Override
    protected View initView() {
        View convertView=View.inflate(context, R.layout.item_app,null);
        this.item_icon= (ImageView) convertView.findViewById(R.id.item_icon);
        this.item_bottom= (TextView) convertView.findViewById(R.id.item_bottom);
        this.item_rating= (RatingBar) convertView.findViewById(R.id.item_rating);
        this.item_size= (TextView) convertView.findViewById(R.id.item_size);
        this.item_title= (TextView) convertView.findViewById(R.id.item_title);
        return convertView;
    }

    @Override
    public void setDataToView(Object data) {
        AppInfo homeAppInfo= (AppInfo) data;
        this.item_title.setText(homeAppInfo.getName());
        this.item_size.setText(Formatter.formatFileSize(context, homeAppInfo.getSize()));
        this.item_rating.setRating(homeAppInfo.getStars());
        this.item_bottom.setText(homeAppInfo.getDes());
        bitmapUtils.display(this.item_icon, Contacts.HOME_IMAGE_URL+homeAppInfo.getIconUrl());
    }
}
