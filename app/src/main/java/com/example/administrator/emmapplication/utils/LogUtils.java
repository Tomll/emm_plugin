package com.example.administrator.emmapplication.utils;

import android.util.Log;

import com.example.administrator.emmapplication.BuildConfig;

public class LogUtils {

    private static String TAG = LogUtils.class.getName();

    public static void i(String msg){
        if(BuildConfig.DEBUG)
            Log.i(TAG,msg);
    }

    public static void e(String msg){
        if(BuildConfig.DEBUG)
            Log.e(TAG,msg);
    }

    public static void d(String msg){
        if(BuildConfig.DEBUG)
            Log.d(TAG,msg);
    }

    public static void i(String TAG,String msg){
        if(BuildConfig.DEBUG)
            Log.i(TAG,msg);
    }

    public static void e(String TAG,String msg){
        if(BuildConfig.DEBUG)
            Log.e(TAG,msg);
    }

    public static void d(String TAG,String msg){
        if(BuildConfig.DEBUG)
            Log.d(TAG,msg);
    }
}
