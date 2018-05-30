package com.example.administrator.emmapplication.entity;

import com.example.administrator.emmapplication.entity.abstractEntity.RecycleViewImpl;
import com.example.administrator.emmapplication.utils.CameraPolicyUtils;

public class EnableOrDisableCameraEntity extends RecycleViewImpl{
    public EnableOrDisableCameraEntity(Builder builder) {
        super(builder);
    }

    @Override
    public void clickOn() {
        CameraPolicyUtils.getInstance().cameraDisabled(mContext,true);
    }

    @Override
    public void clickOff() {
        CameraPolicyUtils.getInstance().cameraDisabled(mContext,false);
    }

    @Override
    public Object getData() {
        return "启用禁用相机";
    }
}
