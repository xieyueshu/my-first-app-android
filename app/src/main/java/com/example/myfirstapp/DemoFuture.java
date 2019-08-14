package com.example.myfirstapp;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.security.auth.callback.Callback;

public class DemoFuture {
    /**
     * Future演示demo
     */
    public static void demoFutureCallable() {
        Callable<String> TestCallable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(2000);
                return "Hello world";
            }
        };
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(TestCallable);

        try {

            System.out.println("执行结果isDone:" + future.isDone());
            System.out.println("执行结果isCancelled:" + future.isCancelled());
            System.out.println("执行结果get:" + future.get());
            System.out.println("去做其他事");
        } catch (Exception e) {

        } finally {
            executorService.shutdown();
        }
    }

    /**
     * CompletableFuture 演示demo
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void demoCompletableFuture() throws ExecutionException, InterruptedException {
        //创建一个异步操作
        CompletableFuture<String> future = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                try {
                    System.out.println("开始执行");
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "Hello world";
            }
        });
        //执行完成的回调
        future.whenComplete(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(String s, Throwable throwable) {
                System.out.println("执行完成结果:" + s);
            }
        });
        System.out.println("去做其他事");
        //执行失败的回调
        future.exceptionally(new Function<Throwable, String>() {
            @Override
            public String apply(Throwable t) {
                System.out.println("执行失败：" + t.getMessage());
                return null;
            }
        });

    }
}
