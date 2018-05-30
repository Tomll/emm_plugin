package com.example.administrator.emmapplication.utils.passsword;

import android.content.Context;

import org.json.JSONObject;

public class PwdDecorator extends BasePwd{

    public PwdDecorator(Context context) {
        super(context);
    }

    BasePwd pwd;
    public void setPwdConfig(BasePwd pwd){
        this.pwd=pwd;
    }

    @Override
    public void pwdConfigs(Context context, JSONObject password) {
        if(pwd!=null){
            pwd.pwdConfigs(context,password);
        }
    }
}
