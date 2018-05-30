package com.example.administrator.emmapplication.utils.accessibility;

import android.os.Bundle;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import static android.content.ContentValues.TAG;

public class WechatQQKeywordFiltering {
    public static WechatQQKeywordFiltering getInstance(){
        return Holder.INSTANCE;
    }
    private static class Holder{
        private static final WechatQQKeywordFiltering INSTANCE = new WechatQQKeywordFiltering();
    }


    private void keywordFilter(AccessibilityNodeInfo info, String text){
        String newText="";
        Bundle arguments = new Bundle();
        String [] keys={"军队","部队","jundui","budui"};
        for(String key:keys){
            if(text.contains(key)){
                newText=text.replace(key,"&%$@#*");
                arguments.putCharSequence(
                        AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, newText);
                info.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
            }
        }
    }

    public void start(AccessibilityNodeInfo info,boolean isStart){
        try {
            if(isStart) {
                if (info.getChildCount() == 0) {
                    Log.i(TAG, "child widget2------------------------------------------------" + info.getClassName());
                    Log.i(TAG, "showDialog:" + info.canOpenPopup());
                    Log.i(TAG, "Text：" + info.getText());
                    Log.i(TAG, "windowId:" + info.getWindowId());
                    Log.i(TAG, "child widget2------------------------------------------------");
                    if (info.getText() != null) {
                        String tag = info.getText().toString();
                        keywordFilter(info,tag);
                    }
                } else {
                    for (int i = 0; i < info.getChildCount(); i++) {
                        if (info.getChild(i) != null) {
                            start(info.getChild(i), true);
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
