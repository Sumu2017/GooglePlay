package com.sumu.googleplay.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sumu.googleplay.Contacts;
import com.sumu.googleplay.bean.SubjectInfo;

import java.util.List;

/**
 * ==============================
 * 作者：苏幕
 * <p>
 * 时间：2015/11/23   22:52
 * <p>
 * 描述：
 * <p>专业数据逻辑
 * ==============================
 */
public class SubjectProtocol extends BaseProtocol<List<SubjectInfo>> {
    @Override
    public List<SubjectInfo> parseJson(String result) {
        Gson gson = new Gson();
        return gson.fromJson(result, new TypeToken<List<SubjectInfo>>() {
        }.getType());
    }

    @Override
    public String getUrl() {
        return Contacts.SUBJECT_URL;
    }
}
