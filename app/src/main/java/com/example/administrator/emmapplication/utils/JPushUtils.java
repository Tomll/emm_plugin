package com.example.administrator.emmapplication.utils;

import android.os.Handler;

import cn.jpush.android.api.JPushInterface;

import static android.security.KeyStore.getApplicationContext;

public class JPushUtils {
    public static JPushUtils getInstance(){
        return new JPushUtils();
    }

    public void initJPush(){
        JPushInterface.init(getApplicationContext());
    }

    public void setAlias(String android_id){
        System.out.println("ANDROID_ID："+android_id);
        JPushInterface.setAlias(getApplicationContext(),1,android_id);
        JPushInterface.getAlias(getApplicationContext(),1);
    }

    public void resumeJPush(){
        JPushInterface.resumePush(getApplicationContext());
    }

    public void stopJPush(){
        JPushInterface.stopPush(getApplicationContext());
    }

    public String getJPushId(){
        return JPushInterface.getRegistrationID(getApplicationContext());
    }
}
