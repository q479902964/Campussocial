package com.example.huwei.campussocial.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by HUWEI on 2017/5/11.
 */

public class Institute extends DataSupport{
    private int id;

//    public Institute(int id, String instituteName, int instituteCode) {
//        this.id = id;
//        this.instituteName = instituteName;
//        this.instituteCode = instituteCode;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String instituteName;
    private int instituteCode;
    private int CampusId;

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public int getInstituteCode() {
        return instituteCode;
    }

    public void setInstituteCode(int instituteCode) {
        this.instituteCode = instituteCode;
    }

    public int getCampusId() {
        return CampusId;
    }

    public void setCampusId(int campusId) {
        CampusId = campusId;
    }
}
