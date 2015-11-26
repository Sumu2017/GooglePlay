package com.sumu.googleplay.protocol;

import com.sumu.googleplay.Contacts;
import com.sumu.googleplay.bean.UserInfo;

/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/11/25   12:28
 * <p/>
 * 描述：
 * <p/>侧滑菜单数据协议
 * ==============================
 */
public class UserProtocol extends BaseProtocol<UserInfo>{
    @Override
    protected UserInfo parseJson(String result) {
        UserInfo userInfo=gson.fromJson(result,UserInfo.class);
        return userInfo;
    }

    @Override
    protected String getUrl() {
        return Contacts.USER_URL;
    }
}
