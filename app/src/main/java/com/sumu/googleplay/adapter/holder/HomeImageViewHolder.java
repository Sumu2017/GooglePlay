package com.sumu.googleplay.adapter.holder;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sumu.googleplay.adapter.HomeImagePageAdapter;
import com.sumu.googleplay.utils.UIUtils;

/**
 * ==============================
 * 作者：苏幕
 * <p>
 * 时间：2015/11/24   19:45
 * <p>
 * 描述：
 * <p>首页上面的ViewPager显示的ViewHolder
 * ==============================
 */
public class HomeImageViewHolder extends BaseViewHolder{
    private ViewPager viewPager;

    public HomeImageViewHolder(Context context) {
        super(context);
    }

    @Override
    protected View initView() {
        viewPager = new ViewPager(context);
        viewPager.setLayoutParams(new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dip2px(context, 180)));
        return viewPager;
    }

    @Override
    public void setDataToView(Object data) {
        String[] imageUrls= (String[]) data;
        HomeImagePageAdapter homeImagePageAdapter=new HomeImagePageAdapter(context,imageUrls,bitmapUtils);
        viewPager.setAdapter(homeImagePageAdapter);
    }
}
