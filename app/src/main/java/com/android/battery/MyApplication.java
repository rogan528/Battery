package com.android.battery;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Intent;
import android.os.Process;
import android.text.TextUtils;

import com.android.battery.location.LocationService;
import com.example.city_picker.BuildConfig;

import java.util.List;

public class MyApplication extends Application {
    private Intent locationIntent;
    private static MyApplication application;
    @Override
    public void onCreate() {
        super.onCreate();
        if (!TextUtils.equals(BuildConfig.APPLICATION_ID +":location",getProcessName(Process.myPid()))){
            application = this;
            locationIntent = new Intent(this, LocationService.class);
            startService(locationIntent);
        }
    }
    public static MyApplication getInstance(){
        return application;
    }
    public Intent getLocationIntent(){
        return locationIntent;
    }
    String getProcessName(int pid){
        ActivityManager systemService = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = systemService.getRunningAppProcesses();
        if (runningAppProcesses ==null){
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo processInfo:runningAppProcesses) {
            if (processInfo.pid == pid){
                return processInfo.processName;
            }
        }
        return null;
    }
}
