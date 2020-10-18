package com.example.yishutansuo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import view.IndexSideBar;
import view.MatrixView;

public class ActivityB extends BaseActivity {
    MatrixView matrixView;
    IndexSideBar indexSideBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainb);
        init();
       // matrixView = findViewById(R.id.matrixView);
     //   matrixView.animating();
        indexSideBar = findViewById(R.id.index_slide_bar);
        TextView tv = findViewById(R.id.index_slide_dialog);
        indexSideBar.setIndicatorTv(tv);
    }



}
