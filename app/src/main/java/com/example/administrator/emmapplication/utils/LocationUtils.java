package com.example.administrator.emmapplication.utils;

import android.content.Context;
import android.location.LocationListener;
import android.text.TextUtils;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.administrator.emmapplication.R;
import com.example.administrator.emmapplication.utils.mobileInfos.DeviceInfos;

import org.json.JSONException;
import org.json.JSONObject;

public class LocationUtils {


    private Context context;
    private static LocationUtils locationUtils = null;

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private LocationResultListener locationResultListener;

    public interface LocationResultListener {
        void locationSuccess(String locationInfo);

        void locationFailed(String failedInfo);
    }

    private LocationUtils() {
    }

    public static LocationUtils getInstance(Context context) {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final LocationUtils INSTANCE = new LocationUtils();
    }

    public void setLocationResltListener(LocationResultListener locationResltListener) {
        this.locationResultListener = locationResltListener;
    }

    public void startLocation() {
        System.out.println("开始定位......................");
        //根据控件的选择，重新设置定位参数
        resetOption();
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    /**
     * 停止定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    public void stopLocation(){
        System.out.println("停止定位......................");
        // 停止定位
        locationClient.stopLocation();
    }

    // 根据控件的选择，重新设置定位参数
    private void resetOption() {
        // 设置是否需要显示地址信息
        locationOption.setNeedAddress(true);
        /**
         * 设置是否优先返回GPS定位结果，如果30秒内GPS没有返回定位结果则进行网络定位
         * 注意：只有在高精度模式下的单次定位有效，其他方式无效
         */
        locationOption.setGpsFirst(true);
        // 设置是否开启缓存
        locationOption.setLocationCacheEnable(true);
        // 设置是否单次定位
        locationOption.setOnceLocation(true);
        //设置是否等待设备wifi刷新，如果设置为true,会自动变为单次定位，持续定位时不要使用
        locationOption.setOnceLocationLatest(true);
        //设置是否使用传感器
        locationOption.setSensorEnable(true);
        //设置是否开启wifi扫描，如果设置为false时同时会停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        try {
            // 设置发送定位请求的时间间隔,最小值为1000，如果小于1000，按照1000算
            locationOption.setInterval(1000);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        try {
            // 设置网络请求超时时间
            locationOption.setHttpTimeOut(1000);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    public void initLocation(Context context) {
        this.context = context;
        //初始化client
        locationClient = new AMapLocationClient(context);
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
    }

    /**
     * 默认的定位参数
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }


    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location) {
                System.out.println("location != null.................");
                StringBuffer sb = new StringBuffer();
                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                if (location.getErrorCode() == 0) {
                    //region 定位信息展示
                    sb.append("定位成功" + "\n");
                    sb.append("经    度    : " + location.getLongitude() + "\n");
                    System.out.println("定位成功，location......"+"经    度    : " + location.getLongitude());
                    sb.append("纬    度    : " + location.getLatitude() + "\n");
                    System.out.println("定位成功，location......"+"纬    度    : " + location.getLatitude());
                    sb.append("地    址    : " + location.getAddress() + "\n");
                    System.out.println("定位成功，location......"+"地    址    : " + location.getAddress());
                    //endregion
                    JSONObject loc = new JSONObject();
                    try {
                        loc.put("id", DeviceInfos.getSerialNumber());
                        loc.put("longitude",location.getLongitude());
                        loc.put("latitude",location.getLatitude());
                        loc.put("location",location.getAddress());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    locationResultListener.locationSuccess(sb.toString());
                    LogUtils.d("定位成功，location......"+sb.toString());
                    System.out.println("定位成功，location......"+sb.toString());
                } else {
                    //定位失败
                    sb.append("定位失败" + "\n");
                    System.out.println("location != null................."+"定位失败");
                    sb.append("错误码:" + location.getErrorCode() + "\n");
                    System.out.println("location != null................."+"错误码:" + location.getErrorCode());
                    sb.append("错误信息:" + location.getErrorInfo() + "\n");
                    System.out.println("location != null................."+"错误信息:" + location.getErrorInfo());
                    sb.append("错误描述:" + location.getLocationDetail() + "\n");
                    System.out.println("location != null................."+"错误描述:" + location.getLocationDetail());
                    locationResultListener.locationFailed(sb.toString());
                    LogUtils.d("c失败，location......"+sb.toString());
                    System.out.println("location != null................."+"c失败，location......"+sb.toString());
                }
                System.out.println("location != null................."+sb.toString());
            } else {
                System.out.println("get location failed.................");
                LogUtils.d("定位失败，get location failed.................");
                locationResultListener.locationFailed(context.getResources().getString(R.string.location_failed_result_info));
            }
        }
    };

    /**
     * 销毁定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void destroyLocation(){
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }
}
