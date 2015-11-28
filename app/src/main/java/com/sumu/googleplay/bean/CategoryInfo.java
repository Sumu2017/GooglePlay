package com.sumu.googleplay.bean;

/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/11/28   13:25
 * <p/>
 * 描述：
 * <p/>分类对象每个模块的详细信息
 * ==============================
 */
public class CategoryInfo {
    private String title;
    private String name1;
    private String name2;
    private String name3;
    private String url1;
    private String url2;
    private String url3;

    public CategoryInfo() {
    }

    public CategoryInfo(String title) {
        this.title = title;
    }

    public CategoryInfo(String title, String name1, String name2, String name3, String url1, String url2, String url3) {
        this.title = title;
        this.name1 = name1;
        this.name2 = name2;
        this.name3 = name3;
        this.url1 = url1;
        this.url2 = url2;
        this.url3 = url3;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getName3() {
        return name3;
    }

    public void setName3(String name3) {
        this.name3 = name3;
    }

    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    public String getUrl3() {
        return url3;
    }

    public void setUrl3(String url3) {
        this.url3 = url3;
    }
}
