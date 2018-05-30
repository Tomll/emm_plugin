package com.example.administrator.emmapplication.service;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.example.administrator.emmapplication.utils.accessibility.ForbidDeviceManagerToCancel;
import com.example.administrator.emmapplication.utils.accessibility.QQAuditLog;
import com.example.administrator.emmapplication.utils.accessibility.WechatAuditLog;
import com.example.administrator.emmapplication.utils.accessibility.WechatQQKeywordFiltering;
import com.example.administrator.emmapplication.utils.accessibility.WechatQQLimit;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by ssgm_d501_tzp on 2018/5/18.
 */

public class MyAccessibilityService extends BaseAccessibilityService {
    private String eventText;
    private boolean wechatKeywordFilter=false;
    private boolean wechatQQAuditLog=false;
    private boolean wechatQQLimit=false;
    @Override
    public void onInterrupt() {
        //当服务要被中断时调用.会被调用多次
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent!=null){
            if("wechat.keyword.filtering.true".equals(intent.getAction())){
                wechatKeywordFilter=true;
            }else if("wechat.keyword.filtering.false".equals(intent.getAction())){
                wechatKeywordFilter=false;
            }

            if("wechat.qq.auditLog.true".equals(intent.getAction())){
                wechatQQAuditLog=true;
            }else if("wechat.qq.auditLog.false".equals(intent.getAction())){
                wechatQQAuditLog=false;
            }

            if("wechat.qq.limit.true".equals(intent.getAction())){
                wechatQQLimit=true;
            }else if("wechat.qq.limit.false".equals(intent.getAction())){
                wechatQQLimit=false;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onServiceConnected() {
        AccessibilityServiceInfo info = getServiceInfo();
        //这里可以设置多个包名，监听多个应用
        info.packageNames = new String[]{"com.tencent.mm", "com.tencent.mobileqq",
                "com.example.administrator.myapplication","com.android.settings",
                "com.oppo.launcher"};
        setServiceInfo(info);
        super.onServiceConnected();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        List<CharSequence> texts = event.getText();
        if(event.getSource()!=null&&event.getSource().getChildCount()>0&&event.getSource().getChild(0)!=null) {
            CharSequence t = event.getSource().getChild(0).getText();
            if(t!=null) {
                eventText=t.toString();
                Log.i("Text", t + " <--"+event.getEventType());
            }
        }
        switch (eventType) {
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                //禁止取消设备管理器
                ForbidDeviceManagerToCancel.getInstance().forbidToCancel(event,this);
                WechatQQLimit.getInstance().action(event,this,wechatQQLimit);
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
                Log.i(TAG,"event type：TYPE_VIEW_TEXT_SELECTION_CHANGED");
                WechatAuditLog.getInstance().startAuditLog(event,this,wechatQQAuditLog);
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                //界面文字改动
                Log.i(TAG,"界面内容改动");
                starct(event);
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED://窗体状态改变
                app( texts);
                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                QQAuditLog.getInstance().startAuditLog(event,wechatQQAuditLog);
                break;
        }
    }


    private void app(List<CharSequence> texts) {
        try {
            Log.i(TAG,"获取文本集合："+texts);
            String tag = eventText;
            if (null!=tag&&tag.contains("SGM")) {
                if (tag.contains("卸载") || tag.contains("删除")) {
                    Log.i(TAG, "监听到正在准备删除开发APP");
                    clickByText("取消");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("AccessibilityError",e.getMessage());
        }
    }

    private void starct(AccessibilityEvent event) {
        try {
            AccessibilityNodeInfo rowNode = event.getSource();
            if (rowNode != null) {
                WechatAuditLog.getInstance().startWechat(rowNode,wechatQQAuditLog);
                WechatQQKeywordFiltering.getInstance().start(rowNode,wechatKeywordFilter);
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("AccessWriteErr",e.getMessage());
        }
    }
}
