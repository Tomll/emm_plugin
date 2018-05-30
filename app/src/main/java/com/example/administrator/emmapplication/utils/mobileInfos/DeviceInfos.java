package com.example.administrator.emmapplication.utils.mobileInfos;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.provider.Settings;

import static android.security.KeyStore.getApplicationContext;

public class DeviceInfos {
    private Context mContext;
//    public static MobileInfosUtils newInstance(Context context){
//        return new MobileInfosUtils(context);
//    }
    public DeviceInfos(Context context){
        this.mContext=context;
    }
    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public  String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }
    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public  String getSystemModel() {
        return Build.MODEL;
    }
    /**
     * 获取手机厂商
     *
     * @return  手机厂商
     */
    public  String getDeviceBrand() {
        return Build.BRAND;
    }



    /**
     * 获取电源状态
     *
     * @return 电源状态
     */
    public String battery(){
        Intent batteryInfoIntent = mContext.getApplicationContext()
                .registerReceiver( null ,
                        new IntentFilter( Intent.ACTION_BATTERY_CHANGED ) ) ;
        if(batteryInfoIntent!=null){
            int level = batteryInfoIntent.getIntExtra("level", 0);//电量（0-100）
            return level+"%";
        }
        return "--";
    }

    /**
     * 获取序列号
     */
    public static String getSerialNumber(){
        String serial = "--";
        try {
            serial = Settings.System.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serial;
    }
}
