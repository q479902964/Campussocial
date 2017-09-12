package com.example.huwei.campussocial.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by HUWEI on 2017/4/24.
 */

public class NetworkState {
    //检查是否连接到网络
  public static boolean networkConnected(Context context){
      if(context!=null){
          ConnectivityManager manager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
          NetworkInfo info = manager.getActiveNetworkInfo();
          if(info!=null)
            return info.isAvailable();
      }
      return false;
  }
   public static boolean wifiConnected(Context context){
       //检查是否连接Wifi
       if(context!=null){
           ConnectivityManager manager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
           NetworkInfo info = manager.getActiveNetworkInfo();
           if(info!=null){
              if(info.getType()==ConnectivityManager.TYPE_WIFI)
                  return info.isAvailable();
           }
       }
       return false;
   }
   public static  boolean mobileDataConnected(Context context){
       //检查移动网络是否连接
       if(context!=null){
           ConnectivityManager manager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
           NetworkInfo info = manager.getActiveNetworkInfo();
           if(info!=null){
               if(info.getType()==ConnectivityManager.TYPE_MOBILE)
                   return true;
           }
       }
       return false;
   }
}
