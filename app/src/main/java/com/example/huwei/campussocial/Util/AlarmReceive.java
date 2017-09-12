package com.example.huwei.campussocial.Util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.huwei.campussocial.view.UIAty;

/**
 * Created by HUWEI on 2017/5/30.
 */

public class AlarmReceive extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent tempIntent = new Intent(context,UIAty.class);
        Bundle bundle = new Bundle();
        bundle.putString("msg", "msg");
        tempIntent.putExtras(bundle);
        tempIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//设置新的task
        context.startActivity(tempIntent);
    }
}
