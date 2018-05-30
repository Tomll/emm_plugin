package com.example.administrator.emmapplication.entity;

import com.example.administrator.emmapplication.entity.abstractEntity.RecycleViewImpl;
import com.example.administrator.emmapplication.utils.LogUtils;
import com.example.administrator.emmapplication.utils.UnknownSourceUtils;

/**
 * Created by admine on 2018/4/17.
 */

public class UnKnownSourceEntity extends RecycleViewImpl {

    public UnKnownSourceEntity(Builder builder){
        super(builder);
    }

    @Override
    public Object getData() {
        return "未知来源";
    }

    @Override
    public void clickOn() {
        LogUtils.d("Clicked On Unknown Source button : "+ (mContext == null));
        UnknownSourceUtils.EnableOrDisableUnknownSource(mContext,true);
    }

    @Override
    public void clickOff() {
        LogUtils.d("Clicked Off Unknown Source button : "+ (mContext == null));
        UnknownSourceUtils.EnableOrDisableUnknownSource(mContext,false);
    }
}
