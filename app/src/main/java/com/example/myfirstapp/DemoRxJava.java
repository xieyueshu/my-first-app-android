package com.example.myfirstapp;

import android.util.Log;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


import io.reactivex.functions.Consumer;

public class DemoRxJava {
    /**
     * Rxjava简单演示Demo
     */
    public static void demoRxJava01() {
        Observable.create(new ObservableOnSubscribe<Integer>() { // 第一步：初始化Observable
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                System.out.println("Observable emit 1");

                e.onNext(1);
                System.out.println("Observable emit 2");
                e.onNext(2);
                System.out.println("Observable emit 3");
                e.onNext(3);
                e.onComplete();
                System.out.println("Observable emit 4");
                e.onNext(4);
            }
        }).subscribe(new Observer<Integer>() { // 第三步：订阅
            // 第二步：初始化Observer
            private int i;
            private Disposable mDisposable;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mDisposable = d;
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                i++;
                if (i == 2) {
                    // 在RxJava 2.x 中，新增的Disposable可以做到切断的操作，让Observer观察者不再接收上游事件
                    mDisposable.dispose();
                }
                System.out.println("onNext：" + "Observable emit "+integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println( "Observable onError");
            }

            @Override
            public void onComplete() {
                System.out.println("Observable onComplete");
            }
        });
    }
    /**
     * Rxjava线程切换演示Demo
     */
    public static void demoRxJava01_1() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                System.out.println( "Observable thread is" + Thread.currentThread().getName());
                e.onNext(1);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        System.out.println( "Observable Thread turn to(mainThread), Current thread is" + Thread.currentThread().getName());

                    }
                })
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        System.out.println( "Observable Thread turn to(io), Current thread is" + Thread.currentThread().getName());

                    }
                });

    }

}
