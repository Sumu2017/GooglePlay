package com.sumu.googleplay.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.List;

/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/11/22   20:40
 * <p/>
 * 描述：
 * <p/>基类Activity
 * ==============================
 */
public class BaseActivity extends AppCompatActivity {
    //管理运行的Activity,LinkedList增加删除速度较快
    private List<BaseActivity> baseActivities = new LinkedList<>();

    private KillAllReceiver receiver;
    private class KillAllReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册关闭所有activity的广播
        receiver=new KillAllReceiver();
        IntentFilter intentFilter=new IntentFilter("com.sumu.googleplay.killallactivity");
        registerReceiver(receiver,intentFilter);


        //加锁以防出现这边还没加入那边就移除的情况
        synchronized (baseActivities) {
            baseActivities.add(this);
        }
        init();
        initView();
        initToolBar();
    }

    /**
     * 初始化数据等
     */
    protected void init() {

    }

    /**
     * 初始化ToolBar
     */
    protected void initToolBar() {

    }

    /**
     * 初始化界面
     */
    protected void initView() {

    }

    /**
     * 关闭所有的activity
     */
    protected void KillAllActivitys() {
        //复制一份集合，因为遍历过程中不能修改集合
        List<BaseActivity> mActivities = new LinkedList<>(baseActivities);
        for (BaseActivity baseActivity : mActivities) {
            baseActivity.finish();
        }
        //杀死当前进程
        android.os.Process.killProcess(android.os.Process.myPid());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (baseActivities) {
            baseActivities.remove(this);
        }
        if (receiver!=null){
            unregisterReceiver(receiver);
            receiver=null;
        }
    }
}
