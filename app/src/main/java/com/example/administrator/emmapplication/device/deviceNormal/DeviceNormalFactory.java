package com.example.administrator.emmapplication.device.deviceNormal;

import com.example.administrator.emmapplication.device.IDeviceFactory;
import com.example.administrator.emmapplication.device.deviceVersion.IVersion;
import com.example.administrator.emmapplication.utils.LogUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by admine on 2018/4/26.
 */

public class DeviceNormalFactory implements IDeviceFactory{
    public void build(Class cls) {
        try {
            LogUtils.d("execute DeviceNormalFactory build method...................");
            Constructor<?> constructor = cls.getConstructor();
            INormal iNormal = (INormal) constructor.newInstance();
            Method defaultMethod = cls.getMethod("defaultMethod");
            defaultMethod.invoke(iNormal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
