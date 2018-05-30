package com.example.administrator.emmapplication.entity;

import com.example.administrator.emmapplication.entity.abstractEntity.RecycleViewImpl;
import com.example.administrator.emmapplication.utils.UsbManagerUtils;

public class UsbManagerEntity extends RecycleViewImpl {
    public UsbManagerEntity(Builder builder) {
        super(builder);
    }

    @Override
    public Object getData() {
        return "USB启停";
    }

    @Override
    public void clickOn() {
        UsbManagerUtils.getInstance(mContext).start();
    }

    @Override
    public void clickOff() {
        UsbManagerUtils.getInstance(mContext).stop();
    }
}
