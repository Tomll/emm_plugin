package com.example.administrator.emmapplication.entity;

import android.app.Activity;

import com.example.administrator.emmapplication.activity.MainActivity;
import com.example.administrator.emmapplication.entity.abstractEntity.RecycleViewImpl;
import android.content.Intent;
import com.example.administrator.emmapplication.activity.MenuActivity;
import com.example.administrator.emmapplication.utils.BitmapUtils;
import com.example.administrator.emmapplication.utils.ConstantUtils;
import com.example.administrator.emmapplication.utils.ScreenCaptureUtils;
import com.example.administrator.emmapplication.utils.ScreenUtils;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by admine on 2018/5/11.
 */

public class ScreenCaptureEntity extends RecycleViewImpl {
    private static ExecutorService singleExecutor = Executors.newSingleThreadExecutor();

    public ScreenCaptureEntity(Builder builder) {
        super(builder);
    }

    @Override
    public Object getData() {
        return "截屏";
    }

    //需要传入Activity，并且在onActivityResult()方法中进行复写
    @Override
    public void clickOn() {
        Activity activity = (MainActivity)mContext;
        ScreenCaptureUtils.startScreenCap(activity,ScreenUtils.getScreenWidth(activity),ScreenUtils.getScreenHeight(activity));
    }

    @Override
    public void clickOff() {

    }

    public static void capOnActivityResult(final Activity context, int requestCode, int resultCode, final Intent data, final int screenWidth, final int screenHeight, final int screenDensity) {
        if (requestCode == ConstantUtils.SCREEN_CAPTURE && resultCode == Activity.RESULT_OK && data != null) {
            singleExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    ScreenCaptureUtils.setUpMediaProjection(context, data, screenWidth, screenHeight, screenDensity);
                    ScreenCaptureUtils.virtualDisplay(screenWidth, screenHeight, screenDensity);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    BitmapUtils.saveImageToGallery(context, ScreenCaptureUtils.createPicture(screenWidth, screenHeight));
                    ScreenCaptureUtils.stopVirtual();
                }
            });
        }
    }
}
