package com.example.administrator.emmapplication.utils.passsword;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;

import com.example.administrator.emmapplication.utils.DevicePolicyUtils;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class BasePwd {
    DevicePolicyManager devicePolicyManager;
    ComponentName componentName;
    boolean isAdminActive;
    public BasePwd(Context context){
        DevicePolicyUtils devicePolicyUtils = new DevicePolicyUtils(context);
        devicePolicyManager=devicePolicyUtils.getDevicePolicyManager();
        componentName=devicePolicyUtils.getComponentName();
        isAdminActive=devicePolicyUtils.isAdminActive();
    }
    public abstract void pwdConfigs(Context context, JSONObject password);
    long getPasswordExpirationTimeout(int type){
        long time=0;
        switch (type){
            case 0://一小时测试用
                time=3600000L;
                break;
            case 1://1周
                time=604800000L;
                break;
            case 2://2周
                time=2L*604800000L;
                break;
            case 3://一个月（一个月按4周算）
                time=4L*604800000L;
                break;
            case 4://二个月
                time=2L*4L*604800000L;
                break;
            case 5://三个月
                time=3L*4L*604800000L;
                break;
        }
        return time;
    }
    int getQuality(int type){
        int code=0;
        switch (type){
            case 0:
                code = 131072;
                break;
            case 1:
                code = 327680;
                break;
            case 2:
                code = 393216;
                break;
        }
        return code;
    }
}
