package com.example.huwei.campussocial.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by HUWEI on 2017/5/2.
 */

public class Chat {
    private String FriendName;
    private String ChatContent;
    private int FriendIcon;
    private String ChatTime;

    public String getChatTime() {
        return ChatTime;
    }

    public void setChatTime(String chatTime) {
        ChatTime = chatTime;
    }

    public String getFriendName() {
        return FriendName;
    }

    public void setFriendName(String friendName) {
        FriendName = friendName;
    }

    public String getChatContent() {
        return ChatContent;
    }

    public void setChatContent(String chatContent) {
        ChatContent = chatContent;
    }

    public int getFriendIcon() {
        return FriendIcon;
    }

    public void setFriendIcon(int friendIcon) {
        FriendIcon = friendIcon;
    }
}
