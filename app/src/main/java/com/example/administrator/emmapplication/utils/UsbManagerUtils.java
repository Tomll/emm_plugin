package com.example.administrator.emmapplication.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemProperties;
import android.provider.SyncStateContract;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Field;


import static android.content.ContentValues.TAG;

public class UsbManagerUtils {

//    因为是通过设置Android的系统属性实现的，因此要求app有system权限。


    private Context mContext;
    private UsbManagerUtils _this;
    private static final String ACTION_USB_STATE = "android.hardware.usb.action.USB_STATE";
    private static final String USB_CONNECTED = "connected";
    private static final String USB_FUNCTION_ADB = "adb";
    private static final String USB_FUNCTION_MTP = "mtp";

    private static UsbManager usbManager;
    private static BroadcastReceiver receiver;

    public static UsbManagerUtils getInstance(Context context){
        return  new UsbManagerUtils(context);
    }

    private UsbManagerUtils(Context context){
        this.mContext=context;
        this._this=this;
        init();
    }

    public void start(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UsbManager.ACTION_USB_ACCESSORY_ATTACHED);
        intentFilter.addAction(UsbManager.ACTION_USB_ACCESSORY_DETACHED);
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        intentFilter.addAction(ACTION_USB_STATE);
        this.mContext.getApplicationContext().registerReceiver(receiver, intentFilter);

        boolean hasUsbHost = mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_USB_HOST);
        boolean hasUsbAccessory = mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_USB_ACCESSORY);

        System.out.println("USB广播开始了..."+hasUsbHost+"  /  "+hasUsbAccessory);
    }

    public void stop(){
        try {
            this.mContext.getApplicationContext().unregisterReceiver(receiver);
        }catch (Exception e){
            Log.e(TAG,e.getMessage());
        }
    }

    private void init(){
        usbManager = (UsbManager) mContext.getSystemService(Context.USB_SERVICE);
        receiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(intent.hasExtra(UsbManager.EXTRA_PERMISSION_GRANTED)) {
                    boolean permissionGranted = intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false);
                    Log.i(TAG,"permissionGranted : " + permissionGranted);
                }
                if(action!=null) {
                    switch (action) {
                        case UsbManager.ACTION_USB_ACCESSORY_ATTACHED:
                        case UsbManager.ACTION_USB_ACCESSORY_DETACHED:
                            //Name of extra for ACTION_USB_ACCESSORY_ATTACHED and ACTION_USB_ACCESSORY_DETACHED broadcasts containing the UsbAccessory object for the accessory.
                            UsbAccessory accessory = intent.getParcelableExtra(UsbManager.EXTRA_ACCESSORY);
                            Log.i(TAG,accessory.toString());
                            Toast.makeText(mContext,accessory.toString(),Toast.LENGTH_SHORT).show();
                            break;
                        case UsbManager.ACTION_USB_DEVICE_ATTACHED:
                        case UsbManager.ACTION_USB_DEVICE_DETACHED:
                            //Name of extra for ACTION_USB_DEVICE_ATTACHED and ACTION_USB_DEVICE_DETACHED broadcasts containing the UsbDevice object for the device.
                            UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                            Log.i(TAG,device.toString());
                            Toast.makeText(mContext,device.toString(),Toast.LENGTH_SHORT).show();
                            break;
                        case ACTION_USB_STATE:
                            boolean connected = intent.getBooleanExtra(USB_CONNECTED, false);
                            boolean function_adb = intent.getBooleanExtra(USB_FUNCTION_ADB, false);
                            boolean function_mtp = intent.getBooleanExtra(USB_FUNCTION_MTP, false);
                            if(function_mtp){
                                //禁止使用mtp
//                                _this.AllowUseUsbAdb();
                                _this.enableAdb();
                            }
                            String msg=(connected?"已连接；":"没有连接；")+(function_adb?"adb模式开启；":"adb模式关闭；")+(function_mtp?"mtp模式开启":"mtp模式关闭");
                            Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }
        };
    }

    private  void AllowUseUsb() {    //允许使用USB
        command("setprop persist.sys.usb.config mtp,adb");
    }
    private  void DisallowUseUsb() {   //禁止使用USB
        command("setprop persist.sys.usb.config none");
    }

    //只想关闭USB调试功能,即只允许使用MTP传输
    private void AllowUseUsbMtp(){
        command("setprop persist.sys.usb.config mtp");
    }
    //只想关闭USB文件传输功能
    private void AllowUseUsbAdb(){
        command("setprop persist.sys.usb.config adb");
    }

    private void enableAdb(){
        try {
            SystemProperties.set("sys.usb.config",USB_FUNCTION_ADB);
        }catch (Exception e){
            Log.e(TAG,e.getMessage());
        }
    }

    private void enableMtp(){
        try {
            SystemProperties.set("sys.usb.config",USB_FUNCTION_MTP);
        }catch (Exception e){
            Log.e(TAG,e.getMessage());
        }
    }



    private  void command(String com) {
        try {
            Log.i(TAG, "Command : " + com);
            Runtime.getRuntime().exec(com);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



//    public boolean usb(){
//        try {
//            final int MSG_SET_CURRENT_FUNCTIONS = 2;
//            IBinder usb_service = ServiceManager.getService(Context.USB_SERVICE);
//
//            Class<?> c_UsbService = SyncStateContract.Helpers.findClass("com.android.server.usb.UsbService");
//            Field f_mDeviceManager = SyncStateContract.Helpers.findField(c_UsbService, "mDeviceManager");
//            Object o_mDeviceManager = f_mDeviceManager.get(usb_service);
//
//            Class<?> c_UsbDeviceManager = SyncStateContract.Helpers.findClass("com.android.server.usb.UsbDeviceManager");
//            Field f_mHandler = SyncStateContract.Helpers.findField(c_UsbDeviceManager, "mHandler");
//            Object o_mHandler = f_mHandler.get(o_mDeviceManager);
//            Handler h_mHandler = (Handler) o_mHandler;
//
//            // 禁用adb
//            Field f_mAdbEnabled = SyncStateContract.Helpers.findField(c_UsbDeviceManager, "mAdbEnabled");
//            f_mAdbEnabled.setBoolean(o_mDeviceManager, false);
//
//            // 不提供USB服务
//            Message msg = Message.obtain(h_mHandler, MSG_SET_CURRENT_FUNCTIONS);
//            msg.obj = new String("none");
//            msg.arg1 = 0;
//            h_mHandler.removeMessages(MSG_SET_CURRENT_FUNCTIONS);
//            h_mHandler.sendMessage(msg);
//
//            Log.d("test", "[End]try disconnect USB");
//            return true;
//        } catch (Throwable e) {
//            e.printStackTrace();
//            Log.e("test", "[disconnectUSB][Throwable]" + e.getMessage());
//        }
//        return false;
//    }
}
