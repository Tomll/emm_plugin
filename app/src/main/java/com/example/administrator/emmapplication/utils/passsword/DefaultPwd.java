package com.example.administrator.emmapplication.utils.passsword;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;

import com.example.administrator.emmapplication.utils.DevicePolicyUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class DefaultPwd extends PwdDecorator{
    public DefaultPwd(Context context) {
        super(context);
    }

    @Override
    public void pwdConfigs(Context context, JSONObject password) {
        try {
            if (isAdminActive) {
                int pwComplexity = password.getInt("pwComplexity");
                devicePolicyManager.setPasswordQuality(componentName, getQuality(pwComplexity));
                //密码最小长度
                devicePolicyManager.setPasswordMinimumLength(componentName, password.getInt("pwLength"));

                maxFailedAttempts(password, devicePolicyManager, componentName);
                maxInactivity(password, devicePolicyManager, componentName);
                pwdExpirationTime(password, devicePolicyManager, componentName);

                //密码记录次数
                devicePolicyManager.setPasswordHistoryLength(componentName, password.getInt("pinHistory"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void pwdExpirationTime(JSONObject password, DevicePolicyManager devicePolicyManager, ComponentName componentName) throws JSONException {
        //密码有效期
        long saveTime = getPasswordExpirationTimeout(password.getInt("maxPinageInDays"));
        devicePolicyManager.setPasswordExpirationTimeout(componentName, saveTime);
    }

    private void maxInactivity(JSONObject password, DevicePolicyManager devicePolicyManager, ComponentName componentName) throws JSONException {
        int maxInactivity = password.getInt("maxInactivity");
        //自动锁定前最长时间
        long lockTime = 60000L * maxInactivity;//每分钟
        devicePolicyManager.setMaximumTimeToLock(componentName, lockTime); //M版本以及之后效果不明
    }

    private void maxFailedAttempts(JSONObject password, DevicePolicyManager devicePolicyManager, ComponentName componentName) throws JSONException {
        int maxFailedAttempts = password.getInt("maxFailedAttempts");
        if (maxFailedAttempts >= 4) {
            //最大尝试次数
            devicePolicyManager.setMaximumFailedPasswordsForWipe(componentName, maxFailedAttempts);
        }
    }

}
