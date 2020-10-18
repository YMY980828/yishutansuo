package com.example.yishutansuo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import view.RippleAnimationView;

public class ActivityF extends AppCompatActivity {
    private RippleAnimationView ripple_animation_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f);
        ripple_animation_view = findViewById(R.id.ripple_animation_view);
        ripple_animation_view.start();
        Toast.makeText(this,threadLocal.get(),Toast.LENGTH_LONG).show();
        EventBus.getDefault().register(this);
        MessageEvent messageEvent = new MessageEvent();
        messageEvent.setMessage("ymy");
        EventBus.getDefault().post(messageEvent);
        EventBus.getDefault().post(new Msg("yh"));
        EventBus.getDefault().post("于明仪是我老婆");

    }
    public class MessageEvent{
        public String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN,priority = 1)
    public void onMessageEvent(Msg event) {

        Toast.makeText(ActivityF.this,event.message,Toast.LENGTH_LONG).show();
    }

    public class Msg{
        private String message;

       public Msg(String msg){
           message=msg;
       }
    }

    @Subscribe(threadMode = ThreadMode.MAIN,priority = 2)
    public void getMessageEvent(MessageEvent event) {

        Toast.makeText(ActivityF.this,event.getMessage(),Toast.LENGTH_LONG).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(String flag) {

        Toast.makeText(ActivityF.this,String.valueOf(flag),Toast.LENGTH_LONG).show();
    }







    private Handler handler1 = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            return false;
        }
    });
    private Handler handler2 = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            return false;
        }
    });


    final  ThreadLocal<String > threadLocal = new ThreadLocal<String>(){

        @Nullable
        @Override
        protected String initialValue() {
            return "我爱于明仪";

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        threadLocal.remove();
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }

    }
}