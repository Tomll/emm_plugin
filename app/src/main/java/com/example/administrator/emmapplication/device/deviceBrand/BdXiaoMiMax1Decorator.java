package com.example.administrator.emmapplication.device.deviceBrand;

import com.example.administrator.emmapplication.utils.LogUtils;

/**
 * Created by admine on 2018/4/27.
 */

public class BdXiaoMiMax1Decorator extends BrandDecorator {

    private String addedState;

    @Override
    public void excuteBrand() {
        super.excuteBrand();
        addedState = "Hua Wei Max1";
        LogUtils.d("Concrete Object Xiao Mi Max1");
    }


}
