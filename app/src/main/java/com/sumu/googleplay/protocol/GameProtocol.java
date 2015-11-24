package com.sumu.googleplay.protocol;

import com.google.gson.reflect.TypeToken;
import com.sumu.googleplay.Contacts;
import com.sumu.googleplay.bean.AppInfo;

import java.util.List;

/**
 * ==============================
 * 作者：苏幕
 * <p>
 * 时间：2015/11/24   14:33
 * <p>
 * 描述：
 * <p>游戏数据协议
 * ==============================
 */
public class GameProtocol extends BaseProtocol<List<AppInfo>>{
    @Override
    protected List<AppInfo> parseJson(String result) {
        List<AppInfo> gameAppInfos = gson.fromJson(result, new TypeToken<List<AppInfo>>() {
        }.getType());
        return gameAppInfos;
    }

    @Override
    protected String getUrl() {
        return Contacts.GAME_URL;
    }
}
