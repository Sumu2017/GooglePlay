package com.sumu.googleplay.viewHolder;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.sumu.googleplay.adapter.HomeImagePageAdapter;
import com.sumu.googleplay.adapter.holder.BaseViewHolder;
import com.sumu.googleplay.utils.UIUtils;

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
public class HomeImageViewHolder extends BaseViewHolder {
    private ViewPager viewPager;

    public HomeImageViewHolder(Context context) {
        super(context);
    }

    @Override
    protected View initView() {
        viewPager = new ViewPager(context);
        viewPager.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dip2px(context, 134)));
        return viewPager;
    }

    private AutoRunTask autoRunTask;

    @Override
    public void setDataToView(Object data) {
        String[] imageUrls = (String[]) data;
        HomeImagePageAdapter homeImagePageAdapter = new HomeImagePageAdapter(context, imageUrls, bitmapUtils);
        viewPager.setAdapter(homeImagePageAdapter);
        viewPager.setCurrentItem(1000 * imageUrls.length);//设置刚进去就可以往前滑，即将位置指向1000*imagUrls.length位置
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
                return false;//这里不能反悔true,因为ViewPager本事就有监听手势，如果消费拦截的话，ViewPager将无法滑动
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
