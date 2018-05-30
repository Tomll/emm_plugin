package com.example.administrator.emmapplication.utils.retrofits;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class LocationBaseRetrofitUtils extends BaseRetrofitUtils {
    private ILocation iLocation;

    public LocationBaseRetrofitUtils() {
        super();
        iLocation=retrofit.create(ILocation.class);
    }

    public void loc(String loc_info){
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),loc_info);
        Call<ResponseBody> call = iLocation.loc(body);
    }

    public interface ILocation{
        @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
        @POST("app/device/location")
        Call<ResponseBody> loc(@Body RequestBody info);
    }
}
