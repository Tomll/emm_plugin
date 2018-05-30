package com.example.administrator.emmapplication.utils.retrofits;

import com.example.administrator.emmapplication.utils.mobileInfos.DeviceInfos;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class CmdStateBackBaseRetrofitUtils extends BaseRetrofitUtils {
    //设备擦出状态返回，成功返回设备序列号,返回类型为4
    //设备淘汰状态返回，成功返回设备序列号,返回类型为5

    private ICommandState stateBack;

    public CmdStateBackBaseRetrofitUtils() {
        super();
        stateBack=retrofit.create(ICommandState.class);
    }

    public void stateBack(int stateType){
        JSONObject json = new JSONObject();
        String device_id= DeviceInfos.getSerialNumber();
        System.out.println("device_id="+device_id);
        try {
            json.put("state_type",stateType);
            json.put("device_id",device_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),json.toString());
        Call<ResponseBody> call=stateBack.back(body);
    }

    public interface ICommandState{
        @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
        @POST("app/device/result")
        Call<ResponseBody> back(@Body RequestBody info);
    }
}
