package com.sumu.googleplay.viewHolder;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sumu.googleplay.Contacts;
import com.sumu.googleplay.R;
import com.sumu.googleplay.adapter.holder.BaseViewHolder;
import com.sumu.googleplay.bean.DetailAppInfo;
import com.sumu.googleplay.utils.UIUtils;

import java.util.List;

/**
 * ==============================
 * 作者：苏幕
 * <p>
 * 时间：2015/11/26   14:43
 * <p>
 * 描述：
 * <p/>应用详情第二模块 安全标记
 * ==============================
 */
public class DetailSafeHolder extends BaseViewHolder implements View.OnClickListener {
    public DetailSafeHolder(Context context) {
        super(context);
    }

    @ViewInject(R.id.safe_arrow)
    private ImageView safe_arrow;
    @ViewInject(R.id.safe_title)
    private LinearLayout safe_title;
    @ViewInject(R.id.safe_content)
    private LinearLayout safe_content;
    @ViewInject(R.id.safe_layout)
    private RelativeLayout safe_layout;

    @Override
    protected View initView() {
        View view = View.inflate(context, R.layout.detail_safe, null);
        ViewUtils.inject(this, view);
        safe_layout.setOnClickListener(this);
        //将安全详细信息控件的高设置为0，即默认隐藏
        ViewGroup.LayoutParams params = safe_content.getLayoutParams();
        params.height=0;
        safe_content.setLayoutParams(params);
        return view;
    }

    @Override
    public void setDataToView(Object data) {
        DetailAppInfo detailAppInfo = (DetailAppInfo) data;
        List<DetailAppInfo.Safe> safes = detailAppInfo.getSafe();
        for (int i = 0; i < safes.size(); i++) {
            DetailAppInfo.Safe safe = safes.get(i);
            //根据安全标识数量，动态添加topImage
            ImageView topImage = new ImageView(context);
            topImage.setScaleType(ImageView.ScaleType.FIT_XY);
            bitmapUtils.display(topImage, Contacts.HOME_IMAGE_URL + safe.getSafeUrl());
            LinearLayout.LayoutParams topParams = new LinearLayout.LayoutParams(UIUtils.dip2px(context, 34), UIUtils.dip2px(context, 15));
            topParams.rightMargin = UIUtils.dip2px(context, 8);
            safe_title.addView(topImage, topParams);
            //动态添加安全标识详细信息desLayout
            LinearLayout.LayoutParams desParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            desParams.topMargin = UIUtils.dip2px(context, 3);
            View desLayout = View.inflate(context, R.layout.des_layout, null);
            ImageView desImage = (ImageView) desLayout.findViewById(R.id.des_iv);
            bitmapUtils.display(desImage, Contacts.HOME_IMAGE_URL + safe.getSafeDesUrl());
            TextView desTv = (TextView) desLayout.findViewById(R.id.des_tv);
            desTv.setText(safe.getSafeDes());

            // 根据服务器数据显示不同的颜色
            int color;
            int colorType = safe.getSafeDesColor();
            if (colorType >= 1 && colorType <= 3) {
                color = context.getResources().getColor(R.color.safe_des_color_1);
            } else if (colorType == 4) {
                color = context.getResources().getColor(R.color.safe_des_color_2);
            } else {
                color = context.getResources().getColor(R.color.safe_des_color_3);
            }
            desTv.setTextColor(color);
            safe_content.addView(desLayout, desParams);
        }
    }

    private boolean flag;
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.safe_layout) {
            int startHeight = 0;
            int targetHeight = 0;
            Animation animation=null;
            if (!flag) {//将内容展开
                flag=true;
                startHeight = 0;
                targetHeight =getMeasuredHeight();
                animation=new RotateAnimation(0,180,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            } else {
                flag=false;
                startHeight = getMeasuredHeight();
                targetHeight = 0;
                animation=new RotateAnimation(180,360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            }
            animation.setDuration(500);
            animation.setFillAfter(true);
            safe_arrow.startAnimation(animation);
            // 值动画，将内容进行展开和隐藏
            ValueAnimator animator = ValueAnimator.ofInt(startHeight, targetHeight);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    ViewGroup.LayoutParams params = safe_content.getLayoutParams();
                    params.height= (int) animation.getAnimatedValue();
                    safe_content.setLayoutParams(params);
                }
            });
            animator.setDuration(500);
            animator.start();
        }
    }
    /**
     * 获取控件实际的高度  onMeasure()  制定测量的规则   measure() 实际测量
     */
    private int getMeasuredHeight(){
        int width = safe_content.getMeasuredWidth();
        int widthMeasureSpec= View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec=View.MeasureSpec.makeMeasureSpec(1000,View.MeasureSpec.AT_MOST);
        // 测量规则 宽度是一个精确的值width, 高度最大是1000,以实际为准
        safe_content.measure(widthMeasureSpec,heightMeasureSpec);// 通过该方法重新测量控件
        return safe_content.getMeasuredHeight();
    }
}
