package com.sumu.googleplay.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sumu.googleplay.fragment.FragmentFactory;

/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/11/22   19:38
 * <p/>
 * 描述：
 * <p/>主界面Viewpager适配器
 * ==============================
 */
public class ContentAdapter extends FragmentStatePagerAdapter {
    private String[] tabNames;

    public ContentAdapter(FragmentManager fm, String[] tabNames) {
        super(fm);
        this.tabNames = tabNames;
    }

    @Override
    public Fragment getItem(int position) {
        //通过Fragment工厂生产Fragment
        return FragmentFactory.createFragment(position);
    }

    @Override
    public int getCount() {
        return tabNames.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames[position];
    }
}
