package com.sumu.googleplay.activity;

import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.sumu.googleplay.R;

public class DetailActivity extends BaseActivity {

    @Override
    protected void init() {
    }

    @Override
    protected void initToolBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_detail);
    }

    /**
     * 处理菜单条目的点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       /* if (item.getItemId()==android.R.id.home){
            finish();
        }*/
        return super.onOptionsItemSelected(item);
    }
}
