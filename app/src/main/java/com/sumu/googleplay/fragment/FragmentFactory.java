package com.sumu.googleplay.fragment;

import java.util.HashMap;
import java.util.Map;

/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/11/22   21:20
 * <p/>
 * 描述：
 * <p/> Fragment工厂(工厂模式)
 * ==============================
 */
public class FragmentFactory {
    private static Map<Integer, BaseFragment> fragmentMap = new HashMap<>();

    public static BaseFragment createFragment(int position) {
        BaseFragment fragment = null;
        fragment = fragmentMap.get(position);//在集合中重新取出Fragment
        if (fragment != null) {//如果在结合中有则直接返回
            return fragment;
        }
        if (position == 0) {
            fragment = new HomeFragment();
        } else if (position == 1) {
            fragment = new AppFragment();
        } else if (position == 2) {
            fragment = new GameFragment();
        } else if (position == 3) {
            fragment = new SubjectFragment();
        } else if (position == 4) {
            fragment = new CategoryFragment();
        } else if (position == 5) {
            fragment = new TopFragment();
        }
        if (fragment != null) {
            fragmentMap.put(position, fragment);//将创建好的Fragment存放到集合中缓存起来
        }
        return fragment;
    }
}
