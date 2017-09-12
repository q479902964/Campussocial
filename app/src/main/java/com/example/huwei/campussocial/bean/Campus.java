package com.example.huwei.campussocial.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by HUWEI on 2017/5/9.
 */

public class Campus extends DataSupport{
    private int id;

//    public Campus(int id, String campusName, int campusCode) {
//        this.id = id;
//        this.campusName = campusName;
//        this.campusCode = campusCode;
//    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    private String campusName;
    private int campusCode;

    public String getCampusName() {
        return campusName;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName;
    }

    public int getCampusCode() {
        return campusCode;
    }

    public void setCampusCode(int campusCode) {
        this.campusCode = campusCode;
    }
}
