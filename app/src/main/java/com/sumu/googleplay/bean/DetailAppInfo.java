package com.sumu.googleplay.bean;

import java.util.List;

/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/11/26   10:07
 * <p/>
 * 描述：
 * <p/>应用详情数据对象
 * ==============================
 */
public class DetailAppInfo {

    private String author;
    private String date;
    private String des;
    private String downloadNum;
    private String downloadUrl;
    private String iconUrl;
    private String name;
    private String packageName;
    private String version;
    private int id;
    private int size;
    private int stars;
    private List<String> screen;
    private List<safe> safe;

    public DetailAppInfo(String author, String date, String des, String downloadNum, String downloadUrl, String iconUrl, String name, String packageName, String version, int id, int size, int stars, List<String> screen, List<DetailAppInfo.safe> safe) {
        this.author = author;
        this.date = date;
        this.des = des;
        this.downloadNum = downloadNum;
        this.downloadUrl = downloadUrl;
        this.iconUrl = iconUrl;
        this.name = name;
        this.packageName = packageName;
        this.version = version;
        this.id = id;
        this.size = size;
        this.stars = stars;
        this.screen = screen;
        this.safe = safe;
    }

    public DetailAppInfo() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getDownloadNum() {
        return downloadNum;
    }

    public void setDownloadNum(String downloadNum) {
        this.downloadNum = downloadNum;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public List<String> getScreen() {
        return screen;
    }

    public void setScreen(List<String> screen) {
        this.screen = screen;
    }

    public List<DetailAppInfo.safe> getSafe() {
        return safe;
    }

    public void setSafe(List<DetailAppInfo.safe> safe) {
        this.safe = safe;
    }

    @Override
    public String toString() {
        return "DetailAppInfo{" +
                "author='" + author + '\'' +
                ", date='" + date + '\'' +
                ", des='" + des + '\'' +
                ", downloadNum='" + downloadNum + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", name='" + name + '\'' +
                ", packageName='" + packageName + '\'' +
                ", version='" + version + '\'' +
                ", id=" + id +
                ", size=" + size +
                ", stars=" + stars +
                ", screen=" + screen +
                ", safe=" + safe +
                '}';
    }

    public class safe {
        private String safeDes;
        private String safeDesColor;
        private String safeDesUrl;
        private String safeUrl;

        public safe() {
        }

        public safe(String safeDes, String safeDesColor, String safeDesUrl, String safeUrl) {
            this.safeDes = safeDes;
            this.safeDesColor = safeDesColor;
            this.safeDesUrl = safeDesUrl;
            this.safeUrl = safeUrl;
        }

        public String getSafeDes() {
            return safeDes;
        }

        public void setSafeDes(String safeDes) {
            this.safeDes = safeDes;
        }

        public String getSafeDesColor() {
            return safeDesColor;
        }

        public void setSafeDesColor(String safeDesColor) {
            this.safeDesColor = safeDesColor;
        }

        public String getSafeDesUrl() {
            return safeDesUrl;
        }

        public void setSafeDesUrl(String safeDesUrl) {
            this.safeDesUrl = safeDesUrl;
        }

        public String getSafeUrl() {
            return safeUrl;
        }

        public void setSafeUrl(String safeUrl) {
            this.safeUrl = safeUrl;
        }

        @Override
        public String toString() {
            return "safe{" +
                    "safeDes='" + safeDes + '\'' +
                    ", safeDesColor='" + safeDesColor + '\'' +
                    ", safeDesUrl='" + safeDesUrl + '\'' +
                    ", safeUrl='" + safeUrl + '\'' +
                    '}';
        }
    }
}
