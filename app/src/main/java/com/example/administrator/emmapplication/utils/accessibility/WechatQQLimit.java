package com.example.administrator.emmapplication.utils.accessibility;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.example.administrator.emmapplication.service.BaseAccessibilityService;

import java.util.List;

import static android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_BACK;
import static android.content.ContentValues.TAG;

public class WechatQQLimit {
    public static WechatQQLimit getInstance(){
        return Holder.INSTANCE;
    }
    private static class Holder{
        private static final WechatQQLimit INSTANCE = new WechatQQLimit();
    }

    public void action(AccessibilityEvent event, BaseAccessibilityService mAccessibilityService, boolean isStartLimit){
        if(isStartLimit){
            startLimit(event,mAccessibilityService);
        }
    }

    private void startLimit(AccessibilityEvent event, BaseAccessibilityService mAccessibilityService){
        try {
            List<CharSequence> textA = event.getText();
            System.out.println("Limit TEXTA："+textA);
            String text = textA.get(0).toString();
            if("漂流瓶".equals(text)|| "摇一摇".equals(text)||"附近的人".equals(text)){
                mAccessibilityService.performBackClick();
            }
            if("附近".equals(text)){
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    //TODO 模拟点击返回或动态按钮返回
                    e.printStackTrace();
                }
                mAccessibilityService.performGlobalAction(GLOBAL_ACTION_BACK);
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG,e.getMessage());
        }
    }
}
