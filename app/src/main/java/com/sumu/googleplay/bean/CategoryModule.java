package com.sumu.googleplay.bean;

import java.util.List;

/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/11/28   11:09
 * <p/>
 * 描述：
 * <p/>分类对象模块
 * ==============================
 */
public class CategoryModule {
    private String title;
    private List<CategoryInfo> infos;

    public CategoryModule() {
    }

    public CategoryModule(String title, List<CategoryInfo> infos) {
        this.title = title;
        this.infos = infos;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<CategoryInfo> getInfos() {
        return infos;
    }

    public void setInfos(List<CategoryInfo> infos) {
        this.infos = infos;
    }

    @Override
    public String toString() {
        return "CategoryModule{" +
                "title='" + title + '\'' +
                ", infos=" + infos +
                '}';
    }
}
