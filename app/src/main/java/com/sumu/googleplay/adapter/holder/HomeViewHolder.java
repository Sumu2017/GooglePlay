package com.sumu.googleplay.adapter.holder;

import android.content.Context;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sumu.googleplay.Contacts;
import com.sumu.googleplay.R;
import com.sumu.googleplay.bean.AppInfo;
import com.sumu.googleplay.bean.DownloadInfo;
import com.sumu.googleplay.manager.DownloadManager;
import com.sumu.googleplay.utils.UIUtils;
import com.sumu.googleplay.view.ProgressArc;

/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/11/24   13:05
 * <p/>
 * 描述：
 * <p>AppAdapter的ViewHolder
 * ==============================
 */
public class HomeViewHolder extends BaseViewHolder implements View.OnClickListener {
    @ViewInject(R.id.item_icon)
    private ImageView item_icon;
    @ViewInject(R.id.item_title)
    private TextView item_title;
    @ViewInject(R.id.item_size)
    private TextView item_size;
    @ViewInject(R.id.item_bottom)
    private TextView item_bottom;
    @ViewInject(R.id.item_rating)
    private RatingBar item_rating;
    @ViewInject(R.id.item_action)
    private RelativeLayout item_action;
    @ViewInject(R.id.action_progress)
    private FrameLayout action_progress;
    @ViewInject(R.id.action_txt)
    private TextView action_txt;
    private ProgressArc mProgressArc;
    private DownloadManager mDownloadManager;
    private int mState;
    private float mProgress;
    private AppInfo homeAppInfo;

    public HomeViewHolder(Context context) {
        super(context);
    }

    public AppInfo getHomeAppInfo() {
        return homeAppInfo;
    }

    @Override
    protected View initView() {
        View convertView = View.inflate(context, R.layout.item_app, null);
        ViewUtils.inject(this, convertView);
        mProgressArc = new ProgressArc(context);
        int arcDiameter = UIUtils.dip2px(context, 26);
        //设置圆的直径
        mProgressArc.setArcDiameter(arcDiameter);
        //设置进度条的颜色
        mProgressArc.setProgressColor(context.getResources().getColor(R.color.progress));
        int size = UIUtils.dip2px(context, 27);
        //进度条的宽高
        action_progress.addView(mProgressArc, new ViewGroup.LayoutParams(size, size));
        return convertView;
    }

    @Override
    public void setDataToView(Object data) {
        homeAppInfo = (AppInfo) data;
        this.item_title.setText(homeAppInfo.getName());
        this.item_size.setText(Formatter.formatFileSize(context, homeAppInfo.getSize()));
        this.item_rating.setRating(homeAppInfo.getStars());
        this.item_bottom.setText(homeAppInfo.getDes());
        this.item_action.setOnClickListener(this);
        bitmapUtils.display(this.item_icon, Contacts.HOME_IMAGE_URL + homeAppInfo.getIconUrl());
        if (mDownloadManager == null) {
            mDownloadManager = DownloadManager.getInstance();
        }
        DownloadInfo downloadInfo = mDownloadManager.getDownloadInfo(homeAppInfo.getId());
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
            case R.id.item_action:
                if (mState == DownloadManager.STATE_NONE || mState == DownloadManager.STATE_PAUSED
                        || mState == DownloadManager.STATE_ERROR) {
                    mDownloadManager.download(homeAppInfo);
                } else if (mState == DownloadManager.STATE_WAITING || mState == DownloadManager.STATE_DOWNLOADING) {
                    mDownloadManager.pasue(homeAppInfo);
                } else if (mState == DownloadManager.STATE_DOWNLOADED) {
                    mDownloadManager.install(homeAppInfo);
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
                mProgressArc.setForegroundResource(R.drawable.ic_download);
                //是否画进度条，不花进度条
                mProgressArc.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                action_txt.setText(context.getString(R.string.app_state_download));
                break;
            case DownloadManager.STATE_DOWNLOADING:
                mProgressArc.setForegroundResource(R.drawable.ic_pause);
                //是否画进度条，不花进度条
                mProgressArc.setStyle(ProgressArc.PROGRESS_STYLE_DOWNLOADING);
                mProgressArc.setProgress(progress, true);
                action_txt.setText((int)(progress*100)+"%");
                break;
            case DownloadManager.STATE_PAUSED:
                mProgressArc.setForegroundResource(R.drawable.ic_resume);
                //是否画进度条，不花进度条
                mProgressArc.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                action_txt.setText(context.getString(R.string.app_state_paused));
                break;
            case DownloadManager.STATE_ERROR:
                mProgressArc.setForegroundResource(R.drawable.ic_redownload);
                //是否画进度条，不花进度条
                mProgressArc.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                action_txt.setText(context.getString(R.string.app_state_error));
                break;
            case DownloadManager.STATE_WAITING:
                mProgressArc.setForegroundResource(R.drawable.ic_pause);
                //是否画进度条，不花进度条
                mProgressArc.setStyle(ProgressArc.PROGRESS_STYLE_WAITING);
                mProgressArc.setProgress(progress,false);
                action_txt.setText(context.getString(R.string.app_state_waiting));
                break;
            case DownloadManager.STATE_DOWNLOADED:
                mProgressArc.setForegroundResource(R.drawable.ic_install);
                //是否画进度条，不花进度条
                mProgressArc.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                action_txt.setText(context.getString(R.string.app_state_downloaded));
                break;
        }
    }
}
