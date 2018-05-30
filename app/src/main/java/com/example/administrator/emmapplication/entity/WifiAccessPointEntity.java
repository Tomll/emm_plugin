package com.example.administrator.emmapplication.entity;

import com.example.administrator.emmapplication.entity.abstractEntity.RecycleViewImpl;
import com.example.administrator.emmapplication.utils.WifiAceessPointUtils;

public class WifiAccessPointEntity extends RecycleViewImpl {

    public WifiAccessPointEntity(Builder builder) {
        super(builder);
    }

    @Override
    public Object getData() {
        return "开启wifi热点";
    }

    @Override
    public void clickOn() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                WifiAceessPointUtils.createAp(mContext, WifiAceessPointUtils.getSSID(mContext), "123456", true);
            }
        }).start();
    }

    @Override
    public void clickOff() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                WifiAceessPointUtils.createAp(mContext, WifiAceessPointUtils.getSSID(mContext), "123456", false);
            }
        }).start();
    }
}
