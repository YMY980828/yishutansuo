package com.example.yishutansuo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import view.MoveView;

public class ActivityG extends AppCompatActivity {
    private MoveView moveView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //调用getWindow（）.setContentView（layoutResID）。
        setContentView(R.layout.activity_g);
        moveView = findViewById(R.id.moveView);
        moveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)moveView.getLayoutParams();
                Toast.makeText(ActivityG.this,String.valueOf(moveView.getLeft()+"--"+layoutParams.leftMargin),Toast.LENGTH_LONG).show();
            }
        });
    }

}