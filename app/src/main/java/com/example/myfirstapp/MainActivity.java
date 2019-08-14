package com.example.myfirstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;

import javax.inject.Inject;


/**
 * 演示程序主入口，调用其他各个模块演示功能，
 * 模块输出一般使用System.out.println()方式。
 */
public class MainActivity extends AppCompatActivity {
    @Inject
    DemoDaggerModuleTest.TestEntity mBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //调用dagger演示功能
       // DaggerDemoDaggerComponent.create().intect(this);
        //System.out.println("Dagger 使用：" + mBus.testContent);

        //调用演示大整数功能
        //DemoBigNumber.demoBigInteger01();

        //调用RxJava演示响应式功能
        //DemoRxJava.demoRxJava01();
        //调用RxJava演示切换线程功能
       // DemoRxJava.demoRxJava01_1();
        //调用RxJava演示切换线程功能
        DemoRxJavaRetrofitOkhttp.demoRxJavaRetrofitOkhttp();

        try {
            //调用JCA/JCE 演示AES 加密 解密算法
            //DemoJCAJCE.demoAES();
            //DemoJCAJCE.demoAES2();
            //演示消息摘要MD5加密算法
            //DemoJCAJCE.demoMD();
            //演示MAC加密算法
            //DemoJCAJCE.demoMAC();
            //演示RES加密解密算法
            //DemoJCAJCE.demoRSA2();
            //演示数字签名RSA算法
            //DemoJCAJCE.demoSignRSA();
            //演示数字签名RSA算法
            //DemoJCAJCE.demoSignDSA();
            //演示数字签名ECDSA算法
            //DemoJCAJCE.demoSignECDSA();
            //演示密钥生成
            DemoJCAJCE.demoPBE();

            //演示Okhttp get
            // DemoOkhttp.demoOkhttpGet();
            //演示Okhttp post
            //DemoOkhttp.demoOkhttpPost();
            //演示Retrofit get
           // DemoRetrofit.demoRetrofitGet();
            //演示Retrofit+okhttp get
            // DemoRetrofit.demoRetrofitOkhttpGet();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
