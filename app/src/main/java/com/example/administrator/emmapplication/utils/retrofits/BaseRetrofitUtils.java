package com.example.administrator.emmapplication.utils.retrofits;


import android.util.Log;

import com.example.administrator.emmapplication.utils.SharedfPreferencesUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Route;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.security.KeyStore.getApplicationContext;

import static android.app.ActivityThread.TAG;

public class BaseRetrofitUtils {

    protected Retrofit retrofit;
    private final String BASE_URL="http://dev-mobile-uem.ssgm.net";
//    private final String BASE_URL="http://172.16.4.158:8085/";
//https://blog.csdn.net/u013653230/article/details/52518713

     public BaseRetrofitUtils(){
         //拦截器
         Interceptor interceptor = new Interceptor() {
             @Override
             public okhttp3.Response intercept(Chain chain) throws IOException {
                 Request originalRequest = chain.request();
                 String token= SharedfPreferencesUtils.getInstance(getApplicationContext()).getToken();
                 Request authorised = originalRequest.newBuilder()
                         .header("Authorization", token)
                         .addHeader("Connection","close")
                         .build();
                 return chain.proceed(authorised);
             }
         };
         //创建一个OkHttpClient
         OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                 .connectTimeout(60, TimeUnit.SECONDS)  //设置超时时间
                 .readTimeout(60, TimeUnit.SECONDS)     //设置读取超时时间
                 .writeTimeout(60, TimeUnit.SECONDS);   //设置写入超时时间
         //错误重连
         builder.retryOnConnectionFailure(true);

         int cacheSize = 10 * 1024 * 1024; // 设置缓存大小10 MiB
         Cache cache = new Cache(getApplicationContext().getCacheDir(), cacheSize);
         builder.cache(cache);
         //builder.addInterceptor(interceptor);//添加一个拦截器
         OkHttpClient mOkHttpClient = builder.build();
         retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                 .client(mOkHttpClient)
                .build();
    }


//    public <T> T create(Class<T> services){
//        return retrofit.create(services);
//    }

    //处理retrofit回调 并调用ApiCallback相应返回
    protected class RetrofitCallback<T> implements Callback<T> {

        private ApiCallback<T> mCallback;

        public RetrofitCallback(ApiCallback<T> callback) {
            mCallback = callback;
        }

        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            if(response.isSuccessful()) {
                //请求成功
                if(((BaseRetData)response.body()).status == 0) {
                    mCallback.onSuccess(((T)response.body()));
                } else {
                    mCallback.onError(((T)response.body()));
                }
            } else {
                mCallback.onFailure();
            }
        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            Log.e(TAG, "api failure,throw=" + t.getMessage());
            t.printStackTrace();
            mCallback.onFailure();
        }
    }

    //api调用回调
    public interface ApiCallback<T> {
        void onSuccess(T ret);        //ret=1时返回
        void onError(T ret);   //ret=0时返回
        void onFailure();   //网络请求失败
    }


}
