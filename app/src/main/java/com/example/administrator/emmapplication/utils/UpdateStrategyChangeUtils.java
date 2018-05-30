package com.example.administrator.emmapplication.utils;

import android.content.Context;

import com.example.administrator.emmapplication.entity.LimitStrategyEntity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import static android.security.KeyStore.getApplicationContext;

public class UpdateStrategyChangeUtils {
    public UpdateStrategyChangeUtils getInstance(){
        return new UpdateStrategyChangeUtils();
    }
    public static String fileName="defaultLimitStrategy.json";

    private static JSONObject defaultValue(){
        JSONObject default_value = new JSONObject();
        JSONObject password = new JSONObject();
        JSONObject restriction = new JSONObject();
        JSONObject encrytion = new JSONObject();
        try {
            default_value.put("policy_id",0);
            default_value.put("policy_name","");
            default_value.put("policy_desc","");
            default_value.put("password",password);
            default_value.put("restriction",restriction);
            default_value.put("encrytion",encrytion);
            //密码策略默认值
            password.put("pwLength",4);
            password.put("pwComplexity",0);
            password.put("minChars",1);
            password.put("minLowerChars",0);
            password.put("minUpperChars",0);
            password.put("minNonalphabetic",0);
            password.put("minDigital",1);
            password.put("minSymbols",1);
            password.put("maxFailedAttempts",-1);
            password.put("maxPinageInDays",0);
            password.put("pinHistory",0);
            password.put("maxInactivity",0);
            password.put("configFlag",0);

            //其他策略
            restriction.put("allowCamera",1);
            restriction.put("allowSdCard",1);
            restriction.put("allowWifi",1);
            restriction.put("allowBluetooth",1);
            restriction.put("allowCellular",1);
            restriction.put("allowMobileHotspot",1);
            restriction.put("allow_gps",-1);
            restriction.put("allowDateTime",1);
            restriction.put("allowUnknownSources",1);
            restriction.put("allowSafeMode",1);
            restriction.put("allowADB",1);
            restriction.put("allowMTP",1);
            restriction.put("allowRecord",1);
            restriction.put("allowScreenshots",1);
            restriction.put("allowWipe",1);
            restriction.put("forbitAppList",null);
            restriction.put("whiteList",null);
            //加密
            encrytion.put("storageFlag",1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return default_value;
    }

    //存储限制策略改动状态的默认json
    public static void createChangeRecord(Context context){
        LimitStrategyEntity limitStrategy = (LimitStrategyEntity)
                GsonUtils.getJsonToEntity(context,0,
                        UpdateStrategyChangeUtils.fileName,LimitStrategyEntity.class);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(limitStrategy);
        write(context,fileName,jsonStr);
    }

    //将json串写入手机app沙盒中存储
    private static void write(Context context,String name,String content){
        Writer writer = null;
        try {
            OutputStream out = context.openFileOutput(name,
                    Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(content);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void saveNewLimitStrategy(Context context,JSONObject jsonObject){
        String json = jsonObject.toString();
        write(context,fileName,json);
    }

}
