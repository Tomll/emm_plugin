package com.example.administrator.emmapplication.entity;

import com.example.administrator.emmapplication.entity.abstractEntity.RecycleViewImpl;
import com.example.administrator.emmapplication.utils.LogUtils;
import com.example.administrator.emmapplication.utils.PropertyUtils;

/**
 * Created by admine on 2018/4/24.
 */

public class SafeModeEntity extends RecycleViewImpl {

    public SafeModeEntity(Builder builder) {
        super(builder);
    }

    @Override
    public Object getData() {
        return "安全模式";
    }

    @Override
    public void clickOn() {
        LogUtils.d("click on set safe mode ......  ");
//        LogUtils.d("click on safe mode is  :  "+PropertyUtils.getProp("persist.sys.safemode"));
//        PropertyUtils.setProp("persist.sys.safemode","1");
//        PropertyUtils.setProp("persist.lsn.playbootmusic","1");
        PropertyUtils.enableSafeMode(mContext,true);
    }

    @Override
    public void clickOff() {
        LogUtils.d("click off set safe mode ......  ");
//        PropertyUtils.setProp("persist.sys.safemode","0");
//        PropertyUtils.setProp("persist.lsn.playbootmusic","0");
        PropertyUtils.enableSafeMode(mContext,false);
    }
}
