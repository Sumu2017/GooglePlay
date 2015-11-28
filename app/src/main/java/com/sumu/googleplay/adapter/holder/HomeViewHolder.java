package com.sumu.googleplay.adapter.holder;

import android.content.Context;
import android.text.format.Formatter;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
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
public class HomeViewHolder extends BaseViewHolder implements View.OnClickListener {
    @ViewInject(R.id.item_icon)
    private ImageView item_icon;
    @ViewInject(R.id.item_title)
    private TextView item_title;
    @ViewInject(R.id.item_size)
    private TextView item_size;
    @ViewInject(R.id.item_bottom)
    private TextView item_bottom;
    @ViewInject(R.id.item_rating)
    private RatingBar item_rating;
    @ViewInject(R.id.item_action)
    private RelativeLayout item_action;
    @ViewInject(R.id.action_progress)
    private FrameLayout action_progress;
    @ViewInject(R.id.action_txt)
    private TextView action_txt;

    public HomeViewHolder(Context context) {
        super(context);
    }

    @Override
    protected View initView() {
        View convertView=View.inflate(context, R.layout.item_app,null);
        ViewUtils.inject(this,convertView);
        return convertView;
    }

    @Override
    public void setDataToView(Object data) {
        AppInfo homeAppInfo= (AppInfo) data;
        this.item_title.setText(homeAppInfo.getName());
        this.item_size.setText(Formatter.formatFileSize(context, homeAppInfo.getSize()));
        this.item_rating.setRating(homeAppInfo.getStars());
        this.item_bottom.setText(homeAppInfo.getDes());
        this.item_action.setOnClickListener(this);
        bitmapUtils.display(this.item_icon, Contacts.HOME_IMAGE_URL+homeAppInfo.getIconUrl());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item_action:
                Toast.makeText(context,"下载",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
