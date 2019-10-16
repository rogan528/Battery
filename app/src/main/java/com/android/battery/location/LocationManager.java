package com.android.battery.location;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.android.battery.utils.JobManager;

public class LocationManager {
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private Context mContext;
    private static LocationManager instance;

    public LocationManager() {
    }
    public static LocationManager getInstance(){
        if (null == instance){
            instance = new LocationManager();
        }
        return instance;
    }

    /**
     * 开始定位
     * @param context
     */
    public void startLocation(Context context){
        if (null != mLocationClient){
            mLocationClient.startLocation();
            return;
        }
        mContext = context.getApplicationContext();
        //初始化定位
        mLocationClient = new AMapLocationClient(mContext);
        //设置定位回调监听
        mLocationClient.setLocationListener(mAMapLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(5*60*1000);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setLocationCacheEnable(false);
        //启动定位
        mLocationClient.startLocation();
    }
    //异步获取定位结果
    AMapLocationListener mAMapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //解析定位结果
                    Log.e("zhangbin","amapLocation:"+amapLocation.getDistrict());
                    String location = amapLocation.toStr();
                    JobManager.getInstance().addJob(location);
                }
            }
        }
    };

    /**
     * 停止定位
     */
    public void stopLocation(){
        if (null != mLocationClient){
            mLocationClient.stopLocation();
        }
    }
    public void destoryLocation(){
        if (null != mLocationClient){
            mLocationClient.unRegisterLocationListener(mAMapLocationListener);
            mLocationClient.onDestroy();
            mLocationClient = null;
        }
    }
}
