package com.sumu.googleplay.viewHolder;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RelativeLayout;

import com.sumu.googleplay.R;
import com.sumu.googleplay.adapter.HomeImagePageAdapter;
import com.sumu.googleplay.adapter.holder.BaseViewHolder;
import com.sumu.googleplay.utils.UIUtils;
import com.sumu.googleplay.view.IndicatorView;

/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/11/24   19:45
 * <p/>
 * 描述：
 * <p>首页上面的ViewPager显示的ViewHolder
 * ==============================
 */
public class HomePicTitleHolder extends BaseViewHolder {
    private ViewPager viewPager;
    private RelativeLayout.LayoutParams params;
    private IndicatorView indicatorView;
    private String[] imageUrls;

    public HomePicTitleHolder(Context context) {
        super(context);
    }

    @Override
    protected View initView() {
        //初始化轮播图最外面的布局
        RelativeLayout mHandView=new RelativeLayout(context);
        //设置轮播图的宽和高
        mHandView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,UIUtils.dip2px(context, 134)));
        viewPager = new ViewPager(context);
        params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        viewPager.setLayoutParams(params);
        //初始化点
        indicatorView = new IndicatorView(context);
        //设置点的背景
        indicatorView.setIndicatorDrawable(UIUtils.getResources().getDrawable(R.drawable.indicator));
        params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置到父控件的右边
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        //设置到父控件的下面
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        //设置左上右下的边距
        params.setMargins(0,0,UIUtils.dip2px(context, 10),UIUtils.dip2px(context, 5));
        indicatorView.setLayoutParams(params);
        //设置点的位置从0开始
        indicatorView.setSelection(0);
        //设置点之间的距离
        indicatorView.setInterval(UIUtils.dip2px(context,2));
        mHandView.addView(viewPager);
        mHandView.addView(indicatorView);
        return mHandView;
    }

    private AutoRunTask autoRunTask;

    @Override
    public void setDataToView(Object data) {
        imageUrls = (String[]) data;
        HomeImagePageAdapter homeImagePageAdapter = new HomeImagePageAdapter(context, imageUrls, bitmapUtils);
        viewPager.setAdapter(homeImagePageAdapter);
        viewPager.setCurrentItem(1000 * imageUrls.length);//设置刚进去就可以往前滑，即将位置指向1000*imagUrls.length位置
        //设置点的个数
        indicatorView.setCount(imageUrls.length);
        autoRunTask = new AutoRunTask();
        autoRunTask.start();
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        autoRunTask.stop();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        autoRunTask.start();
                        break;
                }
                return false;//这里不能返回true,因为ViewPager本事就有监听手势，如果消费拦截的话，ViewPager将无法滑动
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                indicatorView.setSelection(position % imageUrls.length);
            }
        });
    }

    private Handler mHandler = new Handler();

    private class AutoRunTask implements Runnable {
        private boolean flag = false;//记录当前任务是否已经开启

        @Override
        public void run() {
            if (flag) {
                mHandler.removeCallbacks(this);
                int currentItem = viewPager.getCurrentItem();
                currentItem++;
                viewPager.setCurrentItem(currentItem);
                mHandler.postDelayed(this, 2000);//延时发消息,递归调用
            }
        }

        /**
         * 开始轮询
         */
        private void start() {
            if (!flag) {
                mHandler.removeCallbacks(this);
                flag=true;
                mHandler.postDelayed(this, 2000);
            }
        }

        /**
         * 停止轮询
         */
        private void stop() {
            if (flag) {
                flag=false;
                mHandler.removeCallbacks(this);
            }
        }
    }
}
