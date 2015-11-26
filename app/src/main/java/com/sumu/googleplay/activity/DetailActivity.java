package com.sumu.googleplay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sumu.googleplay.R;
import com.sumu.googleplay.bean.DetailAppInfo;
import com.sumu.googleplay.protocol.DetailProtocol;
import com.sumu.googleplay.view.LoadingPage;

public class DetailActivity extends BaseActivity {
    @ViewInject(R.id.my_toolbar)
    private Toolbar myToolbar;
    private LoadingPage loadingPage;
    @ViewInject(R.id.fl_content)
    private FrameLayout flContent;
    private String packageName;
    private DetailAppInfo detailAppInfo;

    @Override
    protected void init() {
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_detail);
        ViewUtils.inject(this);
        loadingPage = new LoadingPage(this) {
            @Override
            protected View createSuccessView() {
                return DetailActivity.this.createSuccessView();
            }

            @Override
            protected LoadResult load() {
                return DetailActivity.this.load();
            }
        };
        loadingPage.show();//  必须调用show方法 才会请求服务器 加载新的界面
        flContent.addView(loadingPage);
    }

    /**
     * 网络请求
     *
     * @return
     */
    private LoadingPage.LoadResult load() {
        DetailProtocol detailProtocol = new DetailProtocol();
        detailAppInfo = detailProtocol.load(packageName);
        if (detailAppInfo == null) {
            return LoadingPage.LoadResult.error;
        } else {
            return LoadingPage.LoadResult.success;
        }
    }

    @ViewInject(R.id.bottom_layout)
    private FrameLayout bottom_layout;
    @ViewInject(R.id.detail_info)
    private FrameLayout detail_info;
    @ViewInject(R.id.detail_safe)
    private FrameLayout detail_safe;
    @ViewInject(R.id.detail_des)
    private FrameLayout detail_des;
    @ViewInject(R.id.detail_screen)
    private HorizontalScrollView detail_screen;

    /**
     * 当数据成功后所展示的界面
     *
     * @return
     */
    private View createSuccessView() {
        View content = View.inflate(this, R.layout.detail_content, null);
        ViewUtils.inject(this,content);
        return content;
    }

    @Override
    protected void initToolBar() {
        setSupportActionBar(myToolbar);
        myToolbar.setLogo(R.mipmap.ic_launcher);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        //因为BaseActivity的oncreate中先执行的initView()中需要用到packageName，所有要提到super前执行
        packageName = intent.getStringExtra("packageName");
        super.onCreate(savedInstanceState);
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
