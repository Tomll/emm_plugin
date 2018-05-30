package com.example.administrator.emmapplication.device.deviceBrand;

import com.example.administrator.emmapplication.utils.LogUtils;

/**
 * Created by admine on 2018/4/27.
 */

public class BdNoKioMax1 extends BrandDecorator {

    private String addedState;

    @Override
    public void excuteBrand() {
        super.excuteBrand();
        addedState = "Nokio Max1";
        LogUtils.d("Concrete Object Nokio Max1");
        androidVersion();
        nokioUI();

    }

    private void  androidVersion(){
        LogUtils.d("Concrete Object Nokio android version 4.0");
    }
    
    private void nokioUI(){
        LogUtils.d("Concrete Object Nokio Max1 Nokio ui cusNokio");
    }


}
