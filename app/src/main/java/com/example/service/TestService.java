package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Random;

/**
 * created by zhangshi on 1/31/21.
 */
public class TestService extends Service {

    public class MyBinder extends Binder {

        public TestService getService(){
            return TestService.this;
        }

    }

    //通过binder实现调用者client与Service之间的通信
    private MyBinder binder = new MyBinder();

    private final Random generator = new Random();

    @Override
    public void onCreate() {
        Log.i("DemoLog","TestService -> onCreate, Thread: " + Thread.currentThread().getName());
        super.onCreate();
    }

//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        Log.i("DemoLog", "TestService -> onStartCommand, startId: " + startId + ", Thread: " + Thread.currentThread().getName());
//        return START_NOT_STICKY;
//    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("DemoLog", "TestService -> onStartCommand, startId: " + startId + ", Thread: " + Thread.currentThread().getName());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("DemoLog", "TestService -> onBind, Thread: " + Thread.currentThread().getName());
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("DemoLog", "TestService -> onUnbind, from:" + intent.getStringExtra("from"));
        return false;
    }

    @Override
    public void onDestroy() {
        Log.i("DemoLog", "TestService -> onDestroy, Thread: " + Thread.currentThread().getName());
        super.onDestroy();
    }

    //getRandomNumber是Service暴露出去供client调用的公共方法
    public int getRandomNumber(){
        return generator.nextInt();
    }
}