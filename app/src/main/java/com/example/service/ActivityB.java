package com.example.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

public class ActivityB extends AppCompatActivity implements View.OnClickListener{

    private TestService service = null;

    private boolean isBound = false;

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            isBound = true;
            TestService.MyBinder myBinder = (TestService.MyBinder)binder;
            service = myBinder.getService();
            Log.i("DemoLog", "ActivityB onServiceConnected");
            int num = service.getRandomNumber();
            Log.i("DemoLog", "ActivityB 中调用 TestService的getRandomNumber方法, 结果: " + num);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
            Log.i("DemoLog", "ActivityB onServiceDisconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("DemoLog", "ActivityBBBBB -> onCreate, Thread: " + Thread.currentThread().getName());
        setContentView(R.layout.activity_b);
        findViewById(R.id.btnBindService).setOnClickListener(this);
        findViewById(R.id.btnUnbindService).setOnClickListener(this);
        findViewById(R.id.btnFinish).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnBindService){
            Intent intent = new Intent(this, TestService.class);
            intent.putExtra("from", "ActivityB");
            Log.i("DemoLog", "----------------------------------------------------------------------");
            Log.i("DemoLog", "ActivityB 执行 bindService");
            bindService(intent, conn, BIND_AUTO_CREATE);
        }else if(v.getId() == R.id.btnUnbindService){
            if(isBound){
                Log.i("DemoLog", "----------------------------------------------------------------------");
                Log.i("DemoLog", "ActivityB 执行 unbindService");
                unbindService(conn);
            }
        }else if(v.getId() == R.id.btnFinish){
            //单击了“Finish”按钮
            Log.i("DemoLog", "----------------------------------------------------------------------");
            Log.i("DemoLog", "ActivityB 执行 finish");
            this.finish();
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.i("DemoLog", "ActivityBBBBB -> onDestroy");
    }
}
