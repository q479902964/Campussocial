package com.example.huwei.campussocial.bean;

/**
 * Created by HUWEI on 2017/4/30.
 */

public class User {
    private int Uid;
    private String UserName;
    private String SignName;
    private String UserImg;
    private boolean IsLogin;

    public int getUid() {
        return Uid;
    }

    public void setUid(int uid) {
        Uid = uid;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getSignName() {
        return SignName;
    }

    public void setSignName(String signName) {
        SignName = signName;
    }

    public String getUserImg() {
        return UserImg;
    }

    public void setUserImg(String userImg) {
        UserImg = userImg;
    }

    public boolean isLogin() {
        return IsLogin;
    }

    public void setLogin(boolean login) {
        IsLogin = login;
    }
}
