package com.example.administrator.emmapplication.device.deviceBrand;

import com.example.administrator.emmapplication.utils.LogUtils;

/**
 * Created by admine on 2018/4/27.
 */

public class BdXiaoMiMax2Decorator extends BrandDecorator {

    private String addedState;

    @Override
    public void excuteBrand() {
        super.excuteBrand();
        addedState = "Xiao Mi";
        LogUtils.d("Concrete Object Xiao Mi Max2");
        androidVersion();
        xiaoMiUI();
    }

    private void  androidVersion(){
        LogUtils.d("Concrete Object Xiao Mi Max1 android version 4.0");
    }

    private void xiaoMiUI(){
        LogUtils.d("Concrete Object Xiao Mi Max2 MUI");
    }

}
