package com.sumu.googleplay.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ListView;

import com.sumu.googleplay.R;

/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/11/23   20:38
 * <p/>
 * 描述：
 * <p/>
 * ==============================
 */
public class BaseListView extends ListView {
    public BaseListView(Context context) {
        super(context);
        init();
    }

    public BaseListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public BaseListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {
    //		setSelector  点击显示的颜色
    //		setCacheColorHint  拖拽的颜色
    //		setDivider  每个条目的间隔	的分割线
        this.setSelector(R.drawable.nothing);
        this.setCacheColorHint(Color.TRANSPARENT);
        this.setDivider(getResources().getDrawable(R.drawable.nothing));
    }
}
