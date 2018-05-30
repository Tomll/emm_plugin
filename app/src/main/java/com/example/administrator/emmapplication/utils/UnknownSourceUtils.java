package com.example.administrator.emmapplication.utils;

import android.content.Context;
import android.provider.Settings;

/**
 * Created by admine on 2018/4/17.
 */

public class UnknownSourceUtils {

    public static boolean EnableOrDisableUnknownSource(Context context, boolean isOpen){
        boolean success = false;
        int result = Settings.Secure.getInt(context.getContentResolver(),
                Settings.Secure.INSTALL_NON_MARKET_APPS, 0);
        LogUtils.d("enable install unknown source result : "+result);
        if (result == 0 && isOpen) {
            success = Settings.Secure.putString(context.getContentResolver(), Settings.Secure.INSTALL_NON_MARKET_APPS, "1");
        }else if(!isOpen){
            success = Settings.Secure.putString(context.getContentResolver(), Settings.Secure.INSTALL_NON_MARKET_APPS, "0");

        }
        LogUtils.d("enable install unknown source : "+success);
        return success;
    }

}
