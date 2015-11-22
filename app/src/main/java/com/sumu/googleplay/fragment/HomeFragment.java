package com.sumu.googleplay.fragment;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.sumu.googleplay.R;
import com.sumu.googleplay.utils.ViewUtils;

/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/11/22   19:39
 * <p/>
 * 描述：
 * <p/>
 * ==============================
 */
public class HomeFragment extends Fragment {
    private static final int STATE_UNKOWN = 0;//未知
    private static final int STATE_LOADING = 1;//加载中
    private static final int STATE_ERROR = 2;//错误
    private static final int STATE_EMPTY = 3;//空
    private static final int STATE_SUCCESS = 4;//加载成功
    private static int STATE_NOW = STATE_UNKOWN;//当前状态

    private FrameLayout frameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (frameLayout == null) {
            frameLayout = new FrameLayout(getActivity());// 之前的frameLayout 已经记录了一个爹了  爹是之前的ViewPager
            init();//在FrameLayout中 添加4中不同的界面
        }else {
            ViewUtils.removeParent(frameLayout);// 移除frameLayout之前的爹
        }
        showPage();//根据不同的状态显示不同的界面
        show();//根据服务器的数据切换不同的状态
        return frameLayout;
    }

    private View loadingView;//加载中的界面
    private View errorView;//加载错误的界面
    private View emptyView;//加载空的界面
    private TextView successView;//加载成功的界面

    //在FrameLayout中 添加4中不同的界面
    private void init() {
        if (loadingView == null) {//添加加载中的界面
            loadingView = View.inflate(getActivity(), R.layout.loadpage_loading, null);
            frameLayout.addView(loadingView, new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        if (errorView == null) {//添加加载错误的界面
            errorView = View.inflate(getActivity(), R.layout.loadpage_error, null);
            Button btnPage = (Button) errorView.findViewById(R.id.page_bt);
            btnPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //加载错误后，点击重新加载后需要改变状态
                    STATE_NOW = STATE_LOADING;
                    show();//加载错误后，点击重新加载
                }
            });
            frameLayout.addView(errorView, new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        if (emptyView == null) {//加载空的界面
            emptyView = View.inflate(getActivity(), R.layout.loadpage_empty, null);
            frameLayout.addView(emptyView, new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        if (successView == null) {//加载成功的界面
            successView = new TextView(getActivity());
            successView.setText("加载成功...");
            frameLayout.addView(successView, new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

    }


    //根据不同的状态显示不同的界面
    private void showPage() {
        if (loadingView != null) {
            loadingView.setVisibility((STATE_NOW == STATE_UNKOWN || STATE_NOW == STATE_LOADING) ? View.VISIBLE : View.INVISIBLE);
        }
        if (errorView != null) {
            errorView.setVisibility(STATE_NOW == STATE_ERROR ? View.VISIBLE : View.INVISIBLE);
        }
        if (emptyView != null) {
            emptyView.setVisibility(STATE_NOW == STATE_EMPTY ? View.VISIBLE : View.INVISIBLE);
        }
        if (successView != null) {
            successView.setVisibility(STATE_NOW == STATE_SUCCESS ? View.VISIBLE : View.INVISIBLE);
        }
    }

    /**
     * 服务器返回的三种状态，加载失败，数据为空，加载成功
     */
    public enum LoadResult {
        error(STATE_ERROR), empty(STATE_EMPTY), success(STATE_SUCCESS);

        int value;

        LoadResult(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    //根据服务器的数据切换不同的状态
    private void show() {
        //请求服务器，获取服务器上的数据，进行判断，返回一个结果
        showPage();//状态改变了，重新判断当前应该显示那个界面
        new Thread() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                final LoadResult loadResult = load();
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (loadResult != null) {
                                STATE_NOW = loadResult.getValue();
                                showPage();//状态改变了，重新判断当前应该显示那个界面
                            }
                        }
                    });
                }
            }
        }.start();
    }

    private LoadResult load() {
        return LoadResult.error;
    }
}
