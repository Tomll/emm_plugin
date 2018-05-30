package com.example.administrator.emmapplication.entity;

import com.example.administrator.emmapplication.entity.abstractEntity.RecycleViewImpl;
import com.example.administrator.emmapplication.utils.LogUtils;
import com.example.administrator.emmapplication.utils.RecorderUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by admine on 2018/4/19.
 */

public class RecorderEntity extends RecycleViewImpl {

    private static ExecutorService singleExecutor = Executors.newSingleThreadExecutor();

    public RecorderEntity(Builder builder) {
        super(builder);
    }

    @Override
    public Object getData() {
        return "开启录音";
    }

    @Override
    public void clickOn() {
        String filePath = mContext.getExternalFilesDir("/recorder")+"";
        LogUtils.d("filePath : "+filePath);
        RecorderUtils.startRecording(filePath);
    }

    @Override
    public void clickOff() {
        RecorderUtils.stopRecording();
        RecorderUtils.play(mContext,mContext.getExternalFilesDir("/recorder")+"");
    }
}
