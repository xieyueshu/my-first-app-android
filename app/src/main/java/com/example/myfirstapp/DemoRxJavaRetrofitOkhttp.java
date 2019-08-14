package com.example.myfirstapp;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class DemoRxJavaRetrofitOkhttp {
    private static String url = "https://api.github.com";

    private interface DemoApiClient {
        @GET("/rate_limit")
        Observable<Response<String>> getCall();
    }

    /**
     * 演示Rxjava+Retrofit+Okhttp三者配合使用
     */
    public static  void demoRxJavaRetrofitOkhttp() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS);
        OkHttpClient client = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url) //设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        DemoApiClient service = retrofit.create(DemoApiClient.class);
        service.getCall()
                .map(r -> {
                    System.out.println("Rxjava+Retrofit+Okhttp获取数据：" + r.body());
                    return r;
                })
                .subscribeOn(Schedulers.io());
    }
}
