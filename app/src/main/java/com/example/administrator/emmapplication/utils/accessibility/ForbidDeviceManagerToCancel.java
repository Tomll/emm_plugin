package com.example.administrator.emmapplication.utils.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.example.administrator.emmapplication.service.BaseAccessibilityService;

import java.util.List;

public class ForbidDeviceManagerToCancel {
    public static ForbidDeviceManagerToCancel getInstance(){
        return Holder.INSTANCE;
    }
    private static class Holder{
        private static final ForbidDeviceManagerToCancel INSTANCE = new ForbidDeviceManagerToCancel();
    }

    public void forbidToCancel(AccessibilityEvent event, BaseAccessibilityService mAccessibilityService){
        List<CharSequence> textA = event.getText();
        System.out.println("Forbid TEXTA："+textA);
        if(textA.size()>0 && "SGM设备".equals(textA.get(0).toString())){
            Log.i("Text","已点击SGM设备管理");
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mAccessibilityService.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
        }
    }
}
