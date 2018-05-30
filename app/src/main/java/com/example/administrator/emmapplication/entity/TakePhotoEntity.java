package com.example.administrator.emmapplication.entity;

import android.content.Context;

import com.example.administrator.emmapplication.entity.abstractEntity.RecycleViewImpl;
import com.example.administrator.emmapplication.utils.Camera2TakePhotoUtils;
import com.example.administrator.emmapplication.utils.MemoryUtils;

public class TakePhotoEntity extends RecycleViewImpl{
    public TakePhotoEntity(Builder builder) {
        super(builder);
    }

    @Override
    public Object getData() {
        return "拍照";
    }

    @Override
    public void clickOn() {
        try {
            Context context = MemoryUtils.getContext();
            Camera2TakePhotoUtils camera2 = new Camera2TakePhotoUtils(context);
            camera2.takePicture();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void clickOff() {

    }
}
