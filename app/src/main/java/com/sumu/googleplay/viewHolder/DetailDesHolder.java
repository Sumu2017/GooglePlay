package com.sumu.googleplay.viewHolder;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sumu.googleplay.R;
import com.sumu.googleplay.adapter.holder.BaseViewHolder;
import com.sumu.googleplay.bean.DetailAppInfo;

/**
 * ==============================
 * 作者：苏幕
 * <p>
 * 时间：2015/11/26   19:18
 * <p>
 * 描述：
 * <p>应用详情第四模块 应用简介
 * ==============================
 */
public class DetailDesHolder extends BaseViewHolder implements View.OnClickListener {
    public DetailDesHolder(Context context) {
        super(context);
    }

    @ViewInject(R.id.des_content)
    private TextView des_content;
    @ViewInject(R.id.des_author)
    private TextView des_author;
    @ViewInject(R.id.des_arrow)
    private ImageView des_arrow;

    @Override
    protected View initView() {
        View view = View.inflate(context, R.layout.detail_des, null);
        ViewUtils.inject(this, view);
        ViewGroup.LayoutParams params = des_content.getLayoutParams();
        if (getFiveLineHeight() > getMeasuredHeight()) {//如果内容超出5行则将后面的隐藏
            params.height = getFiveLineHeight();
        }
        des_content.setLayoutParams(params);
        des_arrow.setOnClickListener(this);
        return view;
    }

    @Override
    public void setDataToView(Object data) {
        DetailAppInfo detailAppInfo = (DetailAppInfo) data;
        des_content.setText(detailAppInfo.getDes());
        des_author.setText(context.getString(R.string.des_author) + detailAppInfo.getAuthor());
    }

    /**
     * 获取7行的高度
     *
     * @return
     */
    private int getFiveLineHeight() {
        // 复制一个新的TextView 用来测量,最好不要在之前的TextView测量 会影响到后面的测量影响其它代码执行
        TextView textView = new TextView(context);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);//设置字体大小14sp
        textView.setMinLines(5);//设置最小有5行
        int width = textView.getMeasuredWidth();
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(1000, View.MeasureSpec.AT_MOST);
        // 测量规则 宽度是一个精确的值width, 高度最大是1000,以实际为准
        textView.measure(widthMeasureSpec, heightMeasureSpec);// 通过该方法重新测量控件
        return textView.getMeasuredHeight();
    }

    /**
     * 获取控件实际的高度  onMeasure()  制定测量的规则   measure() 实际测量
     */
    private int getMeasuredHeight() {
        int width = des_content.getMeasuredWidth();
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(10000, View.MeasureSpec.AT_MOST);
        // 测量规则 宽度是一个精确的值width, 高度最大是10000,以实际为准
        des_content.measure(widthMeasureSpec, heightMeasureSpec);// 通过该方法重新测量控件
        return des_content.getMeasuredHeight();
    }

    /**
     * 获取到界面的ScrollView
     */
    private ScrollView getScrollView(View view) {
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            if (parent instanceof ScrollView) {
                return (ScrollView) parent;
            } else {
                return getScrollView((View) parent);//递归调用，直到拿到ScrollView
            }
        }
        return null;
    }

    private boolean flag;
    private ScrollView scrollView;
    @Override
    public void onClick(View v) {
        //如果内容不超过5行，则点击无效
        if ((v.getId() == R.id.des_arrow) && (getFiveLineHeight() < getMeasuredHeight())) {
            int startHeight = 0;
            int targetHeight = 0;
            Animation animation = null;
            scrollView = getScrollView(des_content);
            if (!flag) {
                flag = true;
                startHeight = getFiveLineHeight();
                targetHeight = getMeasuredHeight();
                animation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            } else {
                flag = false;
                startHeight = getMeasuredHeight();
                targetHeight = getFiveLineHeight();
                animation = new RotateAnimation(180, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            }
            animation.setDuration(500);
            animation.setFillAfter(true);
            des_arrow.startAnimation(animation);
            ValueAnimator animator = ValueAnimator.ofInt(startHeight, targetHeight);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    ViewGroup.LayoutParams params = des_content.getLayoutParams();
                    params.height = (int) animation.getAnimatedValue();
                    des_content.setLayoutParams(params);
                    if (scrollView != null) {
                        scrollView.scrollTo(0, scrollView.getMeasuredHeight());// 让scrollView 移动到最下面
                    }
                }
            });
            animator.setDuration(500);
            animator.start();
        }
    }
}
