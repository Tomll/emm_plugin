package com.example.administrator.emmapplication.utils;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.os.UserManager;

import com.example.administrator.emmapplication.receiver.MyAdmin;

import java.lang.reflect.Method;

/**
 * Created by admine on 2018/4/24.
 */

public class PropertyUtils {

    public static void setProperty(String key,String value){
        System.setProperty(key,value);
    }

    public static <T>T getProperty(String key){
        return  (T) System.getProperty(key);
    }

    public static void enableSafeModeOne(Context context,boolean isEnable){
        if(Build.VERSION.SDK_INT > 8){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        DevicePolicyManager manager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
//        ComponentName admin = new ComponentName(context, DeviceAdminReceiver.class);
        ComponentName admin = new ComponentName(context, MyAdmin.class);

        // To disable safe boot
        manager.addUserRestriction(admin, UserManager.DISALLOW_SAFE_BOOT);
        // To enable safe boot again
//            manager.clearUserRestriction(admin, UserManager.DISALLOW_SAFE_BOOT);

    }

    public static void enableSafeMode(Context context,boolean isEnable){
        try {
            DevicePolicyManager manager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName admin = new ComponentName(context, DeviceAdminReceiver.class);
//            manager.setProfileEnabled(ComponentName.createRelative(context, MyAdmin.class.getName()));
//            ComponentName admin = new ComponentName(context, MyAdmin.class);

            // To disable safe boot
            manager.addUserRestriction(admin, UserManager.DISALLOW_SAFE_BOOT);
            // To enable safe boot again
//            manager.clearUserRestriction(admin, UserManager.DISALLOW_SAFE_BOOT);
        }catch (Exception e){
            LogUtils.d("occur exception : "+e.toString());
        }

    }

    public static void setProp(String key,String value){
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            Method m = cls.getDeclaredMethod("set",String.class,String.class);
            m.invoke(cls,key,value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean getProp(String key){
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            Method m = cls.getDeclaredMethod("get",String.class,String.class);
            String value = (String)m.invoke(cls,key,"-1");
            if(value.equals("1")){
                return true;
            }else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
