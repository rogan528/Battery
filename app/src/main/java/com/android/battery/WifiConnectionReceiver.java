package com.android.battery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.battery.utils.BatteryUtils;

public class WifiConnectionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (BatteryUtils.isWifi(context)){
            Log.e("zhangbin","正在使用wifi");
        }else {
            Log.e("zhangbin","没有使用wifi");
        }
    }
}
