package com.example.administrator.emmapplication.entity;

import com.example.administrator.emmapplication.entity.abstractEntity.RecycleViewImpl;
import com.example.administrator.emmapplication.utils.EnableOrDisableAppUtils;
import com.example.administrator.emmapplication.utils.LogUtils;

/**
 * Created by admine on 2018/4/25.
 */

public class EnableOrDisableAppEntity extends RecycleViewImpl {

    public EnableOrDisableAppEntity(Builder builder) {
        super(builder);
    }

    @Override
    public Object getData() {
        return "启用或禁用第三方应用";
    }

    @Override
    public void clickOn() {
        LogUtils.d("enable app....................");
        EnableOrDisableAppUtils.enableOrDisableApp(mContext,"com.tencent.mm",true);
    }

    @Override
    public void clickOff() {
        LogUtils.d("disable app....................");
        EnableOrDisableAppUtils.enableOrDisableApp(mContext,"com.tencent.mm",false);
    }
}
