package com.example.administrator.emmapplication.entity;

import android.os.Environment;

import com.example.administrator.emmapplication.entity.abstractEntity.RecycleViewImpl;
import com.example.administrator.emmapplication.utils.FileUtils;
import com.example.administrator.emmapplication.utils.LogUtils;
import com.example.administrator.emmapplication.utils.SDCardUtils;

import java.io.File;
import java.io.IOException;

public class SDCardEntity extends RecycleViewImpl {

    public SDCardEntity(Builder builder){
        super(builder);
    }

    @Override
    public Object getData() {
        return "SD 卡";
    }

    @Override
    public void clickOn() {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                try {
//                    LogUtils.d("向内置存储写入文件context.getFilesDir()...................."+ FileUtils.writeTxtFile("test",new File(SDCardUtils.getInnerSDCardPath(mContext,"myinfos"))));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                try {
//                    LogUtils.d("向外置存储本应用目录写入文件context.getExternalFilesDir（）...................."+ FileUtils.writeTxtFile("test",new File(SDCardUtils.getOuterSDCardFilePath(mContext,"myinfos"))));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    LogUtils.d("向外置存储非本工程目录写入文件Environment.getExternalStorageState()...................."+ FileUtils.writeTxtFile("test",new File(SDCardUtils.getOterSDCardStoragePath(mContext,"myinfos"))));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                SDCardUtils.setUSBMassstorage(mContext,true);
                SDCardUtils.mountSDCard(mContext,true, Environment.getExternalStorageDirectory().getPath());
                try {
                    LogUtils.d("向外置存储非本工程目录写入文件Environment.getExternalStorageState()...................."+ FileUtils.writeTxtFile("test",new File(SDCardUtils.getOterSDCardStoragePath(mContext,"myinfos"))));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    public void clickOff() {
//        SDCardUtils.setUSBMassstorage(mContext,false);
        SDCardUtils.mountSDCard(mContext,false, Environment.getExternalStorageDirectory().getPath());
        try {
              LogUtils.d("关闭SD卡后向外置存储非本工程目录写入文件Environment.getExternalStorageState()...................."+ FileUtils.writeTxtFile("test",new File(SDCardUtils.getOterSDCardStoragePath(mContext,"myinfos"))));
         } catch (IOException e) {
              e.printStackTrace();
         }
    }
}
