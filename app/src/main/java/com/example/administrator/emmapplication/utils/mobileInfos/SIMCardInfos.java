package com.example.administrator.emmapplication.utils.mobileInfos;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.RemoteException;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.CellLocation;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.Method;

import static android.content.ContentValues.TAG;
import static android.content.Context.TELEPHONY_SERVICE;
import static android.content.Context.TELEPHONY_SUBSCRIPTION_SERVICE;

public class SIMCardInfos {
    private TelephonyManager tm;
    private Context mContext;

    public SIMCardInfos(Context context) {
        this.mContext = context;
        tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }
//    public static SIMCardInfos newInstance(Context context){
//        return new SIMCardInfos(context);
//    }

    //电话状态 0 无活动 1 响铃 2 摘机
    public int callState() {
        return tm.getCallState();
    }


    //设备软件版本号
    public String deviceSoftWareVersion() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "--";
        }
        return tm.getDeviceSoftwareVersion();
    }

    //服务商名称
    public String simOperatorName() {
        if (tm.getSimState() == TelephonyManager.SIM_STATE_READY) {
            return tm.getSimOperatorName();
        }
        return "--";
    }

    //手机类型
    public int phontType() {
        return tm.getPhoneType();
    }

    //是否开启漫游
    public int netWorkRoaming() {
        if (tm.isNetworkRoaming()) {
            return 1;
        }
        return 0;
    }

    //手机号
    public String lineNumber() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "--";
        }
        String number = tm.getLine1Number();
        if(number!=null) {
            return number;
        }else{
            return "--";
        }
    }

//    public String deviceId(){
//        return tm.getDeviceId();
//    }

    public String getSubscriberId(int subId) {
        String imsi="--";
        try {
            Method getSubId=TelephonyManager.class.getMethod("getSubscriberId",int.class);
            SubscriptionManager sm = (SubscriptionManager)mContext.getSystemService(TELEPHONY_SUBSCRIPTION_SERVICE);
            if(sm!=null) {
                SubscriptionInfo simInfo = sm.getActiveSubscriptionInfoForSimSlotIndex(subId);
                if(simInfo!=null) {
                    Object id = getSubId.invoke(tm, simInfo.getSubscriptionId());
                    imsi = id != null ? (String) id : "--";
                }
            }
            return imsi;
        }catch (Exception e){
            e.printStackTrace();
        }
        return imsi;
    }

    public String getMachineImei(int slotId) {
        String imei = "";
        try {
            Method getImei=TelephonyManager.class.getMethod("getImei",int.class);//(int slotId)
            imei = (String) getImei.invoke(tm,slotId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imei;
    }

}
