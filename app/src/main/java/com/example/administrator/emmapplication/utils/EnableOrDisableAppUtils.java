package com.example.administrator.emmapplication.utils;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by admine on 2018/4/25.
 */

public class EnableOrDisableAppUtils {

    public static void enableOrDisableApp(Context context,String packageName,boolean isEnable){
        int newState = -1;
        PackageManager pm = context.getPackageManager();
        if(isEnable){
            newState = PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
        }else {
            newState = PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
        }
        pm.setApplicationEnabledSetting(packageName,newState,0);
    }

}
