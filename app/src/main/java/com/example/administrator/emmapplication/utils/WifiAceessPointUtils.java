package com.example.administrator.emmapplication.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.ResultReceiver;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class WifiAceessPointUtils {

    public static boolean isWifiApOpen(Context context) {
        try {
            WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            Method method = manager.getClass().getDeclaredMethod("getWifiApState");
            int state = (int) method.invoke(manager);
            Field field = manager.getClass().getDeclaredField("WIFI_AP_STATE_ENABLED");
            int value = (int) field.get(manager);
            if (state == value) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getSSID(Context context) {
        String ssid = "default";
        try {
            WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            Method method = manager.getClass().getDeclaredMethod("getWifiApConfiguration");
            WifiConfiguration configuration = (WifiConfiguration) method.invoke(manager);
            ssid = configuration.SSID;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ssid;
    }

    public static void createAp(Context context, String SSID, String passWord, boolean isOpen) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            creatOREOAP(context, isOpen);
        } else {
            createNormalAP(context, SSID, passWord, isOpen);
        }
    }

    private static void createNormalAP(Context context, String SSID, String passWord, boolean isOpen) {
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        //开启热点先关闭Wifi
        if (manager.isWifiEnabled()) {
            manager.setWifiEnabled(false);
        }
        WifiConfiguration apConfiguration = new WifiConfiguration();
        apConfiguration.SSID = SSID;
        apConfiguration.preSharedKey = passWord;
        apConfiguration.hiddenSSID = false;
        apConfiguration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);     //开放系统认证
        apConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        apConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        if (isOpen) {
            apConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        } else {
            apConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        }
        apConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        apConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        apConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        apConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        apConfiguration.status = WifiConfiguration.Status.ENABLED;
        Method method = null;
        try {
            method = manager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, Boolean.TYPE);
            method.invoke(manager, apConfiguration, isOpen);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.d("开启wifi热点成功.....................2");
    }


    private static void creatOREOAP(Context context, boolean isOpen) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            Field iConnMgrField = connectivityManager.getClass().getDeclaredField("mService");
            iConnMgrField.setAccessible(true);
            Object iConnMgr = iConnMgrField.get(connectivityManager);
            Class<?> iConnMgrClass = Class.forName(iConnMgr.getClass().getName());
            if (isOpen) {
                Method startTethering = iConnMgrClass.getMethod("startTethering", int.class, ResultReceiver.class, boolean.class);
                startTethering.invoke(iConnMgr, 0, null, true);
            } else {
                Method startTethering = iConnMgrClass.getMethod("stopTethering", int.class);
                startTethering.invoke(iConnMgr, 0);
            }
            LogUtils.d("开启wifi热点成功.....................1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
