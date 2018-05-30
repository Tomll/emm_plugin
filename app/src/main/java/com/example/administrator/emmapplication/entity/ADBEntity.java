package com.example.administrator.emmapplication.entity;

import com.example.administrator.emmapplication.entity.abstractEntity.RecycleViewImpl;
import com.example.administrator.emmapplication.utils.ADBModelUtils;

/**
 * Created by admine on 2018/5/17.
 */

public class ADBEntity extends RecycleViewImpl {
    public ADBEntity(Builder builder) {
        super(builder);
    }

    @Override
    public Object getData() {
        return "USB使用MTP数据传输";
    }

    @Override
    public void clickOn() {
        ADBModelUtils.setADBType(mContext,ADBModelUtils.ALLOW_MTP);
    }

    @Override
    public void clickOff() {
        ADBModelUtils.setADBType(mContext,ADBModelUtils.ALLOW_USB);
    }
}
