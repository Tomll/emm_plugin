package com.example.administrator.emmapplication.entity;

import android.widget.Toast;

import com.example.administrator.emmapplication.entity.abstractEntity.RecycleViewImpl;
import com.example.administrator.emmapplication.utils.mobileInfos.MobileInfosUtils;

public class MobileInfoEntity extends RecycleViewImpl {
    public MobileInfoEntity(Builder builder) {
        super(builder);
    }

    @Override
    public Object getData() {
        return "手机信息";
    }

    @Override
    public void clickOn() {
        MobileInfosUtils mobile = new MobileInfosUtils(mContext);
        String mobileInfos = mobile.getAllInfos2();
        Toast.makeText(mContext,mobileInfos,Toast.LENGTH_LONG).show();
    }

    @Override
    public void clickOff() {
        MobileInfosUtils mobile = new MobileInfosUtils(mContext);
        String mobileInfos = mobile.getAllInfos2();
        Toast.makeText(mContext,mobileInfos,Toast.LENGTH_LONG).show();
    }
}
