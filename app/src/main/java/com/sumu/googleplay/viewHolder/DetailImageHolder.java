package com.sumu.googleplay.viewHolder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.sumu.googleplay.Contacts;
import com.sumu.googleplay.R;
import com.sumu.googleplay.adapter.holder.BaseViewHolder;
import com.sumu.googleplay.bean.DetailAppInfo;

import java.util.List;

/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/11/26   14:10
 * <p/>
 * 描述：
 * <p/>应用详情第三模块 图片展示
 * ==============================
 */
public class DetailImageHolder extends BaseViewHolder{
    public DetailImageHolder(Context context) {
        super(context);
    }
    private ImageView[] imgs;
    @Override
    protected View initView() {
        View view=View.inflate(context,R.layout.detail_screen,null);
        imgs=new ImageView[5];
        imgs[0]= (ImageView) view.findViewById(R.id.screen_1);
        imgs[1]= (ImageView) view.findViewById(R.id.screen_2);
        imgs[2]= (ImageView) view.findViewById(R.id.screen_3);
        imgs[3]= (ImageView) view.findViewById(R.id.screen_4);
        imgs[4]= (ImageView) view.findViewById(R.id.screen_5);
        return view;
    }

    @Override
    public void setDataToView(Object data) {
        DetailAppInfo detailAppInfo= (DetailAppInfo) data;
        List<String> imgsUrl= detailAppInfo.getScreen();
        for (int i=0;i<imgs.length;i++){
            if (i<imgsUrl.size()){
                bitmapUtils.display(imgs[i], Contacts.HOME_IMAGE_URL+imgsUrl.get(i));
                imgs[i].setVisibility(View.VISIBLE);
            }else {
                imgs[i].setVisibility(View.GONE);
            }
        }
    }
}
