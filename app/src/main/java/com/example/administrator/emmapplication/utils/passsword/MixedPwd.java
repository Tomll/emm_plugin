package com.example.administrator.emmapplication.utils.passsword;

import android.content.Context;

import org.json.JSONObject;

public class MixedPwd extends PwdDecorator{
    public MixedPwd(Context context) {
        super(context);
    }

    @Override
    public void pwdConfigs(Context context, JSONObject password) {
        try {
            if (isAdminActive) {
                devicePolicyManager.setPasswordMinimumLetters(componentName, password.getInt("minChars"));//最少字母数
                devicePolicyManager.setPasswordMinimumLowerCase(componentName, password.getInt("minLowerChars"));//最少小写字母数
                devicePolicyManager.setPasswordMinimumUpperCase(componentName, password.getInt("minUpperChars"));//最少大写字母数
                devicePolicyManager.setPasswordMinimumNonLetter(componentName, password.getInt("minNonalphabetic"));//最小非字母字符数
                devicePolicyManager.setPasswordMinimumNumeric(componentName, password.getInt("minDigital"));//最小数字位数
                devicePolicyManager.setPasswordMinimumSymbols(componentName, password.getInt("minSymbols"));//最小符号数
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
