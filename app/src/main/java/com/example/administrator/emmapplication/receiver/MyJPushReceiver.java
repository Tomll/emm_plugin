package com.example.administrator.emmapplication.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.emmapplication.activity.Fragement.FragmentNotes;
import com.example.administrator.emmapplication.activity.MenuActivity;
import com.example.administrator.emmapplication.execute.Command;
import com.example.administrator.emmapplication.execute.ExecuteOption;
import com.example.administrator.emmapplication.execute.Strategy;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

import static android.support.constraint.Constraints.TAG;

public class MyJPushReceiver extends BroadcastReceiver{
    private void select(String message){
        try {
            JSONObject returnData = new JSONObject(message);
            int type = returnData.getInt("type");
            JSONObject data=returnData.getJSONObject("data");
            ExecuteOption execute = null;
            if(1==type){
                execute = new Command(data);
            }else if(2==type){
                execute = new Strategy(data);
            }
            if(execute!=null) {
                execute.action();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "onReceive - " + intent.getAction());
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            System.out.println("Registration Id"+regId);
        }else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "收到了自定义消息。消息内容是：" + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            System.out.println("收到了消息："+bundle.getString(JPushInterface.EXTRA_MESSAGE));
            String msg = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            select(msg);
            // 自定义消息不会展示在通知栏，完全要开发者写代码去处理
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "收到了通知");
            // 在这里可以做些统计，或者做些其他工作
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "用户点击打开了通知");
            // 在这里可以自己写代码去定义用户点击后的行为
            Intent i = new Intent(context, MenuActivity.class);  //自定义打开的界面
            i.putExtras(bundle);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
            context.startActivity(i);
        }else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.e(TAG, "[MyReceiver]" + intent.getAction() +" connected:"+connected);
        } else {
            Log.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }
}
