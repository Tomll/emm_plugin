package com.example.administrator.emmapplication.utils.retrofits;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class UploadPictureRetrofitUtils extends BaseRetrofitUtils{
    private IPictureUpload iPictureUpload;

    public UploadPictureRetrofitUtils() {
        super();
        iPictureUpload=retrofit.create(IPictureUpload.class);
    }

    public void upload(int type,String picture){
        JSONObject json = new JSONObject();
        try {
            json.put("type",type);
            json.put("picture",picture);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),json.toString());
        Call<ResponseBody> call = iPictureUpload.upload(body);
    }

    public interface IPictureUpload{
        @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
        @POST("app/device/picture")
        Call<ResponseBody> upload(@Body RequestBody info);
    }
}
