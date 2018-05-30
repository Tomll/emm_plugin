package com.example.administrator.emmapplication.utils;

import android.content.Context;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GsonUtils {
    /**
     *
     * @param type 读取的文件根目录；0则默认从app的assets目录中读取，非0则从手机沙盒中读取
     * @param path 文件名
     * @param entity 关联实体类
     * @return EntityClass
     */
    public static Object getJsonToEntity(Context context,int type, String path, Class<?> entity){
        InputStream json_stream=null;
        BufferedReader json_reader=null;
        try {
            Gson gson = new Gson();
            if(0==type) {
                json_stream = context.getAssets().open(path);
            }else{
                json_stream = context.openFileInput(path);
            }
            json_reader = new BufferedReader(new InputStreamReader(json_stream));

            return gson.fromJson
                    (json_reader,entity);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(json_stream!=null) { json_stream.close(); }
                if(json_reader!=null) { json_reader.close(); }
            } catch (IOException e) { e.printStackTrace(); }
        }
        return null;
    }

    public static String getEntityToJsonStr(Object entity){
        try {
            Gson gson = new Gson();
            return gson.toJson(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
