package com.sumu.googleplay.adapter.holder;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.sumu.googleplay.R;
import com.sumu.googleplay.bean.CategoryInfo;

/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/11/28   13:55
 * <p/>
 * 描述：
 * <p/>分类每个模块的标题
 * ==============================
 */
public class CategoryTitleViewHolder extends BaseViewHolder{

    private CategoryInfo categoryInfo;
    private TextView tvTitle;

    public CategoryTitleViewHolder(Context context) {
        super(context);
    }

    @Override
    protected View initView() {
        tvTitle = new TextView(context);
        tvTitle.setTextColor(Color.BLACK);
        tvTitle.setBackgroundResource(R.drawable.grid_item_bg);
        return tvTitle;
    }

    @Override
    public void setDataToView(Object data) {
        categoryInfo = (CategoryInfo) data;
        tvTitle.setText(categoryInfo.getTitle());
    }
}
