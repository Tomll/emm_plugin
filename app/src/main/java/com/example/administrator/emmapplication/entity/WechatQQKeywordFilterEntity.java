package com.example.administrator.emmapplication.entity;

import android.content.Intent;

import com.example.administrator.emmapplication.entity.abstractEntity.RecycleViewImpl;
import com.example.administrator.emmapplication.service.MyAccessibilityService;

public class WechatQQKeywordFilterEntity extends RecycleViewImpl{
    public WechatQQKeywordFilterEntity(Builder builder) {
        super(builder);
    }

    @Override
    public Object getData() {
        return "微信QQ关键字过滤（军队、部队）";
    }

    @Override
    public void clickOn() {
        Intent keyword = new Intent(mContext, MyAccessibilityService.class);
        keyword.setAction("wechat.keyword.filtering.true");
        mContext.startService(keyword);
    }

    @Override
    public void clickOff() {
        Intent keyword = new Intent(mContext, MyAccessibilityService.class);
        keyword.setAction("wechat.keyword.filtering.false");
        mContext.startService(keyword);
    }
}
