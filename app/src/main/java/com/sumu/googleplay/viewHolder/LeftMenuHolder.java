package com.sumu.googleplay.viewHolder;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sumu.googleplay.Contacts;
import com.sumu.googleplay.R;
import com.sumu.googleplay.adapter.holder.BaseViewHolder;
import com.sumu.googleplay.bean.UserInfo;
import com.sumu.googleplay.manager.ThreadManager;
import com.sumu.googleplay.protocol.UserProtocol;

/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/11/25   12:23
 * <p/>
 * 描述：
 * <p/>侧边菜单
 * ==============================
 */
public class LeftMenuHolder extends BaseViewHolder implements View.OnClickListener {
    @ViewInject(R.id.image_photo)
    private ImageView image_photo;
    @ViewInject(R.id.user_name)
    private TextView user_name;
    @ViewInject(R.id.user_email)
    private TextView user_email;
    @ViewInject(R.id.photo_layout)
    private RelativeLayout photo_layout;
    public LeftMenuHolder(Context context) {
        super(context);
    }

    @Override
    protected View initView() {
        View view=View.inflate(context, R.layout.menu_holder,null);
        ViewUtils.inject(this,view);
        photo_layout.setOnClickListener(this);
        return view;
    }

    @Override
    public void setDataToView(Object data) {
        UserInfo userInfo= (UserInfo) data;
        user_name.setText(userInfo.getName());
        user_email.setText(userInfo.getEmail());
        bitmapUtils.display(image_photo, Contacts.HOME_IMAGE_URL+userInfo.getUrl());
    }

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.obj!=null){
                setDataToView(msg.obj);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.photo_layout:
                ThreadManager.getInstance().createLongPool().exexute(new Runnable() {
                    @Override
                    public void run() {
                        UserProtocol userProtocol = new UserProtocol();
                        UserInfo userInfo = userProtocol.load(0);
                        Message.obtain(mHandler, 0, userInfo).sendToTarget();
                    }
                });
                break;
        }
    }
}
