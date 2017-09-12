package com.example.huwei.campussocial.bean;

/**
 * Created by HUWEI on 2017/9/11.
 */

public class Message {
    private String Usericon;
    private String Title;
    private String Content;
    private String Time;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getUsericon() {
        return Usericon;
    }

    public void setUsericon(String usericon) {
        Usericon = usericon;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
