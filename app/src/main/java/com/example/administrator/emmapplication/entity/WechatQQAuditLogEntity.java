package com.example.administrator.emmapplication.entity;

import android.content.Intent;

import com.example.administrator.emmapplication.entity.abstractEntity.RecycleViewImpl;
import com.example.administrator.emmapplication.service.MyAccessibilityService;

public class WechatQQAuditLogEntity extends RecycleViewImpl {
    public WechatQQAuditLogEntity(Builder builder) {
        super(builder);
    }

    @Override
    public Object getData() {
        return "微信QQ审计日志";
    }

    @Override
    public void clickOn() {
        Intent keyword = new Intent(mContext, MyAccessibilityService.class);
        keyword.setAction("wechat.qq.auditLog.true");
        mContext.startService(keyword);
    }

    @Override
    public void clickOff() {
        Intent keyword = new Intent(mContext, MyAccessibilityService.class);
        keyword.setAction("wechat.qq.auditLog.false");
        mContext.startService(keyword);
    }
}
