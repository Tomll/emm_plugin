package com.example.administrator.emmapplication.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class WifiListener {
    private Context ctx;
    private static BroadcastReceiver mwifiBroadcastReceiver;
    private static WifiManager wifiManager;

    public static WifiListener newInstance(Context context){
        return new WifiListener(context);
    }
    private WifiListener(Context ctx){
        this.ctx=ctx;
        wifiManager=(WifiManager) ctx.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    public void stratSwitch(){
        // 监听wifi状态广播
        mwifiBroadcastReceiver = new BroadcastReceiver() {
            private void disabled(){
                WifiManager wifiManager = (WifiManager) ctx.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                if(wifiManager!=null){
                    wifiManager.setWifiEnabled(false);
                }
            }

            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null) {
                    String action=intent.getAction();
                    if(action!=null&&WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                        int state = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
                        switch (state) {
                            case WifiManager.WIFI_STATE_ENABLING:
                                Log.d(TAG, "WLAN正在打开中");
                                disabled();
                                break;
                            case WifiManager.WIFI_STATE_ENABLED:
                                Log.d(TAG, "WLAN已经打开，正在关闭");
                                disabled();
                                break;
                        }
                    }
                }
            }
        };

        IntentFilter myIntentFilter;
        myIntentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        this.ctx.registerReceiver(mwifiBroadcastReceiver,myIntentFilter);
    }

    public void stopSwitch(){
        Log.d(TAG, "注销该WIFI广播");
        try {
            this.ctx.unregisterReceiver(mwifiBroadcastReceiver);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void enable(){
        wifiManager.setWifiEnabled(true);
    }
    public void disable(){
        wifiManager.setWifiEnabled(false);
    }
}
