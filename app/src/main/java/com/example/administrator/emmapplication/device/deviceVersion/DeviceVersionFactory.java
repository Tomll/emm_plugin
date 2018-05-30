package com.example.administrator.emmapplication.device.deviceVersion;

import com.example.administrator.emmapplication.device.IDeviceFactory;
import com.example.administrator.emmapplication.entity.abstractEntity.RecycleViewImpl;
import com.example.administrator.emmapplication.utils.LogUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by admine on 2018/4/26.
 */

public class DeviceVersionFactory  implements IDeviceFactory{
    @Override
    public void build(Class cls) {
        try {
            LogUtils.d("execute DeviceVersionFactory build method...................");
            Constructor<?> constructor = cls.getConstructor();
            IVersion iVersion = (IVersion) constructor.newInstance();
            Method versionMethod = cls.getMethod("version");
            versionMethod.invoke(iVersion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
