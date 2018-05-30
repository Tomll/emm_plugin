package com.example.administrator.emmapplication.utils;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.example.administrator.emmapplication.receiver.MyAdmin;

public class DevicePolicyUtils {
    public DevicePolicyManager getDevicePolicyManager() {
        return devicePolicyManager;
    }

    public ComponentName getComponentName() {
        return componentName;
    }

    public boolean isAdminActive() {
        System.out.println("isAdminActive..................."+isAdminActive);
        return isAdminActive;
    }

    /**
     * 设备安全管理服务，2.2之前需要通过反射技术获取
     */
    private DevicePolicyManager devicePolicyManager;
    /*
     * 对应自定义DeviceAdminReceiver的组件
     */
    private ComponentName componentName;

    /**
     * 判断该组建是否有系统管理员的权限（系统安全-设备管理器 中是否激活）
     */
    private boolean isAdminActive;

    public DevicePolicyUtils(Context ctx){
        devicePolicyManager = (DevicePolicyManager) ctx.getSystemService(Context.DEVICE_POLICY_SERVICE);
        componentName = new ComponentName(ctx,MyAdmin.class);
        isAdminActive = devicePolicyManager.isAdminActive(componentName);
    }

    public static void startDeviceManager(Activity mContext) {
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) mContext.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName componentName = new ComponentName(mContext,MyAdmin.class);
        if(!devicePolicyManager.isAdminActive(componentName)) {
            //添加一个隐式意图，完成设备权限的添加
            //并传递了两个参数EXTRA_DEVICE_ADMIN 、 EXTRA_ADD_EXPLANATION
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "");
            mContext.startActivityForResult(intent, 1210);
        }
    }
}
