package com.sumu.googleplay.adapter.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sumu.googleplay.Contacts;
import com.sumu.googleplay.R;
import com.sumu.googleplay.bean.SubjectInfo;

/**
 * ==============================
 * 作者：苏幕
 * <p>
 * 时间：2015/11/24   13:33
 * <p>
 * 描述：
 * <p>SubjectAdapter的ViewHolder
 * ==============================
 */
public class SubjectViewHolder extends BaseViewHolder {
    private ImageView item_icon;
    private TextView item_txt;
    public SubjectViewHolder(Context context) {
        super(context);
    }

    @Override
    protected View initView() {
        View convertView=View.inflate(context, R.layout.item_subject,null);
        this.item_txt= (TextView) convertView.findViewById(R.id.item_txt);
        this.item_icon= (ImageView) convertView.findViewById(R.id.item_icon);
        return convertView;
    }

    @Override
    public void setDataToView(Object data) {
        SubjectInfo subjectInfo= (SubjectInfo) data;
        this.item_txt.setText(subjectInfo.getDes());
        bitmapUtils.display(this.item_icon, Contacts.HOME_IMAGE_URL+subjectInfo.getUrl());
    }
}
