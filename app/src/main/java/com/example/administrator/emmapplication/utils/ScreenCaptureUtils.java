package com.example.administrator.emmapplication.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by admine on 2018/5/11.
 */

public class ScreenCaptureUtils {
    private static MediaProjectionManager mediaProjectionManager;
    private static MediaProjection mediaProjection;
    private static ImageReader imageReader;

    private static VirtualDisplay mVirtualDisplay = null;

    public static void startScreenCap(Activity context,int screenWidth,int screenHeight) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        if (mediaProjectionManager == null)
            mediaProjectionManager = (MediaProjectionManager) context.getSystemService(Context.MEDIA_PROJECTION_SERVICE);

        if(imageReader==null)
            imageReader = ImageReader.newInstance(screenWidth, screenHeight, PixelFormat.RGBA_8888, 1);

        context.startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), ConstantUtils.SCREEN_CAPTURE);
    }


    //获取MediaProjection实例
    public static void setUpMediaProjection(Activity context, Intent data, int screenWidth, int screenHeight,int screenDensity) {
        if(mediaProjection == null)
            mediaProjection = mediaProjectionManager.getMediaProjection(Activity.RESULT_OK, data);
    }

    //获取当前屏幕的内容,mImageReader.getSurface()被传入，屏幕的数据也将会在ImageReader中的Surface中
    public static void virtualDisplay(int screenWidth, int screenHeight, int mScreenDensity) {
        try {
            mVirtualDisplay = mediaProjection.createVirtualDisplay("screen-mirror", screenWidth, screenHeight, mScreenDensity,
                    DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                    imageReader.getSurface(), null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //生成图像
    public static Bitmap createPicture(int screenWidth,int screenHeight) {
        final Image image = imageReader.acquireLatestImage();
        int width = image.getWidth();
        int height = image.getHeight();
        final Image.Plane[] planes = image.getPlanes();
        final ByteBuffer buffer = planes[0].getBuffer();
        int pixelStride = planes[0].getPixelStride();
        int rowStride = planes[0].getRowStride();
        int rowPadding = rowStride - pixelStride * width;
        Bitmap bitmap = Bitmap.createBitmap(width + rowPadding / pixelStride, height, Bitmap.Config.ARGB_8888);
        bitmap.copyPixelsFromBuffer(buffer);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height);
        image.close();
        return bitmap;

    }

    public static void stopVirtual() {
        if (mVirtualDisplay == null) {
            return;
        }
        mVirtualDisplay.release();
        mVirtualDisplay = null;
    }

}
