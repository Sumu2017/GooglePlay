package com.sumu.googleplay.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/11/27   12:45
 * <p/>
 * 描述：
 * <p/>按宽高比例显示控件
 * ==============================
 */
public class RatioLayout extends FrameLayout {
    private float ratio = 2.43f;//宽高比例值

    public RatioLayout(Context context) {
        super(context);
    }

    public RatioLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatioLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //xmlns:sumu="http://schemas.android.com/apk/res-auto"
        // 参数1 命名空间 参数2 属性的名字 参数3 默认的值
        ratio = attrs.getAttributeFloatValue("http://schemas.android.com/apk/res-auto", "ratio", 1);
    }

    // 测量当前布局
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);// 模式
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);// 宽度大小
        int width = widthSize - getPaddingLeft() - getPaddingRight();// 去掉左右两边的padding

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int height = heightSize - getPaddingTop() - getPaddingBottom();// 去掉上下两边的padding
        if (widthMode == MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY) {
            height = (int) (width / ratio + 0.5f);
        } else if (widthMode != MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
            width = (int) (height * ratio + 0.5f);
        }
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width + getPaddingLeft() + getPaddingRight(), MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height + getPaddingTop() + getPaddingBottom(), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
