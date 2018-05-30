package com.example.administrator.emmapplication.utils.retrofits;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class LoginBaseRetrofitUtils extends BaseRetrofitUtils {

    private ILogin iLogin;

    public LoginBaseRetrofitUtils() {
        super();
        iLogin=retrofit.create(ILogin.class);
    }

    public void login(String tenant,String account,String pwd,ApiCallback callback){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("tenant",tenant);
            jsonObject.put("account",account);
            jsonObject.put("password",pwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),jsonObject.toString());
        Call<LoginRetData> call = iLogin.login(body);
        call.enqueue(new RetrofitCallback<LoginRetData>(callback));
    }

    public interface ILogin{
        @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
        @POST("app/user/login")
        Call<LoginRetData> login(@Body RequestBody info);   // 请求体味RequestBody 类型
    }

    public class LoginRetData extends BaseRetData{
        public Object data;
    }
}
