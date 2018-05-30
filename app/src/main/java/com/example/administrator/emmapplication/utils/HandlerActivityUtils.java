package com.example.administrator.emmapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.administrator.emmapplication.activity.LoginActivity;
import com.example.administrator.emmapplication.activity.MenuActivity;

public class HandlerActivityUtils {
    public static HandlerActivityUtils getInstance(){
        return new HandlerActivityUtils();
    }

    public void activateActivity(Activity context){
        boolean isActivateFirstUse = SharedfPreferencesUtils.getInstance(context).getIsActivateFirstUse();
        boolean isLoginFirstUse = SharedfPreferencesUtils.getInstance(context).getIsLoginFirstUse();
        if(!isActivateFirstUse){
            if(!isLoginFirstUse) {
                context.startActivity(new Intent(context, MenuActivity.class));
            }else{
                context.startActivity(new Intent(context, LoginActivity.class));
            }
            context.finish();
        }
    }

    public void loginActivity(Activity context){
        boolean isLoginFirstUse = SharedfPreferencesUtils.getInstance(context).getIsLoginFirstUse();
        if(!isLoginFirstUse){
            context.startActivity(new Intent(context,MenuActivity.class));
            context.finish();
        }
    }
}
