package com.sumu.googleplay.manager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.sumu.googleplay.Contacts;
import com.sumu.googleplay.bean.AppInfo;
import com.sumu.googleplay.bean.DownloadInfo;
import com.sumu.googleplay.http.HttpHelper;
import com.sumu.googleplay.utils.LogUtils;
import com.sumu.googleplay.utils.UIUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/12/4   11:30
 * <p/>
 * 描述：
 * <p/>下载APK管理类
 * ==============================
 */
public class DownloadManager {
    public static final int STATE_NONE = 0;
    /**
     * 等待中
     */
    public static final int STATE_WAITING = 1;
    /**
     * 下载中
     */
    public static final int STATE_DOWNLOADING = 2;
    /**
     * 暂停
     */
    public static final int STATE_PAUSED = 3;
    /**
     * 下载完毕
     */
    public static final int STATE_DOWNLOADED = 4;
    /**
     * 下载失败
     */
    public static final int STATE_ERROR = 5;

    private static DownloadManager instance;

    public DownloadManager() {
    }

    /**
     * 用于记录下载信息，如果是正式项目，需要持久化缓存,ConcurrentHashMap保证线程安全
     **/
    private Map<Long, DownloadInfo> mDownloadMap = new ConcurrentHashMap<>();
    /**
     * 用于记录观察者，当信息发生改变时，需要通知他们
     **/
    private final List<DownloadObserver> mObservers = new ArrayList<>();
    /**
     * 用于记录所有下载的任务，方便在取消下载时，通过id能找到该任务并进行删除
     **/
    private Map<Long, DownloadTask> mTaskMap = new ConcurrentHashMap<>();

    public static synchronized DownloadManager getInstance() {
        if (instance == null) {
            instance = new DownloadManager();
        }
        return instance;
    }

    /**
     * 注册观察者
     **/
    public void registerObserver(DownloadObserver observer) {
        synchronized (mObservers) {
            if (!mObservers.contains(observer)) {
                LogUtils.i("观察者注册成功");
                mObservers.add(observer);
            }
        }
    }

    /**
     * 反注册观察者
     **/
    public void unRegisterObserver(DownloadObserver observer) {
        synchronized (mObservers) {
            if (mObservers.contains(observer)) {
                LogUtils.i("观察者反注册成功");
                mObservers.remove(observer);
            }
        }
    }

    /**
     * 当下载状态发生改变时回调
     **/
    public void notifyDownloadStateChanged(DownloadInfo info) {
        synchronized (mObservers) {
            for (DownloadObserver observer : mObservers) {
                observer.onDownloadStateChanged(info);
            }
        }
    }

    /**
     * 当下载进度发生改变时回调
     *
     * @param info
     */
    public void notifyDownloadProgressed(DownloadInfo info) {
        synchronized (mObservers) {
            for (DownloadObserver observer : mObservers) {
                observer.onDownloadProgressed(info);
            }
        }
    }

    /**
     * 下载，需要传入一个appInfo对象
     * @param appInfo
     */
    public synchronized void download(AppInfo appInfo){
        //先判断是都有这个app的下载信息
        DownloadInfo info=mDownloadMap.get(appInfo.getId());
        if (info==null){//如果没有，则根据APPInfo创建一个新的下载信息
            info=DownloadInfo.clone(appInfo);
            mDownloadMap.put(appInfo.getId(),info);
        }
        // 判断状态是否为STATE_NONE、STATE_PAUSED、STATE_ERROR。只有这3种状态才能进行下载，其他状态不予处理
        if (info.getDownloadState()==STATE_NONE||info.getDownloadState()==STATE_ERROR
                ||info.getDownloadState()==STATE_PAUSED){
            //下载之前，把状态设置为STATE_WAITING，因为此时并没有开始下载，知识把任务放到线程池中，
            // 当任务真正开始执行时，再将状态改为STATE_DOWNLOADING
            info.setDownloadState(STATE_WAITING);
            notifyDownloadStateChanged(info);
            DownloadTask task=new DownloadTask(info);//创建一个下载任务，放入线程池中
            mTaskMap.put(info.getId(),task);
            LogUtils.i("加入下载队列");
            ThreadManager.getInstance().getDownloadPool().exexute(task);
        }
    }

    /**
     * 暂停下载
     * @param appInfo
     */
    public synchronized void pasue(AppInfo appInfo){
        stopDownload(appInfo);
        DownloadInfo info=mDownloadMap.get(appInfo.getId());//找出下载信息
        if (info!=null){//将下载状态改为暂停
            info.setDownloadState(STATE_PAUSED);
            notifyDownloadStateChanged(info);
        }
    }

    /**
     * 取消下载，与暂停类似，只是需要将已下载的文件删除
     * @param appInfo
     */
    public synchronized void cancel(AppInfo appInfo){
        stopDownload(appInfo);
        DownloadInfo info=mDownloadMap.get(appInfo.getId());
        if (info!=null){
            info.setDownloadState(STATE_NONE);
            notifyDownloadStateChanged(info);
            info.setCurrentSize(0);
            File file=new File(info.getPath());
            file.delete();
        }
    }

    /** 安装应用 */
    public synchronized void install(AppInfo appInfo) {
        stopDownload(appInfo);
        DownloadInfo info = mDownloadMap.get(appInfo.getId());// 找出下载信息
        if (info != null) {// 发送安装的意图
            Intent installIntent = new Intent(Intent.ACTION_VIEW);
            installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            installIntent.setDataAndType(Uri.parse("file://" + info.getPath()),
                    "application/vnd.android.package-archive");
            UIUtils.getContext().startActivity(installIntent);
        }
        notifyDownloadStateChanged(info);
    }

    /** 启动应用，启动应用是最后一个 */
    public synchronized void open(AppInfo appInfo) {
        try {
            Context context = UIUtils.getContext();
            // 获取启动Intent
            Intent intent = context.getPackageManager()
                    .getLaunchIntentForPackage(appInfo.getPackageName());
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtils.e(e);
        }
    }

    /**
     * 如果下载任务还处于线程池中，且没有执行，先从线程池中移除
     * @param appInfo
     */
    public void stopDownload(AppInfo appInfo){
        LogUtils.i("暂停下载");
        DownloadTask task=mTaskMap.remove(appInfo.getId());//先从集合中找出下载任务
        if (task!=null){
            ThreadManager.getInstance().getDownloadPool().cancel(task);//然后从线程池中移除
        }
    }

    /**
     * 获取下载信息
     * @param id
     * @return
     */
    public synchronized DownloadInfo getDownloadInfo(long id){
        return mDownloadMap.get(id);
    }

    /**
     * 下载任务
     */
    public class  DownloadTask implements Runnable {
        private DownloadInfo info;

        public DownloadTask(DownloadInfo info) {
            this.info = info;
        }

        @Override
        public void run() {
            LogUtils.i("开始下载");
            info.setDownloadState(STATE_DOWNLOADING);//先改变下载状态
            notifyDownloadStateChanged(info);
            File file = new File(info.getPath());//获得下载文件
            HttpHelper.HttpResult httpResult = null;
            InputStream stream = null;
            if (info.getCurrentSize() == 0 || !file.exists() || file.length() != info.getCurrentSize()) {
                //如果文件不存在，或者进度为0，或者进度与文件长度不相符，则需要重新下载
                info.setCurrentSize(0);
                file.delete();
                httpResult = HttpHelper.download(Contacts.DOWNLOAD_APK_URL + info.getUrl());
                LogUtils.i("重新下载");
            } else {
                //文件存在且长度和进度相等，采用断点下载
                httpResult = HttpHelper.download(Contacts.DOWNLOAD_APK_URL + info.getUrl()
                        + Contacts.DOWNLOAD_APK_URL_RANGE + info.getCurrentSize());
                LogUtils.i("断点下载");
            }
            if (httpResult==null||(stream=httpResult.getInputStream())==null){
                //如果没有下载返回内容，修改为错误状态
                info.setDownloadState(STATE_ERROR);
                notifyDownloadStateChanged(info);
                LogUtils.i("没有下载内容");
            }else {
                FileOutputStream fos=null;
                try {
                    fos=new FileOutputStream(file,true);
                    int count=-1;
                    byte[] buffer=new byte[1024];
                    while (((count=stream.read(buffer))!=-1) && info.getDownloadState()==STATE_DOWNLOADING){
                        //每次读取数据后，都需要判断是否为下载状态，如果不是，下载需要终止，如果是，则刷新进度
                        fos.write(buffer, 0, count);
                        fos.flush();
                        info.setCurrentSize(info.getCurrentSize() + count);
                        notifyDownloadProgressed(info);//刷新进度
                    }
                } catch (Exception e) {
                    LogUtils.e(e);//出异常后需要修改其状态并删除文件
                    LogUtils.i("下载出错");
                    info.setDownloadState(STATE_ERROR);
                    notifyDownloadStateChanged(info);
                    info.setCurrentSize(0);
                    file.delete();
                }finally {
                    if (fos!=null){
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (httpResult!=null){
                        httpResult.close();
                    }
                }
                //判断进度是否和app总长度相等
                if (info.getCurrentSize()==info.getAppSize()){
                    info.setDownloadState(STATE_DOWNLOADED);
                    notifyDownloadStateChanged(info);
                    LogUtils.i("下载完成");
                }else if(info.getDownloadState()==STATE_PAUSED){//判断状态
                    notifyDownloadStateChanged(info);
                }else {
                    info.setDownloadState(STATE_ERROR);
                    notifyDownloadStateChanged(info);
                    info.setCurrentSize(0);
                    file.delete();//错误状态需要将文件删除
                }
            }
            mTaskMap.remove(info.getId());
        }
    }

    public interface DownloadObserver {
        void onDownloadStateChanged(DownloadInfo info);

        void onDownloadProgressed(DownloadInfo info);
    }
}
