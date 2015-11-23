package com.sumu.googleplay.activity;

import android.graphics.Color;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sumu.googleplay.R;
import com.sumu.googleplay.adapter.ContentAdapter;
import com.sumu.googleplay.fragment.BaseFragment;
import com.sumu.googleplay.fragment.FragmentFactory;
import com.sumu.googleplay.utils.UIUtils;

public class MainActivity extends BaseActivity implements SearchView.OnQueryTextListener {
    @ViewInject(R.id.drawerLayout)
    private DrawerLayout drawerLayout;
    @ViewInject(R.id.leftMenu)
    private FrameLayout leftMenu;//抽屉内容
    private ActionBarDrawerToggle drawerToggle;
    @ViewInject(R.id.my_toolbar)
    private Toolbar myToolbar;
    /* @ViewInject(R.id.sliding_tabs)
     private SlidingTabLayout slidingTabLayout;//Tab标签*/
    @ViewInject(R.id.pager_title_strip)
    private PagerTabStrip pagerTitle;
    @ViewInject(R.id.vp_content)
    private ViewPager vpContent;//主内容
    private ContentAdapter contentAdapter;
    private String[] tabNames;//标签名字

    @Override
    protected void init() {
        tabNames= UIUtils.getStringArray(R.array.tab_names);
    }

    @Override
    protected void initToolBar() {
        setSupportActionBar(myToolbar);
        myToolbar.setLogo(R.mipmap.ic_launcher);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, myToolbar, R.string.open_drawer, R.string.close_drawer);
        //让开关和Toolbar建立关系
        drawerToggle.syncState();
        //设置显示和打开抽屉界面
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        pagerTitle.setTabIndicatorColor(Color.parseColor("#FF0084FF"));//设置标签的下滑线的颜色
        contentAdapter = new ContentAdapter(getSupportFragmentManager(),tabNames);
        vpContent.setAdapter(contentAdapter);
        vpContent.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                BaseFragment fragment = FragmentFactory.createFragment(position);
                fragment.show();//当界面进行切换时，重新请求服务器
            }
        });
       /* slidingTabLayout.setSelectedIndicatorColors(Color.BLUE);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(vpContent);*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);//搜索的监听
        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Toast.makeText(MainActivity.this, newText, Toast.LENGTH_SHORT).show();
        return true;
    }
}
