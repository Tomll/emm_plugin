package com.example.administrator.emmapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * 检查权限的工具类
 * <p/>
 * Created by wangchenlong on 16/1/26.
 */
public class PermissionsChecker {
    private final Context mContext;
    private final Activity mActivity;


    public PermissionsChecker(Context context,Activity activity) {
        mContext = context.getApplicationContext();
        mActivity=activity;
    }

    // 判断权限集合
    public boolean lacksPermissions(String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    // 判断是否缺少权限
    private boolean lacksPermission(String permission) {
        return ContextCompat.checkSelfPermission(mContext, permission) ==
                PackageManager.PERMISSION_DENIED;
    }

    // 请求权限兼容低版本
    private void requestPermissions(String[] permissions) {
        ActivityCompat.requestPermissions(mActivity, permissions, 213);
    }

    public void startPermission(String[] permissions){
        if (lacksPermissions(permissions)) {
            requestPermissions(permissions); // 请求权限
            allPermissionsGranted();
        }else{
            somePermissionNotGranted();
        }
    }

    private void allPermissionsGranted() {
    }

    private void somePermissionNotGranted(){
    }

    // 含有全部的权限
    public boolean hasAllPermissionsGranted(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}
