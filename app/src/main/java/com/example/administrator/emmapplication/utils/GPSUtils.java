package com.example.administrator.emmapplication.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

public class GPSUtils {

    //此段代码是控制GPS开启关闭

    public static void  toggleGPS(Context context,boolean isOpen) {
//        Settings.Secure.setLocationProviderEnabled( context.getContentResolver(), LocationManager.GPS_PROVIDER, isOpen );
        LogUtils.d("ope gps exception : 1" );
        Intent gpsIntent = new Intent();
        gpsIntent.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider");
        LogUtils.d("ope gps exception : 2" );
        gpsIntent.addCategory("android.intent.category.ALTERNATIVE");
        LogUtils.d("ope gps exception : 3" );
        gpsIntent.setData(Uri.parse("custom:3"));
        LogUtils.d("ope gps exception : 4" );
        try {
            PendingIntent.getBroadcast(context, 0, gpsIntent, 0).send();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.d("ope gps exception : " + e.toString());
        }
    }



//此段代码是得到当前GPS的状态  （开启 true 关闭 false）

    public static Boolean isGPSEnable(Context context) {
        String str = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        LogUtils.d("GPS 状态   ：  "+str);
        if (str != null) {
            return str.contains("gps");
        } else {
            return false;
        }
    }
}
