package com.example.administrator.emmapplication.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.Set;

import static android.content.ContentValues.TAG;

public class BlueToothListener {
    private Context ctx;
    private static BroadcastReceiver  blueTooth;

    public static BlueToothListener newInstance(Context context){
        return new BlueToothListener(context);
    }

    private BlueToothListener(Context ctx){
        this.ctx=ctx;
    }

    public void startListener(){
        blueTooth=new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO Auto-generated method stub
                String action = intent.getAction();
                if(action != null) {
                    if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                        //监听蓝牙打开与否的状态
                        int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);

                        switch (state) {
                            case BluetoothAdapter.STATE_TURNING_ON:
                                Log.e("blueTooth", "蓝牙模块正在打开");
                                BluetoothAdapter.getDefaultAdapter().disable();
                                break;
                            case BluetoothAdapter.STATE_ON:
                                Log.e("blueTooth", " 蓝牙模块处于开启状态");
                                BluetoothAdapter.getDefaultAdapter().disable();
                                break;
                        }
                    }
                }
            }
        };

        IntentFilter filter1 = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        ctx.registerReceiver(blueTooth,filter1);
    }

    public void stopListener(){
        ctx.unregisterReceiver(blueTooth);
    }


}
