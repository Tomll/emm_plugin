package com.example.administrator.emmapplication.entity;

import android.net.wifi.WifiManager;

import com.example.administrator.emmapplication.entity.abstractEntity.RecycleViewImpl;
import com.example.administrator.emmapplication.utils.WifiListener;

public class WifiEnableEntity extends RecorderEntity{
    public WifiEnableEntity(Builder builder) {
        super(builder);
    }

    @Override
    public Object getData() {
        return "WIFI启停";
    }

    @Override
    public void clickOn() {
        super.clickOn();
        try {
            WifiListener.newInstance(mContext).disable();
            WifiListener.newInstance(mContext).stratSwitch();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void clickOff() {
        super.clickOff();
        try {
            WifiListener.newInstance(mContext).enable();
            WifiListener.newInstance(mContext).stopSwitch();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
