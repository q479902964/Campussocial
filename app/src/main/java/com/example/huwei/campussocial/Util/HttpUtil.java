package com.example.huwei.campussocial.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.huwei.campussocial.bean.LocalUser;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by HUWEI on 2017/4/25.
 */

public class HttpUtil {
    private static final String BOUNDARY =  UUID.randomUUID().toString(); // 边界标识 随机生成
    private static final String PREFIX = "--";
    private static final String LINE_END = "\r\n";
    private static final String CONTENT_TYPE = "multipart/form-data"; // 内容类型
    public static void sendOkhttpRequest(final String address, final okhttp3.Callback callback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient mOkHttpClient = new OkHttpClient();
                //2,创建一个Request
                Request request = new Request.Builder().url(address).build();
                //3,创建一个call对象
                mOkHttpClient.newCall(request).enqueue(callback);
            }
        }).start();
    }
    public static void getMessage(final String address,final String userphone,final okhttp3.Callback callback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient mOkHttpClient = new OkHttpClient();
                RequestBody formBody = new FormBody.Builder()
                        .add("phone",userphone)
                        .build();
                Request request = new Request.Builder()
                        .url(address)
                        .post(formBody)
                        .build();
                mOkHttpClient.newCall(request).enqueue(callback);

            }
        }).start();
    }
    public static void postingcomment(final String address,final String post_id,final String comment,final String userphone,final int status ,final okhttp3.Callback callback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient mOkHttpClient = new OkHttpClient();
                RequestBody formBody = new FormBody.Builder()
                        .add("content", comment)
                        .add("phone",userphone)
                        .add("status", String.valueOf(status))
                        .add("post_id",post_id)
                        .build();
                Request request = new Request.Builder()
                        .url(address)
                        .post(formBody)
                        .build();
                mOkHttpClient.newCall(request).enqueue(callback);
            }
        }).start();
    }
        public static void uploadLogin(final String address, final String userphone, final String password, final okhttp3.Callback callback){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    OkHttpClient mOkHttpClient = new OkHttpClient();
                    RequestBody formBody = new FormBody.Builder()
                            .add("phone", userphone)
                            .add("password",password)
                            .build();
                    Request request = new Request.Builder()
                            .url(address)
                            .post(formBody)
                            .build();
                    mOkHttpClient.newCall(request).enqueue(callback);
                }
            }).start();

    }
    public static void postPostings(final String address,final String userphone, final String content, final String title,final int campus_id,final int institute_id,final int major_id,final int niming,final int reward, final int type,final okhttp3.Callback callback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("reward", String.valueOf(reward));
                Log.i("title",title);
                OkHttpClient mOkHttpClient = new OkHttpClient();
                RequestBody formBody = new FormBody.Builder()
                        .add("phone",userphone)
                        .add("content", content)
                        .add("title",title)
                        .add("campus_id", String.valueOf(campus_id))
                        .add("institute_id", String.valueOf(institute_id))
                        .add("type", String.valueOf(major_id))
                        .add("status", String.valueOf(niming))
                        .add("reward", String.valueOf(reward))
                        .add("post_type", String.valueOf(type))
                        .build();
                Request request = new Request.Builder()
                        .url(address)
                        .post(formBody)
                        .build();
                mOkHttpClient.newCall(request).enqueue(callback);
            }
        }).start();

    }
    public static void postPostingsofmoney(final String address, final String content, final String title, final String time, final String money,final okhttp3.Callback callback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient mOkHttpClient = new OkHttpClient();
                RequestBody formBody = new FormBody.Builder()
                        .add("content", content)
                        .add("title",title)
                        .add("time",time)
                        .add("money",money)
                        .build();
                Request request = new Request.Builder()
                        .url(address)
                        .post(formBody)
                        .build();
                mOkHttpClient.newCall(request).enqueue(callback);
            }
        }).start();
    }
    public static void uploadUserData1(final String address, final LocalUser localUser, final okhttp3.Callback callback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String filename = localUser.getUserIcon();
                File file = new File(filename);
                String password = localUser.getUserPassword();
                String phone = localUser.getUserPhone();
                String realname = localUser.getRealName();
                String nickname = localUser.getNickName();
                int major_id = localUser.getMajor_id();
                String grade = localUser.getGrade();
                String identity = localUser.getIdentity();
                int campus_id = localUser.getCampus_id();
                int institude_id = localUser.getInstitute_id();
                OkHttpClient mOkHttpClient = new OkHttpClient();
                RequestBody formBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("phone",phone)
                        .addFormDataPart("real_name", realname)
                        .addFormDataPart("nick_name",nickname)
                        .addFormDataPart("password",password)
                        .addFormDataPart("identity",identity)
                        .addFormDataPart("campus_id", String.valueOf(campus_id))
                        .addFormDataPart("institute_id", String.valueOf(institude_id))
                        .addFormDataPart("major_id", String.valueOf(major_id))
                        .addFormDataPart("grade",grade)
                        .addFormDataPart("filename",file.getName(),RequestBody.create(MediaType.parse("application/octet-stream"),file))
                        .build();
                Log.i("body",password +"/"+ filename);
                Request request = new Request.Builder()
                        .url(address)
                        .post(formBody)
                        .build();
                mOkHttpClient.newCall(request).enqueue(callback);
            }
        }).start();

    }
    public static void updateUserData(final String address, final LocalUser localUser, final okhttp3.Callback callback){
//        String content = "real_name="+ realname +"&nick_name="+ nickname +"&password="+password+
////                            "&identity="+identity +"&institute_id="+ institude_id + "&campus_id="+ campus_id
////                            +"&major_id="+ major_id + "&grade="+ grade + "&phone="+phone;
        new Thread(new Runnable() {
            @Override
            public void run() {
                String realname = localUser.getRealName();
                String nickname = localUser.getNickName();
                int major_id = localUser.getMajor_id();
                String grade = localUser.getGrade();
                String identity = localUser.getIdentity();
                int campus_id = localUser.getCampus_id();
                int institude_id = localUser.getInstitute_id();
                OkHttpClient mOkHttpClient = new OkHttpClient();
                RequestBody formBody = new FormBody.Builder()
                        .add("real_name", realname).add("nick_name",nickname)
                        .add("identity",identity)
                        .add("institude", String.valueOf(institude_id))
                        .add("campus_id", String.valueOf(campus_id))
                        .add("major_id", String.valueOf(major_id))
                        .add("grade",grade)
                        .build();
                Request request = new Request.Builder()
                        .url(address)
                        .post(formBody)
                        .build();
                mOkHttpClient.newCall(request).enqueue(callback);
            }
        }).start();
    }
    public static void updateIcon(final String address,final String phone, final String filename, final okhttp3.Callback callback){
//        String content = "real_name="+ realname +"&nick_name="+ nickname +"&password="+password+
////                            "&identity="+identity +"&institute_id="+ institude_id + "&campus_id="+ campus_id
////                            +"&major_id="+ major_id + "&grade="+ grade + "&phone="+phone;
        new Thread(new Runnable() {
            @Override
            public void run() {
                File file = new File(filename);
                OkHttpClient mOkHttpClient = new OkHttpClient();
                RequestBody formBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("phone",phone)
                        .addFormDataPart("filename",file.getName(),RequestBody.create(MediaType.parse("application/octet-stream"),file))
                        .build();
                Request request = new Request.Builder()
                        .url(address)
                        .post(formBody)
                        .build();
                mOkHttpClient.newCall(request).enqueue(callback);
            }
        }).start();
    }
}
