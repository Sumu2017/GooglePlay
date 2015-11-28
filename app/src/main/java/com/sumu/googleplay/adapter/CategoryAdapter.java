package com.sumu.googleplay.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sumu.googleplay.adapter.holder.BaseViewHolder;
import com.sumu.googleplay.adapter.holder.CategoryTitleViewHolder;
import com.sumu.googleplay.adapter.holder.CategoryViewHolder;
import com.sumu.googleplay.bean.CategoryInfo;

import java.util.List;


/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/11/28   11:53
 * <p/>
 * 描述：
 * <p/>分类界面适配器
 * ==============================
 */
public class CategoryAdapter extends DefaultAdapter<CategoryInfo> {
    private static final int ITEM_TITLE = 2;// 标题条目
    private int position;

    public CategoryAdapter(Context context, List<CategoryInfo> datas, ListView listView) {
        super(context, datas, listView);
    }

    @Override
    public int getItemViewType(int position) {
        if (!TextUtils.isEmpty(datas.get(position).getTitle())) {//如果有标题则就返回标题的条目
            return ITEM_TITLE;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;//因为有个标题条目，所以需要在原来的基础上加1
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        this.position = position;//记录下当前所显示的条目位置，getHolder()需要根据位置返回不同的ViewHolder
        return super.getView(position, convertView, parent);
    }

    @Override
    public void onMyItemClick(int position) {

    }

    @Override
    public BaseViewHolder getHolder() {
        if (getItemViewType(position)==ITEM_TITLE){
            return new CategoryTitleViewHolder(context);
        }else {
            return new CategoryViewHolder(context);
        }
    }

    @Override
    protected List<CategoryInfo> getMoreDataFromServer() {
        return null;
    }
}
