package com.example.administrator.emmapplication.entity;

import com.example.administrator.emmapplication.entity.abstractEntity.RecycleViewImpl;
import com.example.administrator.emmapplication.utils.LockPolicyUtils;

public class LockEntity extends RecycleViewImpl {
    public LockEntity(Builder builder) {
        super(builder);
    }

    @Override
    public Object getData() {
        return "锁屏";
    }

    @Override
    public void clickOn() {
        LockPolicyUtils.getInstance().lock(mContext);
    }

    @Override
    public void clickOff() {

    }
}
