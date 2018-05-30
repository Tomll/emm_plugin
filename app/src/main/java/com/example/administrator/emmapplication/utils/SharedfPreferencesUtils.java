package com.example.administrator.emmapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedfPreferencesUtils {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public static SharedfPreferencesUtils getInstance(Context context){
        return new SharedfPreferencesUtils(context);
    }

    private SharedfPreferencesUtils(Context context){
        preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }


    //用户登录账号
    public void setAccount(String tenant,String userName){
        editor.putString("tenant",tenant);
        editor.putString("account",userName);
        editor.commit();
    }

    public String getAccount(){
        //取出数据,第一个参数是写入是的键，第二个参数是如果没有获取到数据就默认返回的值。
        return preferences.getString("account","defaultName");
    }
    public String getTenant(){
        return preferences.getString("tenant","defaultTenant");
    }

    //token
    public void setToken(String token){
        editor.putString("token",token);
        editor.commit();
    }
    public String getToken(){
        return preferences.getString("token","defaultToken");
    }

    //用户登录页面
    public void setIsLoginFirstUse(){
        editor.putBoolean("isLoginFirstUse",false);
        editor.commit();
    }
    public void setIsNotLoginFirstUse(){
        editor.putBoolean("isLoginFirstUse",true);
        editor.commit();
    }
    public boolean getIsLoginFirstUse(){
        return  preferences.getBoolean("isLoginFirstUse", true);
    }


    //设备激活页面
    public void setIsActivateFirstUse(){
        editor.putBoolean("isActivateFirstUse",false);
        editor.commit();
    }
    public boolean getIsActivateFirstUse(){
        return  preferences.getBoolean("isActivateFirstUse", true);
    }
}
