package com.sumu.googleplay.adapter.holder;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import com.sumu.googleplay.R;
import com.sumu.googleplay.adapter.DefaultAdapter;

import java.util.List;

/**
 * ==============================
 * 作者：苏幕
 * <p>
 * 时间：2015/11/24   15:24
 * <p>
 * 描述：
 * <p>加载更多的ViewHolder
 * ==============================
 */
public class MoreViewHolder extends BaseViewHolder {
    private RelativeLayout rl_more_loading, rl_more_error;
    private DefaultAdapter defaultAdapter;

    public MoreViewHolder(Context context, DefaultAdapter defaultAdapter) {
        super(context);
        this.defaultAdapter = defaultAdapter;
    }

    @Override
    protected View initView() {
        View convertView = View.inflate(context, R.layout.load_more, null);
        rl_more_error = (RelativeLayout) convertView.findViewById(R.id.rl_more_error);
        rl_more_loading = (RelativeLayout) convertView.findViewById(R.id.rl_more_loading);
        return convertView;
    }

    @Override
    public View getConvertView() {
        loadMore();//当viewHolder.getConvertView()被调用时，表示加载更多已经显示，这时候需要去请求服务器
        return super.getConvertView();
    }

    /**
     * 请求服务器加载下批数据
     */
    private void loadMore() {
        //让adapter自己加载更多数据并更新界面
        defaultAdapter.loadMore();
    }

    @Override
    public void setDataToView(Object data) {
        List datas = (List) data;
        if (datas == null) {//加载失败
            rl_more_loading.setVisibility(View.GONE);
            rl_more_error.setVisibility(View.VISIBLE);
        }else if (datas.size()==0){// 没有额外数据了
            rl_more_loading.setVisibility(View.GONE);
            rl_more_error.setVisibility(View.GONE);
        }else {//加载数据成功
            rl_more_loading.setVisibility(View.VISIBLE);
            rl_more_error.setVisibility(View.GONE);
        }
    }
}
