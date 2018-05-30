package com.example.administrator.emmapplication.utils;

import android.content.Context;

public class CameraPolicyUtils{

    public static CameraPolicyUtils getInstance(){
        return Holder.INSTANCE;
    }

    private static class Holder{
        private static  final CameraPolicyUtils INSTANCE = new CameraPolicyUtils();
    }


    /**
     * 配置使用摄像头
     * @param disabled true 禁止使用
     */
    public void cameraDisabled(Context  context,boolean disabled){
        DevicePolicyUtils devicePolicyUtils = new DevicePolicyUtils(context);
        if (devicePolicyUtils.isAdminActive()){
            try {
                devicePolicyUtils.getDevicePolicyManager().setCameraDisabled(devicePolicyUtils.getComponentName(), disabled);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
