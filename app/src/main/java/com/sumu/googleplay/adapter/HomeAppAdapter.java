package com.sumu.googleplay.adapter;

import android.content.Context;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.sumu.googleplay.Contacts;
import com.sumu.googleplay.R;
import com.sumu.googleplay.bean.HomeAppInfo;

import java.util.List;

/**
 * ==============================
 * 作者：苏幕
 * <p>
 * 时间：2015/11/23   18:29
 * <p>
 * 描述：
 * <p>
 * ==============================
 */
public class HomeAppAdapter extends BaseAdapter {
    private Context context;
    private List<HomeAppInfo> homeAppInfos;
    private BitmapUtils bitmapUtils;
    public HomeAppAdapter(Context context, List<HomeAppInfo> homeAppInfos,BitmapUtils bitmapUtils) {
        this.context = context;
        this.homeAppInfos = homeAppInfos;
        this.bitmapUtils=bitmapUtils;
    }

    @Override
    public int getCount() {
        return homeAppInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return homeAppInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=View.inflate(context, R.layout.item_app,null);
            viewHolder.item_icon= (ImageView) convertView.findViewById(R.id.item_icon);
            viewHolder.item_bottom= (TextView) convertView.findViewById(R.id.item_bottom);
            viewHolder.item_rating= (RatingBar) convertView.findViewById(R.id.item_rating);
            viewHolder.item_size= (TextView) convertView.findViewById(R.id.item_size);
            viewHolder.item_title= (TextView) convertView.findViewById(R.id.item_title);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        HomeAppInfo homeAppInfo= (HomeAppInfo) getItem(position);
        viewHolder.item_title.setText(homeAppInfo.getName());
        viewHolder.item_size.setText(Formatter.formatFileSize(context, homeAppInfo.getSize()));
        viewHolder.item_rating.setRating(homeAppInfo.getStars());
        viewHolder.item_bottom.setText(homeAppInfo.getDes());
        bitmapUtils.display(viewHolder.item_icon, Contacts.HOME_IMAGE_URL+homeAppInfo.getIconUrl());
        return convertView;
    }
    static class ViewHolder{
        ImageView item_icon;
        TextView item_title,item_size,item_bottom;
        RatingBar item_rating;
    }
}
