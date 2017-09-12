package com.example.huwei.campussocial.bean;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by HUWEI on 2017/4/22.zz
 */

public class LocalUser implements Serializable {
    private String UserPhone;
    private String UserPassword;
    private String RealName;
    private String NickName;
    private String Identity;
    private int Institute_id;
    private int Campus_id;
    private int Major_id;
    private String Grade;
    private String UserIcon;

    public String getUserIcon() {
        return UserIcon;
    }

    public void setUserIcon(String userIcon) {
        UserIcon = userIcon;
    }

    public String getRealName() {
        return RealName;
    }

    public void setRealName(String realName) {
        RealName = realName;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getIdentity() {
        return Identity;
    }

    public void setIdentity(String identity) {
        Identity = identity;
    }

    public int getInstitute_id() {
        return Institute_id;
    }

    public void setInstitute_id(int institute_id) {
        Institute_id = institute_id;
    }

    public int getCampus_id() {
        return Campus_id;
    }

    public void setCampus_id(int campus_id) {
        Campus_id = campus_id;
    }

    public int getMajor_id() {
        return Major_id;
    }

    public void setMajor_id(int major_id) {
        Major_id = major_id;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }
}
