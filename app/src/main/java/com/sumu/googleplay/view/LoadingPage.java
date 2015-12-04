package com.sumu.googleplay.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.sumu.googleplay.R;
import com.sumu.googleplay.manager.ThreadManager;

/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/11/23   13:43
 * <p/>
 * 描述：
 * <p/>创建了自定义帧布局 把baseFragment 一部分代码 抽取到这个类中
 * ==============================
 */
public abstract class LoadingPage extends FrameLayout{
    private Context context;
    public LoadingPage(Context context) {
        super(context);
        this.context=context;
        init();//在FrameLayout中 添加4中不同的界面
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init();//在FrameLayout中 添加4中不同的界面
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init();//在FrameLayout中 添加4中不同的界面
    }

    private static final int STATE_UNKOWN = 0;//未知
    private static final int STATE_LOADING = 1;//加载中
    private static final int STATE_ERROR = 2;//错误
    private static final int STATE_EMPTY = 3;//空
    private static final int STATE_SUCCESS = 4;//加载成功
    private int STATE_NOW = STATE_UNKOWN;//当前状态,不能设置为静态的，不然所有的界面会共享状态


    private View loadingView;//加载中的界面
    private View errorView;//加载错误的界面
    private View emptyView;//加载空的界面
    private View successView;//加载成功的界面

    //在FrameLayout中 添加4中不同的界面
    private void init() {
        if (loadingView == null) {//添加加载中的界面
            loadingView = View.inflate(context, R.layout.loadpage_loading, null);
            this.addView(loadingView, new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        if (errorView == null) {//添加加载错误的界面
            errorView = View.inflate(context, R.layout.loadpage_error, null);
            Button btnPage = (Button) errorView.findViewById(R.id.page_bt);
            btnPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show();//加载错误后，点击重新加载
                }
            });
            this.addView(errorView, new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        if (emptyView == null) {//加载空的界面
            emptyView = View.inflate(context, R.layout.loadpage_empty, null);
            this.addView(emptyView, new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        showPage();// 根据不同的状态显示不同的界面
    }

    //根据不同的状态显示不同的界面
    public void showPage() {
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

        if (STATE_NOW==STATE_SUCCESS) {
            if (successView == null) {//加载成功的界面
                successView = createSuccessView();
                this.addView(successView, new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            }
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
    public void show() {
        //加载错误后，点击重新加载后需要改变状态
        if (STATE_NOW == STATE_ERROR || STATE_NOW == STATE_EMPTY) {
            STATE_NOW = STATE_LOADING;
        }
        //请求服务器，获取服务器上的数据，进行判断，返回一个结果
        showPage();//状态改变了，重新判断当前应该显示那个界面
        ThreadManager.getInstance().createLongPool().exexute(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1000);
                final LoadResult loadResult = load();
                if (loadResult != null) {
                    STATE_NOW = loadResult.getValue();
                    handler.sendEmptyMessage(0);
                }
            }
        });
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            showPage();//状态改变了，重新判断当前应该显示那个界面
        }
    };

    /**
     * 当数据成功后所展示的界面
     *
     * @return
     */
    protected abstract View createSuccessView();



    /**
     * 网络请求
     *
     * @return
     */
    protected abstract LoadResult load();

}
