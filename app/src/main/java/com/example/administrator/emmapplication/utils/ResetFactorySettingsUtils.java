package com.example.administrator.emmapplication.utils;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.example.administrator.emmapplication.receiver.MyAdmin;

/**
 * Created by admine on 2018/5/3.
 */

public class ResetFactorySettingsUtils {

    public static void resetFactorySettings(Context context){
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName componentName = new ComponentName(context, MyAdmin.class);
        boolean isAdminActive = devicePolicyManager.isAdminActive(componentName);
        if(isAdminActive){
            devicePolicyManager.lockNow();
            devicePolicyManager.wipeData(0);
        }else {
            Intent intent = new Intent();
            intent.setAction(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
            context.startActivity(intent);
        }
    }
}
