package com.example.administrator.emmapplication.entity;

import android.content.Intent;

import com.example.administrator.emmapplication.entity.abstractEntity.RecycleViewImpl;
import com.example.administrator.emmapplication.service.MyAccessibilityService;

public class WechatQQLimitEntity extends RecycleViewImpl {
    public WechatQQLimitEntity(Builder builder) {
        super(builder);
    }

    @Override
    public Object getData() {
        return "微信QQ限制";
    }

    @Override
    public void clickOn() {
        Intent keyword = new Intent(mContext, MyAccessibilityService.class);
        keyword.setAction("wechat.qq.limit.true");
        mContext.startService(keyword);
    }

    @Override
    public void clickOff() {
        Intent keyword = new Intent(mContext, MyAccessibilityService.class);
        keyword.setAction("wechat.qq.limit.false");
        mContext.startService(keyword);
    }
}
