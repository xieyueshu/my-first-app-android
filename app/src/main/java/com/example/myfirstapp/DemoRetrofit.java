package com.example.myfirstapp;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class DemoRetrofit {
    private static String url = "https://api.github.com";

    public interface Demo_Interface {
        // @GET注解的作用:采用Get方法发送网络请求
        // getCall() = 接收网络请求数据的方法
        // 其中返回类型为Call<*>，*是接收数据的类（即上面定义的Translation类）
        // 如果想直接获得Responsebody中的内容，可以定义网络请求返回值为Call<ResponseBody>
        @GET("/rate_limit")
        Call<ResponseBody> getCall();
    }

    /**
     * Retrofit演示Demo
     */
    public static void demoRetrofitGet() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url) //设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                .build();
        Demo_Interface service = retrofit.create(Demo_Interface.class);
        // @FormUrlEncoded
        Call<ResponseBody> call1 = service.getCall();
        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    System.out.println("Retrofit get方法执行结果：" + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * Retrofit+okhttp演示Demo
     */
    public static void demoRetrofitOkhttpGet() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS);
        OkHttpClient client = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url) //设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                .client(client)
                .build();
        Demo_Interface service = retrofit.create(Demo_Interface.class);
        // @FormUrlEncoded
        Call<ResponseBody> call1 = service.getCall();
        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    System.out.println("Retrofit+Okhttp get方法执行结果：" + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
