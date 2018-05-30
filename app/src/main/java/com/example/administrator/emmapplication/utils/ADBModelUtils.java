package com.example.administrator.emmapplication.utils;

import android.content.Context;
import android.hardware.usb.UsbManager;

/**
 * Created by admine on 2018/5/17.
 */

public class ADBModelUtils {

    public static final String ALLOW_USB = "setprop persist.sys.usb.config mtp,adb" ;
    public static final String DISALLOW_USB = "setprop persist.sys.usb.config none" ;
    public static final String ALLOW_MTP = "setprop persist.sys.usb.config mtp" ;

    public static void setADBType(Context context,String type){
        CommandUils.command(type);
    }

}
