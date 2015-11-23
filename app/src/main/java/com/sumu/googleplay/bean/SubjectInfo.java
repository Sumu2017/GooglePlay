package com.sumu.googleplay.bean;

/**
 * ==============================
 * 作者：苏幕
 * <p>
 * 时间：2015/11/23   22:52
 * <p>
 * 描述：
 * <p>专题数据对象
 * ==============================
 */
public class SubjectInfo {
    private String des;
    private String url;

    @Override
    public String toString() {
        return "SubjectInfo{" +
                "des='" + des + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public SubjectInfo() {
    }

    public SubjectInfo(String des, String url) {
        this.des = des;
        this.url = url;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
