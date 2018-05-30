package com.example.administrator.emmapplication.device.deviceBrand;

import com.example.administrator.emmapplication.device.deviceVersion.VersionImpl;

/**
 * Created by admine on 2018/4/27.
 */

public abstract class BrandDecorator extends BrandImpl {

    protected BrandImpl brand;

    public void setBrand(BrandImpl brand){
        this.brand = brand;
    }

    @Override
    public void excuteBrand() {
        if(brand != null){
            brand.excuteBrand();
        }
    }


}
