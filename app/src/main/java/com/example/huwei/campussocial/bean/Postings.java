package com.example.huwei.campussocial.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by HUWEI on 2017/5/4.
 */

public class Postings {
    private String ID;
    private String UserName;
    private String UserIcon;
    private String Title;
    private String Content;
    private String Time;
    private int CommentNum;
    private int  ThumbsupNum;

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    private List<Comment> commentList;

    public int getThumbsdownNum() {
        return ThumbsdownNum;
    }

    public void setThumbsdownNum(int thumbsdownNum) {
        ThumbsdownNum = thumbsdownNum;
    }

    public int getThumbsupNum() {
        return ThumbsupNum;
    }

    public void setThumbsupNum(int thumbsupNum) {
        ThumbsupNum = thumbsupNum;
    }

    public int getCommentNum() {
        return CommentNum;
    }

    public void setCommentNum(int commentNum) {
        CommentNum = commentNum;
    }

    private int  ThumbsdownNum;
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserIcon() {
        return UserIcon;
    }

    public void setUserIcon(String userIcon) {
        UserIcon = userIcon;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }


}
