package com.example.myfirstapp;

import android.util.Base64;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DemoOkhttp {
    private static OkHttpClient client = new OkHttpClient();
    private static String url = "https://api.github.com/rate_limit";

    /**
     * Okhttp get方法演示
     * @throws IOException
     */
    public static void demoOkhttpGet() throws IOException {
        Request request = new Request.Builder()
                .url(url).get()
                .build();

        //将Request封装成call
        Call call = client.newCall(request);
        //执行call，这个方法是异步请求数据
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call arg0, IOException arg1) {
                //失败调用
            }

            @Override
            //由于OkHttp在解析response的时候依靠的是response头信息当中的Content-Type字段来判断解码方式
            //OkHttp会使用默认的UTF-8编码方式来解码
            //这里使用的是异步加载，如果需要使用控件，则在主线程中调用
            public void onResponse(Call arg0, Response arg1) throws IOException {
                //成功调用
                System.out.println("Okhttp get方法Demo获取数据：" + arg1.body().string());
            }
        });

    }
    /**
     * Okhttp post方法演示
     */
    public static  void demoOkhttpPost() {
        RequestBody requestBodyPost = new FormBody.Builder()

                .build();
        Request requestPost = new Request.Builder()
                .url(url)
                .post(requestBodyPost)
                .build();
        client.newCall(requestPost).enqueue(new Callback() {
            @Override
            public void onFailure(Call arg0, IOException arg1) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onResponse(Call arg0, Response arg1) throws IOException {
                //okHttp还支持GJson的处理方式
                //在这里可以进行List<bean>和bean处理
                System.out.println("Okhttp post方法执行结果：" + arg1.body().string());
            }
        });
    }
}
