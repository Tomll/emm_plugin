package com.example.administrator.emmapplication.utils.mobileInfos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.SystemClock;
import android.util.Size;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.TimeZone;

public class OtherInfos {
    private Context mContext;

    public OtherInfos(Context context){
        this.mContext=context;
    }
    /**
     * 获取开机时长
     *
     * @return 开机时长
     */
    public String bootTime(){
        long time1= System.currentTimeMillis() - SystemClock.elapsedRealtimeNanos() / 1000000;//开机时间
        long time2=SystemClock.elapsedRealtimeNanos() / 1000000;//开机时长
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        return formatter.format(time2);
    }
    /**
     * 获取ROOT状态
     *
     * @return 是否ROOT
     */
    public int isRoot() {
        String binPath = "/system/bin/su";
        String xBinPath = "/system/xbin/su";

        if (new File(binPath).exists() && isCanExecute(binPath)) {
            return 1;
        }
        if (new File(xBinPath).exists() && isCanExecute(xBinPath)) {
            return 1;
        }
        return 0;
    }
    private static boolean isCanExecute(String filePath) {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("ls -l " + filePath);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String str = in.readLine();
            if (str != null && str.length() >= 4) {
                char flag = str.charAt(3);
                if (flag == 's' || flag == 'x')
                    return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return false;
    }

    /**
     * 获取手机相素
     *
     * @return 手机相素
     */
    public String cameraPixels(){
        String pixels="";
        try {
            CameraManager manager = (CameraManager)mContext.getSystemService(Context.CAMERA_SERVICE);
            if(manager!=null) {
                for (String cameraId : manager.getCameraIdList()) {
                    CameraCharacteristics cameraCharacteristics = manager.getCameraCharacteristics(cameraId);
                    Integer facing = cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
                    if(facing == null){
                        return "--";
                    }
                    //获取StreamConfigurationMap，它是管理摄像头支持的所有输出格式和尺寸
                    StreamConfigurationMap map = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                    assert map != null;
                    //获取相机支持的最大拍照尺寸
                    Size mCaptureSize = Collections.max(Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)), new Comparator<Size>() {
                        @Override
                        public int compare(Size lhs, Size rhs) {
                            return Long.signum(lhs.getWidth() * lhs.getHeight() - rhs.getHeight() * rhs.getWidth());
                        }
                    });
                    if (facing == CameraCharacteristics.LENS_FACING_FRONT){
                        pixels=pixels.concat("    前置："+(mCaptureSize.getHeight()*mCaptureSize.getWidth())+"像素    ");
                    }else if(facing == CameraCharacteristics.LENS_FACING_BACK){
                        pixels=pixels.concat("    后置："+(mCaptureSize.getHeight()*mCaptureSize.getWidth())+"像素    ");
                    }
                }
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        return pixels;
    }

    public String device_plat(){
        return "--";
    }

    //平板 True，手机 False
    public  String device_type() {
        if((mContext.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE ){
            return "Android平板";
        }
        return "Android手机";
    }
}
