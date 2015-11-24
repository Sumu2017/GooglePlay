package com.sumu.googleplay.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sumu.googleplay.adapter.holder.BaseViewHolder;
import com.sumu.googleplay.adapter.holder.MoreViewHolder;
import com.sumu.googleplay.manager.ThreadManager;

import java.util.List;

/**
 * ==============================
 * 作者：苏幕
 * <p>
 * 时间：2015/11/24   12:35
 * <p>
 * 描述：
 * <p>所有ListView适配器的基类
 * ==============================
 */
public abstract class DefaultAdapter<T> extends BaseAdapter {
    protected Context context;
    private List<T> datas;
    private static final int ITEM_MORE = 0;//加载更多条目
    private static final int ITEM_NORM = 1;//普通条目

    public DefaultAdapter(Context context, List<T> datas) {
        this.context = context;
        this.datas = datas;
    }

    //根据位置 判断当前条目是什么类型
    @Override
    public int getItemViewType(int position) {
        if (position == (getCount() - 1)) {//如果是最后一个，则返回加载更多条目类型
            return ITEM_MORE;
        } else {
            return ITEM_NORM;
        }
    }

    //当前ListView有几种不同的条目类型
    @Override
    public int getViewTypeCount() {
        return 2;//MoreViewHolder和BaseViewHolder
    }

    @Override
    public int getCount() {
        return datas.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseViewHolder viewHolder = null;
        switch (getItemViewType(position)) {
            case ITEM_MORE://如果是最后一个位置 就显示MoreHolder
                if (convertView == null) {
                    viewHolder = getMoreViewHolder();
                } else {
                    viewHolder = (BaseViewHolder) convertView.getTag();
                }
                break;
            case ITEM_NORM:
                if (convertView == null) {
                    viewHolder = getHolder();//不能通过构造方法传viewHolder，否则只能显示一个，其他都为空
                } else {
                    viewHolder = (BaseViewHolder) convertView.getTag();
                }
                viewHolder.setDataToView(getItem(position));
                break;
        }
        return viewHolder.getConvertView();
    }

    private MoreViewHolder moreViewHolder = null;

    private BaseViewHolder getMoreViewHolder() {
        if (moreViewHolder == null) {
            moreViewHolder = new MoreViewHolder(context, this);
        }
        return moreViewHolder;
    }

    /**
     * 获取ViewHolder
     *
     * @return
     */
    public abstract BaseViewHolder getHolder();

    /**
     * 加载更多数据时刷新界面
     */
    protected void addAndRefresh(List<T> newDatas) {
        if (newDatas != null) {
            datas.addAll(newDatas);
            notifyDataSetChanged();
        }
    }

    /**
     * 当加载更多条目显示的时候 调用该方法
     */
    public void loadMore() {
        ThreadManager.getInstance().createLongPool().exexute(new Runnable() {
            @Override
            public void run() {
                List<T> moreData = getMoreDataFromServer();
                Message.obtain(mhandler, 0, moreData).sendToTarget();
            }
        });
    }

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            List<T> moreData = (List<T>) msg.obj;
            addAndRefresh(moreData);
            moreViewHolder.setDataToView(moreData);
        }
    };

    /**
     * 从网络加载更多数据
     *
     * @return
     */
    protected abstract List<T> getMoreDataFromServer();


}
