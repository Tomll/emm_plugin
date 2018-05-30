package com.example.administrator.emmapplication.utils.retrofits;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public class LoginOutBaseRetrofitUtils extends BaseRetrofitUtils {
    private ILoginOut iLoginOut;

    public LoginOutBaseRetrofitUtils() {
        super();
        iLoginOut=retrofit.create(ILoginOut.class);
    }

    public void loginOut(String token,ApiCallback callback){
        String lang="Bearer "+token;
        Call<BaseRetData> call = iLoginOut.out(lang);
        call.enqueue(new RetrofitCallback<BaseRetData>(callback));
    }

    public interface ILoginOut{
        @GET("app/user/logout")
        Call<BaseRetData> out(@Header("Authorization") String lang);   // 请求体味RequestBody 类型
    }
}
