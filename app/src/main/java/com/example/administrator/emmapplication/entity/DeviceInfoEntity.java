package com.example.administrator.emmapplication.entity;

import com.example.administrator.emmapplication.entity.abstractEntity.RecycleViewImpl;
import com.example.administrator.emmapplication.utils.DeviceUtils;
import com.example.administrator.emmapplication.utils.LogUtils;
import com.example.administrator.emmapplication.utils.TrustedCredentialsUtils;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeviceInfoEntity extends RecycleViewImpl {

    public DeviceInfoEntity(Builder builder){
        super(builder);
    }

    @Override
    public Object getData() {
        return "设备安全补丁";
    }

    @Override
    public void clickOn() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("获取设备补丁号");
                System.out.println("设备补丁号"+DeviceUtils.getSecurityPatchInfo());
                LogUtils.d(DeviceUtils.getSecurityPatchInfo());
                TrustedCredentialsUtils.listX509Certificate();
            }
        }).start();

    }

    @Override
    public void clickOff() {

    }
}
