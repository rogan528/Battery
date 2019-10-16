package com.android.battery.location;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.SystemClock;
import android.text.TextUtils;

public class LocationService extends Service {
    private PowerManager.WakeLock wakeLock;
    private Intent alarmIntent;
    public LocationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LocationManager.getInstance().startLocation(this);
        alarmKeep();
    }

    private void alarmKeep() {
        alarmIntent = new Intent();
        alarmIntent.setAction("LOCATION");
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        //闹钟管理器
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("LOCATION");
        registerReceiver(alarmReceiver,intentFilter);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(),5000,broadcast);
    }
    BroadcastReceiver alarmReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
                  if (TextUtils.equals(intent.getAction(),"LOCATION")){
                      LocationManager.getInstance().startLocation(LocationService.this);
                  }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
