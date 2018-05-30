package com.example.administrator.emmapplication.utils;

import android.content.Context;

public class LockPolicyUtils {
    public static LockPolicyUtils getInstance(){
        return Holder.INSTANCE;
    }
    private static class Holder{
        private static final LockPolicyUtils INSTANCE = new LockPolicyUtils();
    }

    public void lock(Context context){
        DevicePolicyUtils devicePolicyUtils = new DevicePolicyUtils(context);
        try {
            devicePolicyUtils.getDevicePolicyManager().lockNow();
        }catch (Exception e){
            e.printStackTrace();
            LogUtils.e(e.getMessage());
        }
    }

    public void lock(Context context,String pwd){
        DevicePolicyUtils devicePolicyUtils = new DevicePolicyUtils(context);
        try {
            devicePolicyUtils.getDevicePolicyManager().lockNow();
            devicePolicyUtils.getDevicePolicyManager().resetPassword(pwd,0);
        }catch (Exception e){
            e.printStackTrace();
            LogUtils.e(e.getMessage());
        }
    }
}
