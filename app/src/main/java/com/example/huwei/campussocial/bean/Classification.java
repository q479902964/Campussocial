package com.example.huwei.campussocial.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by HUWEI on 2017/5/11.
 */

public class Classification extends DataSupport{
    private int id;
    private String ClassificationName;
    private String PostingsId;
    private int InstituteId;

//    public Classification(int id, String ClassificationName, String PostingsId) {
//        this.id = id;
//        this.ClassificationName =ClassificationName;
//        this.PostingsId = PostingsId;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassificationName() {
        return ClassificationName;
    }

    public void setClassificationName(String classificationName) {
        ClassificationName = classificationName;
    }

    public String getPostingsId() {
        return PostingsId;
    }

    public void setPostingsId(String postingsId) {
        PostingsId = postingsId;
    }

    public int getInstituteId() {
        return InstituteId;
    }

    public void setInstituteId(int instituteId) {
        InstituteId = instituteId;
    }
}
