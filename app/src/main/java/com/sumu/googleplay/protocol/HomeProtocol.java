package com.sumu.googleplay.protocol;

import com.google.gson.reflect.TypeToken;
import com.sumu.googleplay.Contacts;
import com.sumu.googleplay.bean.AppInfo;

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
 * <p>首页数据协议
 * ==============================
 */
public class HomeProtocol extends BaseProtocol<List<AppInfo>> {
    private String[] imageUrl;
    @Override
    public List<AppInfo> parseJson(String result) {
        JSONArray jsonArray = null;
        try {
            JSONObject jsonObject = new JSONObject(result);
            jsonArray = jsonObject.getJSONArray("list");
            JSONArray imageArray=jsonObject.getJSONArray("picture");
            imageUrl=new String[imageArray.length()];
            for (int i=0;i<imageArray.length();i++){
                imageUrl[i]= (String) imageArray.get(i);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        List<AppInfo> homeAppInfos=gson.fromJson(jsonArray.toString(), new TypeToken<List<AppInfo>>() {
        }.getType());
        return homeAppInfos;
    }

    @Override
    public String getUrl() {
        return Contacts.HOME_URL;
    }

    /**
     * 获取ViewPager的图片地址
     * @return
     */
    public String[] getImageUrl() {
        return imageUrl;
    }
}
