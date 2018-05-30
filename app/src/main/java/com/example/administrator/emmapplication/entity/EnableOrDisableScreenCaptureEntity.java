package com.example.administrator.emmapplication.entity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.example.administrator.emmapplication.entity.abstractEntity.RecycleViewImpl;
import com.example.administrator.emmapplication.utils.ScreenShotListenUtils;

import java.io.File;

/**
 * Created by admine on 2018/5/17.
 */

public class EnableOrDisableScreenCaptureEntity extends RecycleViewImpl {
    private boolean isOk=false;

    public EnableOrDisableScreenCaptureEntity(Builder builder) {
        super(builder);
    }

    @Override
    public Object getData() {
        return "启用禁用截屏";
    }

    @Override
    public void clickOn() {
//        ScreenCapturePolicyUtils.getInstance().ScreenCaptureDisabled(mContext,true);
//        Window window=((Activity) MemoryUtils.getContext()).getWindow();
        ScreenShotListenUtils manager = ScreenShotListenUtils.newInstance(mContext);
        if(ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
            // 请求权限
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 10000);
        }
        if(ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            manager.setListener(
                    new ScreenShotListenUtils.OnScreenShotListener() {
                        public void onShot(String imagePath) {
                            //删除截屏
                            File file = new File(imagePath);
                            boolean status = file.delete();
                            if(status) {
                                Toast.makeText(mContext, "该设备已被禁止截屏", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );
            manager.startListen();
            isOk=true;
        }
    }

    @Override
    public void clickOff() {
        //        ScreenCapturePolicyUtils.getInstance().ScreenCaptureDisabled(mContext,false);
        if(isOk) {
            ScreenShotListenUtils manager = ScreenShotListenUtils.newInstance(mContext);
            manager.stopListen();
            isOk=false;
        }
    }
}
