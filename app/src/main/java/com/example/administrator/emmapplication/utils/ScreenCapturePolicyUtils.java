package com.example.administrator.emmapplication.utils;

import android.content.ComponentName;
import android.content.Context;

import com.example.administrator.emmapplication.receiver.MyAdmin;

public class ScreenCapturePolicyUtils {
    public static ScreenCapturePolicyUtils getInstance(){
        return Holder.INSTANCE;
    }
    private static class Holder{
        private static final ScreenCapturePolicyUtils INSTANCE = new ScreenCapturePolicyUtils();
    }

    /**
     *启用禁用截屏
     * @param disabled true 禁止使用
     */
    public void ScreenCaptureDisabled(Context context,boolean disabled){
        DevicePolicyUtils devicePolicyUtils = new DevicePolicyUtils(context);
        try {
            if(devicePolicyUtils.isAdminActive()){
                devicePolicyUtils.getDevicePolicyManager().setScreenCaptureDisabled(devicePolicyUtils.getComponentName(),disabled);
            }
        }catch (Exception e){
            e.printStackTrace();
            LogUtils.e(e.getMessage());
        }
    }
}
