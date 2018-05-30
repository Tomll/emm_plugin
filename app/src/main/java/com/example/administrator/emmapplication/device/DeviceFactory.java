package com.example.administrator.emmapplication.device;

import com.example.administrator.emmapplication.device.deviceNormal.INormal;
import com.example.administrator.emmapplication.device.deviceVersion.IVersion;
import com.example.administrator.emmapplication.entity.abstractEntity.RecycleViewImpl;
import com.example.administrator.emmapplication.utils.LogUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by admine on 2018/4/26.
 */

public class DeviceFactory {

    public void build(Class factory,Class cls){
        try {
            LogUtils.d("execute DeviceFactory build method...................");
            Constructor<?> constructor = factory.getConstructor();
            IDeviceFactory iDeviceFactory = (IDeviceFactory) constructor.newInstance();
            Method buildFunc = factory.getMethod("build",Class.class);
            buildFunc.invoke(iDeviceFactory,cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
