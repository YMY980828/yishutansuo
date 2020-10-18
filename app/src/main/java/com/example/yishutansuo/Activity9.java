package com.example.yishutansuo;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Activity9 extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main9);
        init();

       String s1 =  getCacheDir().getAbsolutePath();
       String s2 =    getFilesDir().getAbsolutePath();
       String s3 =     getExternalFilesDir(null).getAbsolutePath();

        Log.e("yh",s1);
        Log.e("yh",s2);
        Log.e("yh",s3);

    }

    @Override
    public void init() {
        super.init();
    new Thread(){
        @Override
        public void run() {
            super.run();
            OkHttpClient okHttpClient = new OkHttpClient
                    .Builder()
                    .connectTimeout(8000, TimeUnit.SECONDS)
//                    .addInterceptor(new MyInterceptor())
                    .addNetworkInterceptor(new MyInterceptor())
                    .build();
            Request request = new Request
                    .Builder()
                    .get()
                    .header("X-Token","123456")
                    .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36")
                    .url("http://www.baidu.com")
                    .build();
            try {
                Response response = okHttpClient.newCall(request).execute();

                Message msg = new Message();
                msg.what=1;
                msg.obj = response.body().string();
              handler.sendMessage(msg);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }.start();


    }
    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    showToast(String.valueOf(msg.obj));
                    break;
            }
        }
    };

    public class MyInterceptor implements Interceptor{

        @NotNull
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {

            Request request = chain.request();
       //     Request r = request.newBuilder().header("auth","17802528170").build();
              Log.e("ymy",String.valueOf(request.url()));
           Log.e("ymy",String.valueOf(request.headers()));

            return chain.proceed(request);
        }

    }



}
