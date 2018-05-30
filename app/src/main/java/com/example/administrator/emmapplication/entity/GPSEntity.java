package com.example.administrator.emmapplication.entity;

import com.example.administrator.emmapplication.entity.abstractEntity.RecycleViewImpl;
import com.example.administrator.emmapplication.utils.GPSUtils;
import com.example.administrator.emmapplication.utils.LogUtils;

public class GPSEntity extends RecycleViewImpl {

    public GPSEntity(Builder builder){
        super(builder);
    }

    @Override
    public Object getData() {
        return "开启或关闭gps";
    }

    @Override
    public void clickOn() {
        if(!GPSUtils.isGPSEnable(mContext)) {
            GPSUtils.toggleGPS(mContext,true);
            LogUtils.d("开启gps.....................");
        }
    }

    @Override
    public void clickOff() {
        if(GPSUtils.isGPSEnable(mContext)) {
            GPSUtils.toggleGPS(mContext,false);
            LogUtils.d("关闭gps.....................");
        }
    }
}
