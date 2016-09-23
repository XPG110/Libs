package com.example.nanfu.libs.network;

import android.content.Context;
import android.util.Log;

import com.example.nanfu.libs.MyApplication;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/9/8.
 */
public class ApiConfig {

    private static String TAG=ApiConfig.class.getSimpleName();

    private static final String baseUrl="https://api.douban.com/v2/movie/";

    public static boolean ISTEST=true;

    /**
     * 超时时间
     */
    private static final int DEFALT_TIMEOUT=5;


    private Retrofit retrofit;

    private static Map<String,Retrofit> retrofitMap=new HashMap<>();


    private ApiConfig(){
        initData();
    }

    public static ApiConfig getInstance(){
        return SingletonHolder.INSTANCE;
    }
    private static class SingletonHolder {
        private static final ApiConfig INSTANCE = new ApiConfig();
    }

    public Retrofit getRetrofit(){
        return retrofit;
    }
    private void initData(){
        OkHttpClient.Builder okHttpClientBuilder=new OkHttpClient.Builder();
        //设置缓存路径
        File httpCacheDirectory = new File(MyApplication.getContext().getCacheDir(), "responses");
        //设置缓存 10M
        Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);

        OkHttpClient client=okHttpClientBuilder
                .addInterceptor(getCacheInterceptor())
                .cache(cache)
                .connectTimeout(DEFALT_TIMEOUT, TimeUnit.SECONDS).build();

        retrofit=new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    public static Retrofit getRetrofit(String host,boolean haveCache){
        if(retrofitMap.containsKey(host)){
            return retrofitMap.get(host);
        }else {
            OkHttpClient client=null;
            if(haveCache){
                //设置缓存路径
                File httpCacheDirectory = new File(MyApplication.getContext().getCacheDir(), "responses");
                //设置缓存 10M
                Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
                //手动创建一个OkHttpClient并设置超时时间
                client = new OkHttpClient.Builder()
                        .addInterceptor(getCacheInterceptor())
                        .cache(cache)
                        .build();
            }else{
                //手动创建一个OkHttpClient并设置超时时间
                client = new OkHttpClient.Builder()
                        .addInterceptor(getCacheInterceptor())
                        .build();
            }


            Retrofit retrofit=new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .baseUrl(host)
                    .build();
            retrofitMap.put(host,retrofit);

            return retrofit;
        }
    }

    public static Retrofit getRetrofit(String host) {
        if (retrofitMap.containsKey(host)) {
            return retrofitMap.get(host);
        } else {
            //设置缓存路径
            File httpCacheDirectory = new File(MyApplication.getContext().getCacheDir(), "responses");
            //设置缓存 10M
            Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
            //手动创建一个OkHttpClient并设置超时时间
            OkHttpClient  client = new OkHttpClient.Builder()
                    .addInterceptor(getCacheInterceptor())
                    .cache(cache)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(host)
                    .build();
            retrofitMap.put(host, retrofit);
            return retrofit;
        }
    }

    public static Interceptor getCacheInterceptor(){
        // 实现离线缓存
        // （离线可以缓存，在线就获取最新数据）
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!ApiConfig.isNetworkAvailable(MyApplication.getContext())) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                    Log.i(TAG, "no network");
                }

                Response response = chain.proceed(request);

                if (ApiConfig.isNetworkAvailable(MyApplication.getContext())) {
                    int maxAge = 0 * 60; // 有网络时 设置缓存超时时间0个小时
                    Log.i(TAG, "has network maxAge="+maxAge);
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .build();
                } else {
                    Log.i(TAG, "network error");
                    int maxStale = 60 * 60 * 24 * 28; // 无网络时，设置超时为4周
                    Log.i(TAG, "has maxStale="+maxStale);
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                    Log.i(TAG, "response build maxStale="+maxStale);
                }
                return response;
            }
        };
    }

    public static boolean isNetworkAvailable(Context context){
        return true;
    }
}
