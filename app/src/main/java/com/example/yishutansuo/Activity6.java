package com.example.yishutansuo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import util.Stu;

public class Activity6 extends BaseActivity {

    private MyReceiver myReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        init();

    }


    public class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            switch (intent.getAction()){
                case "com.ymy":
                    showToast("接受到一个广播!");
                    break;
            }
        }
    }

    @Override
    public void init() {
        super.init();
        myReceiver = new MyReceiver();
        Stu stu = getIntent().getParcelableExtra("stu");
        showToast(stu.getUsername());

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.ymy");
        registerReceiver(myReceiver,intentFilter);
        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("com.ymy");
                sendBroadcast(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myReceiver!=null){
            unregisterReceiver(myReceiver);
            myReceiver=null;
        }

    }
}
