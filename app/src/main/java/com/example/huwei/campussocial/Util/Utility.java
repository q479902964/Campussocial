package com.example.huwei.campussocial.Util;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.example.huwei.campussocial.bean.Campus;
import com.example.huwei.campussocial.bean.Classification;
import com.example.huwei.campussocial.bean.Chat;
import com.example.huwei.campussocial.bean.Comment;
import com.example.huwei.campussocial.bean.Friend;
import com.example.huwei.campussocial.bean.Institute;
import com.example.huwei.campussocial.bean.Message;
import com.example.huwei.campussocial.bean.Postings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

/**
 * Created by HUWEI on 2017/5/11.
 */

public class Utility {
    public static boolean handleCampusResponse(String response){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray allCampus = new JSONArray(response);
                for(int i = 0;i<allCampus.length();i++){
                JSONObject campusObject = allCampus.getJSONObject(i);
                Campus campus = new Campus();
                campus.setCampusName(campusObject.getString("name"));
                campus.setCampusCode(campusObject.getInt("id"));
                campus.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    return false;
    }
    public static boolean handleInstituteResponse(String response,int campusId){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray allInstitute = new JSONArray(response);
                for(int i = 0;i<allInstitute.length();i++){
                    JSONObject instituteObject = allInstitute.getJSONObject(i);
                    Institute institute = new Institute();
                    institute.setInstituteName(instituteObject.getString("name"));
                    institute.setInstituteCode(instituteObject.getInt("id"));
                    institute.setCampusId(campusId);
                    institute.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public static boolean handleClassificationResponse(String response,int instituteId){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray allClassification = new JSONArray(response);
                for(int i = 0;i<allClassification.length();i++){
                    JSONObject classificationObject = allClassification.getJSONObject(i);
                    Classification classification = new Classification();
                    classification.setClassificationName(classificationObject.getString("name"));
                    classification.setPostingsId(classificationObject.getString("postings_id"));
                    classification.setInstituteId(instituteId);
                    classification.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public static List<Postings> handlePostingResponse(String response){
        try {
            List<Postings> postingsList = new ArrayList<>();
            List<Comment> commentList = new ArrayList<>();
           JSONArray jsonArray = new JSONArray(response);
            for(int i = 0;i<jsonArray.length();i++){
                JSONObject jsonObject =jsonArray.getJSONObject(i);
                String id  = jsonObject.getString("post_id");
                String username  = jsonObject.getString("nick_name");
                String usericon  = jsonObject.getString("photo");
                String title = jsonObject.getString("title");
                String content = jsonObject.getString("content");
                String time = jsonObject.getString("release_time");
                int commentNum = jsonObject.getInt("comment_num");
                int thumbsupnum = jsonObject.getInt("number_point");
                int thumbsdownnum = jsonObject.getInt("number_step");
                JSONArray jsonArray1 = jsonObject.getJSONArray("comment_list");
                for(int j = 0;j<jsonArray1.length();j++){
                    JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
                    String comment_photo=jsonObject1.getString("photo");
                    String comment_nick_name=jsonObject1.getString("nick_name");
                    String comment_release_time = jsonObject1.getString("release_time");
                    String comment_content=jsonObject1.getString("content");
                    Comment comment = new Comment();
                    comment.setUserName(comment_nick_name);
                    comment.setUserIcon(comment_photo);
                    comment.setTime(comment_release_time);
                    comment.setContent(comment_content);
                    commentList.add(comment);
                }
                Postings postings = new Postings();
                postings.setID(id);
                postings.setCommentNum(commentNum);
                postings.setContent(content);
                postings.setThumbsdownNum(thumbsdownnum);
                postings.setThumbsupNum(thumbsupnum);
                postings.setTime(time);
                postings.setTitle(title);
                postings.setUserName(username);
                postings.setUserIcon(usericon);
                postings.setCommentList(commentList);
                postingsList.add(0,postings);
            }
            return postingsList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<Message> handleChatResponse(String response){
        List<Message> chatlist = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for(int i = 0;i<jsonArray.length();i++){
             JSONObject jsonObject =jsonArray.getJSONObject(i);
             String title = jsonObject.getString("title");
             String chatcontent = jsonObject.getString("content");
             String chattime = jsonObject.getString("time");
             String usericon = jsonObject.getString("usericon");
                Message message = new Message();
                message.setTitle(title);
                message.setContent(chatcontent);
                message.setUsericon(usericon);
                message.setTime(chattime);
                chatlist.add(message);
            }
            return chatlist;
        } catch (JSONException e) {
            e.printStackTrace();
        }
             return null;
    }
    public static List<Friend> handleFriendResponse(String response){
        List<Friend> friendList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for(int i = 0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String friendname = jsonObject.getString("friendname");
                String friendicon = jsonObject.getString("friendicon");
                String friendId = jsonObject.getString("friendId");
                Friend friend = new Friend(friendId,friendname,friendicon);
                friendList.add(friend);
            }
            return friendList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
            return null;
    }

}
