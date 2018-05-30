package com.example.administrator.emmapplication.utils.retrofits;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class ActivateBaseRetrofitUtils extends BaseRetrofitUtils {
    private IActivate iActivate;

    public ActivateBaseRetrofitUtils() {
        super();
        iActivate=retrofit.create(IActivate.class);
    }

    public void activate(String json,ApiCallback callback){
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),json);
        Call<ActivateRetData> call = iActivate.getMessage(body);
        call.enqueue(new RetrofitCallback<ActivateRetData>(callback));
    }

    public interface IActivate{
        @Headers({"Content-Type:application/json","Accept:application/json"})//需要添加头
        @POST("app/user/activate")
        Call<ActivateRetData> getMessage(@Body RequestBody info);   // 请求体味RequestBody 类型
    }

    public class ActivateRetData extends BaseRetData{
        public Object data;
    }
}
