package com.sumu.googleplay.fragment;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sumu.googleplay.R;
import com.sumu.googleplay.protocol.RecommentProtocol;
import com.sumu.googleplay.utils.UIUtils;
import com.sumu.googleplay.view.LoadingPage;
import com.sumu.googleplay.view.randomLayout.ShakeListener;
import com.sumu.googleplay.view.randomLayout.StellarMap;

import java.util.List;
import java.util.Random;

/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/12/1   11:08
 * <p/>
 * 描述：
 * <p/>推荐界面
 * ==============================
 */
public class RecommentFragment extends BaseFragment{
    private StellarMap mStellarMap;
    private ShakeListener mShakeListener;
    private List<String> datas;
    @Override
    protected LoadingPage.LoadResult load() {
        RecommentProtocol recommentProtocol=new RecommentProtocol();
        datas = recommentProtocol.load(0);
        return checkData(datas);
    }

    @Override
    protected View createSuccessView() {
        mStellarMap = new StellarMap(context);
        // 1.设置内部的TextView距离四周的内边距
        int padding = UIUtils.dip2px(context,15);
        mStellarMap.setInnerPadding(padding, padding, padding, padding);
        mStellarMap.setAdapter(new StellarMapAdapter());
        // 设置默认显示第几组的数据
        mStellarMap.setGroup(0, true);// 这里默认显示第0组,第二个参数表示是否开启动画
        // 设置x和y方向上的显示的密度
        mStellarMap.setRegularity(6, 9);// 如果值设置的过大，有可能造成子View摆放比较稀疏
        mStellarMap.setBackgroundResource(R.drawable.grid_item_bg_normal);
        mShakeListener=new ShakeListener(context);
        mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                int currentGroup = mStellarMap.getCurrentGroup();
                mStellarMap.setGroup((currentGroup + 1) % 2, true);
            }
        });
        return mStellarMap;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mShakeListener!=null){
            mShakeListener.resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mShakeListener!=null){
            mShakeListener.pause();
        }
    }

    class StellarMapAdapter implements StellarMap.Adapter {
        /**
         * 返回多少组数据
         */
        @Override
        public int getGroupCount() {
            return 2;
        }

        /**
         * 每组多少个数据
         */
        @Override
        public int getCount(int group) {
            return 15;
        }

        /**
         * group: 当前是第几组 position:是当前组的position
         */
        @Override
        public View getView(int group, final int position, View convertView) {
            final TextView textView = new TextView(context);
            // 根据group和组中的position计算出对应的在list中的位置
            int listPosition = group * getCount(group) + position;
            textView.setText(datas.get(listPosition));
            // 1.设置随机的字体大小(随机大小)
            Random random = new Random();
            textView.setTextSize(random.nextInt(15) + 14);// 14-29
            textView.setTextColor(UIUtils.getColor());
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,datas.get(position),Toast.LENGTH_SHORT).show();
                }
            });
            return textView;
        }

        /**
         * 虽然定义了，但是并没有什么用
         */
        @Override
        public int getNextGroupOnPan(int group, float degree) {
            return (group + 1) % getGroupCount();
        }

        /**
         * 当前组缩放完成之后下一组加载哪一组的数据 group： 表示当前是第几组
         */
        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            // 0->1->2->0
            return (group + 1) % getGroupCount();
        }

    }


}
