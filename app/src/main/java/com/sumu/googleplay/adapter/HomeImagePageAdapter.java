package com.sumu.googleplay.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;
import com.sumu.googleplay.Contacts;

/**
 * ==============================
 * 作者：苏幕
 * <p>
 * 时间：2015/11/24   19:53
 * <p>
 * 描述：
 * <p>首页ViewPager图片适配器
 * ==============================
 */
public class HomeImagePageAdapter extends PagerAdapter{
    private String[] imageUrls;
    private Context context;
    private BitmapUtils bitmapUtils;
    public HomeImagePageAdapter(Context context,String[] imageUrls,BitmapUtils bitmapUtils) {
        this.context=context;
        this.imageUrls=imageUrls;
        this.bitmapUtils=bitmapUtils;
    }

    @Override
    public int getCount() {
        return imageUrls.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView=new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        bitmapUtils.display(imageView, Contacts.HOME_IMAGE_URL+imageUrls[position]);
        container.addView(imageView);
        return imageView;
    }
}
