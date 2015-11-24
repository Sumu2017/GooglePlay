package com.sumu.googleplay.protocol;

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
 * <p>专题数据协议
 * ==============================
 */
public class SubjectProtocol extends BaseProtocol<List<SubjectInfo>> {
    @Override
    public List<SubjectInfo> parseJson(String result) {
        List<SubjectInfo> subjectInfos = gson.fromJson(result, new TypeToken<List<SubjectInfo>>() {
        }.getType());
        return subjectInfos;
    }

    @Override
    public String getUrl() {
        return Contacts.SUBJECT_URL;
    }
}
