package com.sumu.googleplay.viewHolder;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sumu.googleplay.R;
import com.sumu.googleplay.adapter.holder.BaseViewHolder;
import com.sumu.googleplay.bean.AppInfo;
import com.sumu.googleplay.bean.DetailAppInfo;
import com.sumu.googleplay.bean.DownloadInfo;
import com.sumu.googleplay.manager.DownloadManager;
import com.sumu.googleplay.utils.UIUtils;

/**
 * ==============================
 * 作者：苏幕
 * <p>
 * 时间：2015/11/27   11:13
 * <p>
 * 描述：
 * <p/>应用详情第五模块 下方三个按钮
 * ==============================
 */
public class DetailBottomHolder extends BaseViewHolder implements View.OnClickListener, DownloadManager.DownloadObserver {

    private AppInfo appInfo;
    private int mState;
    private float mProgress;

    public DetailBottomHolder(Context context) {
        super(context);
    }

    @ViewInject(R.id.bottom_favorites)
    private Button bottom_favorites;
    @ViewInject(R.id.bottom_share)
    private Button bottom_share;
    @ViewInject(R.id.progress_btn)
    private Button progress_btn;
    @ViewInject(R.id.pb_load_process)
    private ProgressBar pb_load_process;
    @ViewInject(R.id.tv_load_process)
    private TextView tv_load_process;
    @ViewInject(R.id.progress_layout)
    private FrameLayout progress_layout;
    private DownloadManager mDownloadManager;

    @Override
    protected View initView() {
        View view = View.inflate(context, R.layout.detail_bottom, null);
        ViewUtils.inject(this, view);
        progress_layout.setOnClickListener(this);
        progress_btn.setOnClickListener(this);
        return view;
    }

    @Override
    public void setDataToView(Object data) {
        DetailAppInfo detailAppInfo = (DetailAppInfo) data;
        appInfo = new AppInfo();
        appInfo.setId(detailAppInfo.getId());
        appInfo.setPackageName(detailAppInfo.getPackageName());
        appInfo.setName(detailAppInfo.getName());
        appInfo.setSize(detailAppInfo.getSize());
        appInfo.setDownloadUrl(detailAppInfo.getDownloadUrl());
        if (mDownloadManager == null) {
            mDownloadManager = DownloadManager.getInstance();
        }
        DownloadInfo downloadInfo = mDownloadManager.getDownloadInfo(appInfo.getId());
        if (downloadInfo != null) {//如果之前有下载就直接读取下载状态
            mState = downloadInfo.getDownloadState();
            mProgress = downloadInfo.getProgress();
        } else {
            mState = DownloadManager.STATE_NONE;
            mProgress = 0;
        }
        refreshState(mState, mProgress);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.progress_layout:
            case R.id.progress_btn:
                if (mState == DownloadManager.STATE_NONE || mState == DownloadManager.STATE_PAUSED
                        || mState == DownloadManager.STATE_ERROR) {
                    mDownloadManager.download(appInfo);
                } else if (mState == DownloadManager.STATE_WAITING || mState == DownloadManager.STATE_DOWNLOADING) {
                    mDownloadManager.pasue(appInfo);
                } else if (mState == DownloadManager.STATE_DOWNLOADED) {
                    mDownloadManager.install(appInfo);
                }
                break;
        }
    }

    /**
     * 根据不同的状态改变进度条样式
     *
     * @param state    下载状态
     * @param progress 下载进度
     */
    public void refreshState(int state, float progress) {
        this.mState = state;
        this.mProgress = progress;
        switch (mState) {
            case DownloadManager.STATE_NONE:
                progress_layout.setVisibility(View.GONE);
                progress_btn.setVisibility(View.VISIBLE);
                progress_btn.setText(context.getString(R.string.app_state_download));
                break;
            case DownloadManager.STATE_DOWNLOADING:
                progress_layout.setVisibility(View.VISIBLE);
                pb_load_process.setProgress((int) (progress * 100));
                tv_load_process.setText((int) (progress * 100) + "%");
                progress_btn.setVisibility(View.GONE);
                break;
            case DownloadManager.STATE_PAUSED:
                progress_layout.setVisibility(View.VISIBLE);
                pb_load_process.setProgress((int) (progress * 100));
                tv_load_process.setText(context.getString(R.string.app_state_paused));
                progress_btn.setVisibility(View.GONE);
                break;
            case DownloadManager.STATE_ERROR:
                progress_layout.setVisibility(View.VISIBLE);
                pb_load_process.setProgress((int) (progress * 100));
                tv_load_process.setText(context.getString(R.string.app_state_error));
                progress_btn.setVisibility(View.GONE);
                break;
            case DownloadManager.STATE_WAITING:
                progress_layout.setVisibility(View.VISIBLE);
                tv_load_process.setText(context.getString(R.string.app_state_waiting));
                progress_btn.setVisibility(View.GONE);
                break;
            case DownloadManager.STATE_DOWNLOADED:
                progress_layout.setVisibility(View.VISIBLE);
                tv_load_process.setText(context.getString(R.string.app_state_downloaded));
                progress_btn.setVisibility(View.GONE);
                break;
        }
    }

    public void startObserver() {
        mDownloadManager.registerObserver(this);
    }

    public void stopObserver() {
        mDownloadManager.unRegisterObserver(this);
    }

    @Override
    public void onDownloadStateChanged(DownloadInfo info) {
        refreshHolder(info);
    }

    @Override
    public void onDownloadProgressed(DownloadInfo info) {
        refreshHolder(info);
    }

    /**
     * 刷新界面
     *
     * @param info
     */
    private void refreshHolder(final DownloadInfo info) {
        if (appInfo.getId() == info.getId()) {
            UIUtils.runInMainThread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("info.getProgress()-------->"+info.getProgress());
                    refreshState(info.getDownloadState(), info.getProgress());
                }
            });
        }
    }
}
