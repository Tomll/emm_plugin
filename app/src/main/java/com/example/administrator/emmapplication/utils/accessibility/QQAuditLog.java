package com.example.administrator.emmapplication.utils.accessibility;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import static android.content.ContentValues.TAG;

public class QQAuditLog {
    public static QQAuditLog getInstance(){
        return Holder.INSTANCE;
    }
    private static class Holder{
        private static final QQAuditLog INSTANCE = new QQAuditLog();
    }

    public void startAuditLog(AccessibilityEvent event,boolean isStart){
        String packageName = event.getPackageName().toString();
        if(isStart&&packageName.contains("com.tencent.mobileqq")) {
            CharSequence c = event.getClassName();
            if ("android.widget.RelativeLayout".equals(c == null ? "" : c.toString())) {
                AccessibilityNodeInfo info = event.getSource();
                if (info != null) {
                    getTalkInfo(info);
                }
            }
        }
    }


    private void getTalkInfo(AccessibilityNodeInfo info){
        try {
            if (info.getChildCount() == 0) {
                if(info.getText()!=null) {
                    Log.i("QQTalkLog","QQ聊天内容"+info.getText());
                }
            } else {
                for (int i = 0; i < info.getChildCount(); i++) {
                    if (info.getChild(i) != null) {
                        getTalkInfo(info.getChild(i));
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG,e.getMessage());
        }
    }
}
