package com.sumu.googleplay.protocol;

import com.google.gson.reflect.TypeToken;
import com.sumu.googleplay.Contacts;
import com.sumu.googleplay.bean.CategoryInfo;
import com.sumu.googleplay.bean.CategoryModule;

import java.util.ArrayList;
import java.util.List;


/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/11/28   11:13
 * <p/>
 * 描述：
 * <p/>分类数据协议
 * ==============================
 */
public class CategoryProtocol extends BaseProtocol<List<CategoryInfo>> {
    @Override
    protected List<CategoryInfo> parseJson(String result) {
        List<CategoryModule> categoryModules = gson.fromJson(result, new TypeToken<List<CategoryModule>>() {
        }.getType());
        List<CategoryInfo> categoryInfos=new ArrayList<>();
        CategoryInfo categoryInfo=null;
        CategoryModule categoryModule=null;
        for (int i=0;i<categoryModules.size();i++){//将网络获取的数据，重新进行整理
            categoryModule=categoryModules.get(i);
            categoryInfo=new CategoryInfo(categoryModule.getTitle());
            categoryInfos.add(categoryInfo);//将标题单独放入List中方便后面listView的展示
            categoryInfos.addAll(categoryModule.getInfos());
        }
        return categoryInfos;
    }

    @Override
    protected String getUrl() {
        return Contacts.CATEGORY_URL;
    }
}
