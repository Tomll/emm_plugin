package com.example.administrator.emmapplication.device.deviceBrand;

import com.example.administrator.emmapplication.utils.LogUtils;

/**
 * Created by admine on 2018/4/27.
 */

public class BdNokioMax2 extends BrandDecorator {

    private String addedState;

    @Override
    public void excuteBrand() {
        super.excuteBrand();
        addedState = "Nokio Max2";
        LogUtils.d("Concrete Object Nokio Max2");
    }
}
