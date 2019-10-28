package com.android.battery.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.telecom.ConnectionService;

public class BatteryUtils {

    public static void addWhileName(Activity activity){

        PowerManager powerManager = (PowerManager) activity.getSystemService(Context.POWER_SERVICE);
        //应用是否在白名单中
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (!powerManager.isIgnoringBatteryOptimizations(activity.getPackageName())){
                //方法1，启动intent
                Intent intent = new Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
                activity.startActivity(intent);
            }
        }
    }

    /**
     * 是否在充电
     * @param context
     * @return
     */
    public static boolean isPlugged(Context context){
        //发送一个广播
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent intent = context.registerReceiver(null, intentFilter);
        //获取充电状态
        int pluggedValue = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean acPlugged = pluggedValue == BatteryManager.BATTERY_PLUGGED_AC;
        boolean usbPlugged = pluggedValue == BatteryManager.BATTERY_PLUGGED_USB;
        boolean wirelessPlugged = pluggedValue == BatteryManager.BATTERY_PLUGGED_WIRELESS;
        return acPlugged || usbPlugged || wirelessPlugged;
    }

    /**
     * 是否wifi
     * @param context
     * @return
     */
    public static boolean isWifi(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (null != activeNetworkInfo && activeNetworkInfo.isConnected()
                && activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI){
            return true;

        }
        return false;
    }
}
