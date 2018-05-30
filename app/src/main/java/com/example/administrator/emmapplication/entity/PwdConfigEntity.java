package com.example.administrator.emmapplication.entity;

import android.content.Intent;

import com.example.administrator.emmapplication.entity.abstractEntity.RecycleViewImpl;
import com.example.administrator.emmapplication.utils.passsword.DefaultPwd;
import com.example.administrator.emmapplication.utils.passsword.MixedPwd;

import org.json.JSONObject;

public class PwdConfigEntity extends RecycleViewImpl{

    public PwdConfigEntity(Builder builder) {
        super(builder);
    }

    @Override
    public void clickOn() {
        try {
            String message = "{\"pwLength\": 5,\"pwComplexity\": 0,\"maxFailedAttempts\": 4,\"maxPinageInDays\": 0,\"pinHistory\": 1,\"maxInactivity\": 6,\"minChars\": 1,\"minLowerChars\": 0,\"minUpperChars\": 0,\"minNonalphabetic\": 0,\"minDigital\": 1,\"minSymbols\": 1}";
            JSONObject password = new JSONObject(message);
            DefaultPwd pwd1 = new DefaultPwd(mContext);
            int pwComplexity = password.getInt("pwComplexity");
            MixedPwd pwd2 = new MixedPwd(mContext);
            if(2==pwComplexity){
                pwd1.setPwdConfig(pwd2);
            }
            pwd1.pwdConfigs(mContext,password);
            Intent intentPwd = new Intent("android.app.action.SET_NEW_PASSWORD");
            mContext.startActivity(intentPwd);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void clickOff() {
        try {
            String message = "{\"pwLength\": 0,\"pwComplexity\": -1,\"maxFailedAttempts\": 0,\"maxPinageInDays\": 0,\"pinHistory\": 0,\"maxInactivity\": 0,\"minChars\": 0,\"minLowerChars\": 0,\"minUpperChars\": 0,\"minNonalphabetic\": 0,\"minDigital\": 0,\"minSymbols\": 0}";
            JSONObject password = new JSONObject(message);
            DefaultPwd pwd1 = new DefaultPwd(mContext);
            MixedPwd pwd2 = new MixedPwd(mContext);
            pwd1.setPwdConfig(pwd2);
            pwd1.pwdConfigs(mContext,password);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Object getData() {
        return "密码配置策略";
    }
}
