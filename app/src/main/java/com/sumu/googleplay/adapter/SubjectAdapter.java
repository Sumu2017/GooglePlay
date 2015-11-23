package com.sumu.googleplay.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.sumu.googleplay.Contacts;
import com.sumu.googleplay.R;
import com.sumu.googleplay.bean.SubjectInfo;

import java.util.List;

/**
 * ==============================
 * 作者：苏幕
 * <p>
 * 时间：2015/11/23   22:59
 * <p>
 * 描述：
 * <p>专题对象界面适配器
 * ==============================
 */
public class SubjectAdapter extends BaseAdapter {
    private Context context;
    private List<SubjectInfo> subjectInfos;
    private BitmapUtils bitmapUtils;
    public SubjectAdapter(Context context,List<SubjectInfo> subjectInfos,BitmapUtils bitmapUtils) {
        this.context=context;
        this.subjectInfos=subjectInfos;
        this.bitmapUtils=bitmapUtils;
    }

    @Override
    public int getCount() {
        return subjectInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return subjectInfos.get(position);
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
            convertView=View.inflate(context,R.layout.item_subject,null);
            viewHolder.item_txt= (TextView) convertView.findViewById(R.id.item_txt);
            viewHolder.item_icon= (ImageView) convertView.findViewById(R.id.item_icon);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        SubjectInfo subjectInfo= (SubjectInfo) getItem(position);
        viewHolder.item_txt.setText(subjectInfo.getDes());
        bitmapUtils.display(viewHolder.item_icon, Contacts.HOME_IMAGE_URL+subjectInfo.getUrl());
        return convertView;
    }

    static class ViewHolder{
        ImageView item_icon;
        TextView item_txt;
    }
}
