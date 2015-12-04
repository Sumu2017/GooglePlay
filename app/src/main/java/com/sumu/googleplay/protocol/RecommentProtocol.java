package com.sumu.googleplay.protocol;

import com.sumu.googleplay.Contacts;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/12/1   11:14
 * <p/>
 * 描述：
 * <p/>推荐数据协议
 * ==============================
 */
public class RecommentProtocol extends BaseProtocol<List<String>>{
    @Override
    protected List<String> parseJson(String result) {
        List<String> datas=new ArrayList<>();
        try {
            JSONArray jsonArray=new JSONArray(result);
            for (int i=0;i<jsonArray.length();i++){
                datas.add((String) jsonArray.get(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return datas;
    }

    @Override
    protected String getUrl() {
        return Contacts.RECOMMEND_URL;
    }
}
