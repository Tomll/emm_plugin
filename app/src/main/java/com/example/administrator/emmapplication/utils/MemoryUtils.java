package com.example.administrator.emmapplication.utils;

import android.annotation.SuppressLint;
import android.content.Context;

public class MemoryUtils {

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        MemoryUtils.context = context;
    }

    @SuppressLint("StaticFieldLeak")
    private static  Context context;

}
