package com.sumu.googleplay.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sumu.googleplay.Contacts;
import com.sumu.googleplay.bean.HomeAppInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * ==============================
 * 作者：苏幕
 * <p>
 * 时间：2015/11/23   21:30
 * <p>
 * 描述：
 * <p>
 * ==============================
 */
public class HomeProtocol extends BaseProtocol<List<HomeAppInfo>> {

    @Override
    public List<HomeAppInfo> parseJson(String result) {
        JSONArray jsonArray = null;
        try {
            JSONObject jsonObject = new JSONObject(result);
            jsonArray = jsonObject.getJSONArray("list");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        return gson.fromJson(jsonArray.toString(), new TypeToken<List<HomeAppInfo>>() {
        }.getType());
    }

    @Override
    public String getUrl() {
        return Contacts.HOME_URL;
    }

}
