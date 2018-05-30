package com.example.administrator.emmapplication.device.deviceBrand;

import com.example.administrator.emmapplication.utils.LogUtils;

/**
 * Created by admine on 2018/4/27.
 */

public class BdHuaWeiMax1Decorator extends BrandDecorator {

    private String addedState;

    @Override
    public void excuteBrand() {
        super.excuteBrand();
        addedState = "Hua Wei Max1";
        LogUtils.d("Concrete Object Hua Wei Max1");
        androidVersion();
        huaWeiUI();
    }

    private void  androidVersion(){
        LogUtils.d("Concrete Object Hua Wei Max1 android version 4.0");
    }

    private void huaWeiUI(){
        LogUtils.d("Concrete Object Hua Wei Max1 hua wei ui cusHuawei");
    }


}
