package com.example.administrator.emmapplication.entity;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.example.administrator.emmapplication.entity.abstractEntity.RecycleViewImpl;
import com.example.administrator.emmapplication.utils.LocationUtils;
import com.example.administrator.emmapplication.utils.LogUtils;

/**
 * Created by Administrator on 2018/4/9.
 */

public class LocationEntity extends RecycleViewImpl{
    public double latitude;
    public double longitude;
    private LocationUtils locationUtils ;
    private static String dataResult;

    public LocationEntity(Builder builder){
        super(builder);
    }

//    public void init(){
//        locationUtils = LocationUtils.getInstance(mContext);
//        locationUtils.initLocation(mContext);
//        locationUtils.setLocationResltListener(new LocationUtils.LocationResultListener() {
//            @Override
//            public void locationSuccess(String locationInfo) {
//                LocationEntity.dataResult = locationInfo;
//                sendBroadCast();
//            }
//
//            @Override
//            public void locationFailed(String failedInfo) {
//                LocationEntity.dataResult = failedInfo;
//                sendBroadCast();
//            }
//        });
//    }


    @Override
    public void init() {
        super.init();
        LogUtils.d("run method init...................");
        locationUtils = LocationUtils.getInstance(mContext);
        locationUtils.initLocation(mContext);
        locationUtils.setLocationResltListener(new LocationUtils.LocationResultListener() {
            @Override
            public void locationSuccess(String locationInfo) {
                LocationEntity.dataResult = locationInfo;
                sendBroadCast();
                LogUtils.d("location is : "+ locationInfo);
            }

            @Override
            public void locationFailed(String failedInfo) {
                LocationEntity.dataResult = failedInfo;
                sendBroadCast();
            }
        });
    }

    private void sendBroadCast(){
        Intent intent = new Intent();
        intent.setAction("com.location.test");
        sendBroadCast();
    }


    @Override
    public Object getData() {
        return "开启定位\\n"+dataResult;
    }

    @Override
    public void clickOn() {
       locationUtils.startLocation();
    }

    @Override
    public void clickOff() {
        locationUtils.stopLocation();
    }

}
