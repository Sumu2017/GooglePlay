package com.sumu.googleplay.protocol;

import com.sumu.googleplay.Contacts;
import com.sumu.googleplay.bean.DetailAppInfo;

/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/11/26   9:59
 * <p/>
 * 描述：
 * <p/>应用详情数据协议
 * ==============================
 */
public class DetailProtocol extends BaseProtocol<DetailAppInfo>{
    @Override
    protected DetailAppInfo parseJson(String result) {
        DetailAppInfo detailAppInfo = gson.fromJson(result, DetailAppInfo.class);
        return detailAppInfo;
    }

    @Override
    protected String getUrl() {
        return Contacts.DETAIL_URL;
    }
}
