package com.sumu.googleplay.protocol;

import com.google.gson.reflect.TypeToken;
import com.sumu.googleplay.Contacts;
import com.sumu.googleplay.bean.AppInfo;

import java.util.List;

/**
 * ==============================
 * 作者：苏幕
 * <p>
 * 时间：2015/11/24   14:16
 * <p>
 * 描述：
 * <p>应用数据协议
 * ==============================
 */
public class AppProtocol extends BaseProtocol<List<AppInfo>>{
    @Override
    protected List<AppInfo> parseJson(String result) {
        List<AppInfo> AppInfos=gson.fromJson(result,new TypeToken<List<AppInfo>>(){}.getType());
        return AppInfos;
    }

    @Override
    protected String getUrl() {
        return Contacts.APP_URL;
    }
}
