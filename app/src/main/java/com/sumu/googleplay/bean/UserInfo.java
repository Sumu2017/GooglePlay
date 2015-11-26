package com.sumu.googleplay.bean;

/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/11/25   12:29
 * <p/>
 * 描述：
 * <p/>用户对象
 * ==============================
 */
public class UserInfo {
    private String name;
    private String email;
    private String url;

    public UserInfo() {
    }

    public UserInfo(String name, String email, String url) {
        this.name = name;
        this.email = email;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
