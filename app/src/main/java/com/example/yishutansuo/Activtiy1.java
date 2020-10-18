package com.example.yishutansuo;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

public class Activtiy1 extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("ymy","Activtiy1->onCreate");
        Log.e("ymy",getTaskId()+"");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.e("ymy","Activtiy1->onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("ymy","Activtiy1->onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("ymy","Activtiy1->onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("ymy","Activtiy1->onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("ymy","Activtiy1->onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("ymy","Activtiy1->onDestroy");
    }
}
