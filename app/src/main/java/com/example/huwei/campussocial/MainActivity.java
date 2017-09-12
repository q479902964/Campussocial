package com.example.huwei.campussocial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.huwei.campussocial.Util.AutoUpdateService;
import com.example.huwei.campussocial.Util.NetworkState;
import com.example.huwei.campussocial.bean.Friend;
import com.example.huwei.campussocial.view.LoginAty;
import com.example.huwei.campussocial.view.UIAty;

import java.util.ArrayList;
import java.util.List;

import cn.smssdk.SMSSDK;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;


public class MainActivity extends AppCompatActivity implements RongIM.UserInfoProvider{
    private static final String TAG = "MainActivity";
    private static final String token1 ="Fq5D4YZerdVAk6mihy9sGSMEcxJztQGOq1G82o69++BWpTXY0QR2XwKkXnW/k5/eHc+p2gutLP1pZtW1eAPlBmdq7QEGfhzT";
    private List<Friend> userIdList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Boolean connect = NetworkState.networkConnected(this);
        if(connect){
            Toast.makeText(MainActivity.this,"已连接网络",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(MainActivity.this,"没有连接网络",Toast.LENGTH_SHORT).show();
        }
        initUserInfo();
//        connectRongServer(token1);
        SMSSDK.initSDK(this, "1d6014acc5db2", "339c3af52fe63e2c887a8d53a3702a0b");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String userphone = prefs.getString("userphone","");
        boolean login = prefs.getBoolean("login",false);
        if(login){
          Intent intent = new Intent(MainActivity.this, UIAty.class);
            intent.putExtra("userphone",userphone);
           startActivity(intent);
            finish();
        }else {
        Intent intent = new Intent(MainActivity.this, LoginAty.class);
        startActivity(intent);
            finish();
        }
    }
    @Override
    public UserInfo getUserInfo(String s) {
        for (Friend i : userIdList) {
            if (i.getUserId().equals(s)) {
                Log.e(TAG, i.getPortraitUri());
                return new UserInfo(i.getUserId(), i.getName(), Uri.parse(i.getPortraitUri()));
            }
        }
        Log.e("MainActivity", "UserId is : " + s);
        return null;
    }
    private void initUserInfo() {
        userIdList = new ArrayList<Friend>();
        userIdList.add(new Friend("10010", "联通", "http://www.51zxw.net/bbs/UploadFile/2013-4/201341122335711220.jpg"));//联通图标
        userIdList.add(new Friend("10086", "移动", "http://img02.tooopen.com/Download/2010/5/22/20100522103223994012.jpg"));//移动图标
        userIdList.add(new Friend("KEFU144542424649464","在线客服","http://img02.tooopen.com/Download/2010/5/22/20100522103223994012.jpg"));
        RongIM.setUserInfoProvider(this, true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(this,AutoUpdateService.class);
        stopService(intent);
    }
}
