package com.example.yishutansuo;

import android.app.Application;
import android.util.Log;

import com.tencent.bugly.crashreport.CrashReport;


import org.greenrobot.eventbus.EventBus;

import util.DaggerStuComponent;
import util.DaggerUserComonent;
import util.UserComonent;
import util.UserModule;


public class AppContext extends Application {
    private UserComonent userComonent;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("ymy","Application->onCreate");
        CrashReport.initCrashReport(this,"9b6ec368ec", true);
        EventBus eventBus = EventBus.builder().addIndex(new MyEventBusIndex()).build();
          userComonent = DaggerUserComonent
                  .builder()
                  .userModule(new UserModule())
                  .stuComponent(DaggerStuComponent.create())
                  .build();
    }


    public UserComonent getUserComonent() {
        return userComonent;
    }
}
