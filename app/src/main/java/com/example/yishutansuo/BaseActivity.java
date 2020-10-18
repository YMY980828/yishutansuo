package com.example.yishutansuo;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * singleTop:栈顶复用模式
 * singleTask:栈内复用模式
 * singleInstance:单实例模式
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public void init(){
        ButterKnife.bind(this);
    }
    public Context getActivity(){
        return  this;
    }
    public void showToast(String string){
        Toast.makeText(getActivity(),string,Toast.LENGTH_SHORT).show();
    }
}
