package com.example.administrator.emmapplication.utils.accessibility;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.example.administrator.emmapplication.service.BaseAccessibilityService;

import java.util.List;

import static android.content.ContentValues.TAG;

public class WechatAuditLog {
    private String getWeChat;
    public static WechatAuditLog getInstance(){
        return Holder.INSTANCE;
    }
    private static class Holder{
        private static final WechatAuditLog INSTANCE = new WechatAuditLog();
    }

    public void startAuditLog(AccessibilityEvent event, BaseAccessibilityService mAccessibilityService, boolean isStart) {
        String packageName = event.getPackageName().toString();
        if(isStart&&packageName.contains("com.tencent.mm")) {
            AccessibilityNodeInfo nodeInfo = mAccessibilityService.getRootInActiveWindow();
            if (nodeInfo != null) {
                List<AccessibilityNodeInfo> list = nodeInfo
                        .findAccessibilityNodeInfosByText("发送");
                if(list.size()==0){
                    Log.i("WeChatSendTalk","发送了对话："+getWeChat);
                }
            }
        }
    }

    public void startWechat(AccessibilityNodeInfo info,boolean isStart){
        try {
            if(isStart) {
                if (info.getChildCount() == 0) {
                    Log.i(TAG, "child widget2------------------------------------------------" + info.getClassName());
                    Log.i(TAG, "showDialog:" + info.canOpenPopup());
                    Log.i(TAG, "Text：" + info.getText());
                    Log.i(TAG, "windowId:" + info.getWindowId());
                    Log.i(TAG, "child widget2------------------------------------------------");
                    if (info.getText() != null) {
                        getWeChat = info.getText().toString();
                    }
                } else {
                    for (int i = 0; i < info.getChildCount(); i++) {
                        if (info.getChild(i) != null) {
                            startWechat(info.getChild(i), true);
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG,e.getMessage());
        }
    }
}
