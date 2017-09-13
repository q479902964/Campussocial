package com.example.huwei.campussocial.Util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.huwei.campussocial.view.LoginAty;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by HUWEI on 2017/9/11.
 */

public class AutoUpdateService extends Service {
    private final static String url ="http://192.168.165.18:8088/Campus/index.php/Academic_exchange/Index/getAllPost";
    public String phone;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        phone= intent.getStringExtra("userphone");
        Log.i("phone",phone);
          updateMessage();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int interval = 1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + interval;
        Intent i = new Intent(this,AutoUpdateService.class);
        i.putExtra("userphone",phone);
        PendingIntent pi = PendingIntent.getService(this,0,i,0);
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
        return super.onStartCommand(intent, flags, startId);
    }

    private void updateMessage() {
        Log.i("alarm","good");
        HttpUtil.getMessage(url, phone, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.i("fail","great");
                SharedPreferences prefs = getSharedPreferences("new", Context.MODE_MULTI_PROCESS);
                SharedPreferences.Editor editor = prefs.edit();
//                editor.putString("new_message"," [\n" +
//                        "        {\n" +
//                        "            \"title\": \"Google\",\n" +
//                        "            \"time\": \"18:20\",\n" +
//                        "            \"content\": \"你好\",\n" +
//                        "            \"usericon\": \"http://bpic.588ku.com/back_pic/02/67/58/81578e331cc7693.jpg\"\n" +
//                        "        },\n" +
//                        "        {\n" +
//                        "            \"title\": \"lj\",\n" +
//                        "            \"time\": \"18:20\",\n" +
//                        "            \"content\": \"你好\",\n" +
//                        "            \"usericon\": \"http://bpic.588ku.com/back_pic/02/67/58/81578e331cc7693.jpg\"\n" +
//                        "        }\n" +
//                        "    ]\n"
//                        );
                editor.putString("new_message",null);
                editor.apply();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String re= response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this).edit();
                editor.putString("new_message",re);
                editor.apply();
            }
        });
    }
}
