package com.example.administrator.emmapplication.device.deviceBrand;

import com.example.administrator.emmapplication.device.IDeviceFactory;
import com.example.administrator.emmapplication.device.deviceVersion.IVersion;
import com.example.administrator.emmapplication.utils.LogUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by admine on 2018/4/26.
 */

public class DeviceBrandFactory implements IDeviceFactory{
    public void build(Class cls) {
        try {
            LogUtils.d("execute DeviceBrandFactory build method...................");
            Constructor<?> constructor = cls.getConstructor();
            IBrand iBrand = (IBrand) constructor.newInstance();
            Method excuteBrand = cls.getMethod("excuteBrand");
            excuteBrand.invoke(iBrand);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
