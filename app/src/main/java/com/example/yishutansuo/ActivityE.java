package com.example.yishutansuo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;


import butterknife.BindView;
import util.Global;
import view.StatusBarView;

public class ActivityE extends BaseActivity {
    @BindView(R.id.tool_bar)
    public Toolbar tool_bar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e);
        init();
        hide();

    }

    private void hide() {

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            |WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }else if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        StatusBarView statusBarView = new StatusBarView(this);
        int h = Global.getStatusBarHeight(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,h );
        statusBarView.setLayoutParams(params);
        ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
        decorView.addView(statusBarView);
        if (tool_bar!=null){
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) tool_bar.getLayoutParams();
            marginLayoutParams.setMargins(0,h,0,0);
        }


    }


    @Override
    public void init() {
        super.init();
        setSupportActionBar(tool_bar);
        ;
        tool_bar.setTitle("歌单");
        tool_bar.setSubtitle("编辑推荐,编辑推荐精选单曲");
        //setDisplayHomeAsUpEnabled(true)
       // tool_bar.setNavigationIcon(R.drawable.arrow_back);
        modifyToolBar();
    }

    private void modifyToolBar() {


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
