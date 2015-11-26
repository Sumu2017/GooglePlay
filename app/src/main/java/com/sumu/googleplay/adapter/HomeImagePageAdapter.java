package com.sumu.googleplay.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;
import com.sumu.googleplay.Contacts;

import java.util.LinkedList;

/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/11/24   19:53
 * <p/>
 * 描述：
 * <p>首页ViewPager图片适配器
 * ==============================
 */
public class HomeImagePageAdapter extends PagerAdapter {
    private String[] imageUrls;
    private Context context;
    private BitmapUtils bitmapUtils;
    private LinkedList<ImageView> convertView = new LinkedList<>();

    public HomeImagePageAdapter(Context context, String[] imageUrls, BitmapUtils bitmapUtils) {
        this.context = context;
        this.imageUrls = imageUrls;
        this.bitmapUtils = bitmapUtils;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;//为了ViewPager能无限滑动
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ImageView imageView = (ImageView) object;
        convertView.add(imageView);// 把移除的对象 添加到缓存集合中
        container.removeView(imageView);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int index = position % imageUrls.length;//为了ViewPager能无限滑动,防止position出界
        ImageView imageView = null;
        if (convertView.size() > 0) {//模仿ListView复用简单优化
            imageView = convertView.remove(0);
        } else {
            imageView = new ImageView(context);
        }
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        bitmapUtils.display(imageView, Contacts.HOME_IMAGE_URL + imageUrls[index]);
        container.addView(imageView);
        return imageView;
    }
}
