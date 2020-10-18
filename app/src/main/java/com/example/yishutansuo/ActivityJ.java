package com.example.yishutansuo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.IntentService;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

public class ActivityJ extends AppCompatActivity {

    private ThreadLocal<String> stringThreadLocal = new ThreadLocal<String>(){
        @Nullable
        @Override
        protected String initialValue() {
            return super.initialValue();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_j);
        init();
        Window window = getWindow();
        ViewGroup decor = (ViewGroup) window.getDecorView();
        View v = LayoutInflater.from(this).inflate(R.layout.activity_f,decor,true);


    }

    private void init(){
       //线程池

        

        new Thread(new Runnable() {
            @Override
            public void run() {
                HandlerThread handlerThread = new HandlerThread(Thread.currentThread().getName());
                Handler handler = new Handler(handlerThread.getLooper(), new Handler.Callback() {
                    @Override
                    public boolean handleMessage(@NonNull Message message) {
                        return false;
                    }
                });

            }
        });
    }




}