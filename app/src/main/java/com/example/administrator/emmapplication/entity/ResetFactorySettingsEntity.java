package com.example.administrator.emmapplication.entity;

import android.content.Context;

import com.example.administrator.emmapplication.entity.abstractEntity.RecycleViewImpl;
import com.example.administrator.emmapplication.entity.entityFactory.IEntityFactory;
import com.example.administrator.emmapplication.entity.interfaceEntity.IRecycleViewEntity;
import com.example.administrator.emmapplication.utils.ResetFactorySettingsUtils;

/**
 * Created by admine on 2018/5/3.
 */

public class ResetFactorySettingsEntity extends RecycleViewImpl {

    public ResetFactorySettingsEntity(Builder builder) {
        super(builder);
    }

    @Override
    public Object getData() {
        return "恢复出厂设置";
    }

    @Override
    public void clickOn() {
        ResetFactorySettingsUtils.resetFactorySettings(mContext);
    }

    @Override
    public void clickOff() {

    }
}
