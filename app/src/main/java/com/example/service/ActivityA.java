package com.example.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

public class ActivityA extends AppCompatActivity implements View.OnClickListener{

    private TestService service = null;

    private boolean isBound = false;

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            isBound = true;
            TestService.MyBinder myBinder = (TestService.MyBinder)binder;
            service = myBinder.getService();
            Log.i("DemoLog", "ActivityA onServiceConnected");
            int num = service.getRandomNumber();
            Log.i("DemoLog", "ActivityA 中调用 TestService的getRandomNumber方法, 结果: " + num);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
            Log.i("DemoLog", "ActivityA onServiceDisconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        Log.i("DemoLog", "ActivityA -> onCreate, Thread: " + Thread.currentThread().getName());
        findViewById(R.id.btnBindService).setOnClickListener(this);
        findViewById(R.id.btnUnbindService).setOnClickListener(this);
        findViewById(R.id.btnStartActivityB).setOnClickListener(this);
        findViewById(R.id.btnFinish).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnBindService){
            //单击了“bindService”按钮
            Intent intent = new Intent(this, TestService.class);
            intent.putExtra("from", "ActivityA");
            Log.i("DemoLog", "-------------------------------------------------------------");
            Log.i("DemoLog", "ActivityA 执行 bindService");
            bindService(intent, conn, BIND_AUTO_CREATE);
            startService(intent);
        }else if(v.getId() == R.id.btnUnbindService){
            //单击了“unbindService”按钮
            if(isBound){
                Log.i("DemoLog", "-------------------------------------------------------------");
                Log.i("DemoLog", "ActivityA 执行 unbindService");
                unbindService(conn);
            }
        }else if(v.getId() == R.id.btnStartActivityB){
            //单击了“start ActivityB”按钮
            Intent intent = new Intent(this, ActivityB.class);
            Log.i("DemoLog", "-------------------------------------------------------------");
            Log.i("DemoLog", "ActivityA 启动 ActivityB");
            startActivity(intent);
        }else if(v.getId() == R.id.btnFinish){
            //单击了“Finish”按钮
            Log.i("DemoLog", "-------------------------------------------------------------");
            Log.i("DemoLog", "ActivityA 执行 finish");
            this.finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("DemoLog", "ActivityA -> onDestroy");
    }
}
