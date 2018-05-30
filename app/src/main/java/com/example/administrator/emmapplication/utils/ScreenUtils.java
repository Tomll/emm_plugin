package com.example.administrator.emmapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by admine on 2018/5/11.
 */

public class ScreenUtils {
    public static int getScreenWidth(Activity context){
        return context.getWindowManager().getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight(Activity context){
        return context.getWindowManager().getDefaultDisplay().getHeight();
    }

    public static float getScreenDensity(Activity context){
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.density;
    }

    public static int getScreenDensityDpi(Activity context){
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.densityDpi;
    }
}
