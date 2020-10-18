package com.example.yishutansuo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import javax.inject.Inject;


import util.Stu;
import util.User;

public class MainActivity2 extends AppCompatActivity {

    @Inject
    User user;
    @Inject
    Stu stu;
    @Inject
    Stu stu1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
      //  DaggerUserComonent.create().toMainActivity2(this);
        //全局单例
        ((AppContext) getApplication()).getUserComonent().toMainActivity2(this);

        Log.e("yh", "user" + user.hashCode());
        Log.e("yh", "stu" + stu.hashCode());
        Log.e("yh", "stu1" + stu1.hashCode());
    }
}