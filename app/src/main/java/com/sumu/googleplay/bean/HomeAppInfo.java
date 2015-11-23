package com.sumu.googleplay.bean;

/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/11/23   16:41
 * <p/>
 * 描述：
 * <p/>首页软件详情
 * ==============================
 */
public class HomeAppInfo {

    private String des;
    private String downloadUrl;
    private String iconUrl;
    private long id;
    private String name;
    private String packageName;
    private long size;
    private float stars;

    public HomeAppInfo() {
    }

    public HomeAppInfo(String des, String downloadUrl, String iconUrl,
                       long id, String name, String packageName, long size, float stars) {
        this.des = des;
        this.downloadUrl = downloadUrl;
        this.iconUrl = iconUrl;
        this.id = id;
        this.name = name;
        this.packageName = packageName;
        this.size = size;
        this.stars = stars;
    }

    @Override
    public String toString() {
        return "HomeAppInfo{" +
                "des='" + des + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", packageName='" + packageName + '\'' +
                ", size=" + size +
                ", stars=" + stars +
                '}';
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }
}
