package com.example.yishutansuo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.LayoutInflaterCompat;
import androidx.core.view.LayoutInflaterFactory;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import rxjava.Observable;
import rxjava.OnSubscribe;
import rxjava.Subscribe;
import view.RefreshableView;

public class ActivityD extends AppCompatActivity {
    private LinearLayout linearLayout;
    RefreshableView refreshableView;
    ListView listView;
    ArrayAdapter<String> adapter;
    String[] items = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L" ,"M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        LayoutInflaterCompat.setFactory2(getLayoutInflater(), new LayoutInflater.Factory2() {
//            @Nullable
//            @Override
//            public View onCreateView(@Nullable View view, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attributeSet) {
//                Log.e("ymy",name);
//                return null;
//            }
//
//            @Nullable
//            @Override
//            public View onCreateView(@NonNull String s, @NonNull Context context, @NonNull AttributeSet attributeSet) {
//                return null;
//            }
//        });
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_d);
//        linearLayout = findViewById(R.id.linearLayout);
//
//        View v =getLayoutInflater().inflate(R.layout.button_layout,null,false);

        // linearLayout.addView(v);

        //下拉刷新
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_d);
        refreshableView = (RefreshableView) findViewById(R.id.refreshable_view);
        listView = (ListView) findViewById(R.id.list_view);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refreshableView.finishRefreshing();
            }
        }, 0);


    }




    public void click(View view){
        Observable.create(new OnSubscribe<String>() {
            @Override
            public void call(Subscribe<? super String> subscribe) {
                subscribe.onNext("开始打铁");
            }
        }).subscribe(new Subscribe<String>() {
            @Override
            public void onNext(String s) {

            }
        });
    }


}