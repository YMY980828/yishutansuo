package com.example.yishutansuo;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import javax.inject.Inject;

import annotation.AnnotationProcessor;
import annotation.Get;

import util.User;


public class ActivityH extends AppCompatActivity {
    @Inject
    User user;
    @Inject
    User user1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainh);

        //HttpURLConnection
        URLConnection();
        AnnotationProcessor.process();

       // DaggerUserComonent.create().toActivity(this);
      //  DaggerUserComonent.builder().userModule(new UserModule()).build().toActivity(this);
        ((AppContext) getApplication()).getUserComonent().toActivity(this);
        Log.e("yh",String.valueOf("user"+user.hashCode()));
        Log.e("yh",String.valueOf("user1"+user1.hashCode()));

    }

    private void URLConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
        HttpURLConnection urlConnection;
        ByteArrayOutputStream bos = null;
        InputStream inputStream = null;
        try {
            URL url = new URL("http://sxhgz.snzfnm.com/s3xcertificate/webapp/portal/getEntClassifyListWithSmallClassifyConfig.do?urlFlag=ylweb");
            urlConnection = (HttpURLConnection) url.openConnection();
             if ( urlConnection.getResponseCode()==200){
                  inputStream =  urlConnection.getInputStream();
                 byte[] b = new byte[1024*10];
                 int len;
                   bos = new ByteArrayOutputStream();
                    while ( (len=inputStream.read(b))>-1){
                        bos.write(b,len,0);
                    }

                 Log.e("ymy", new String(b) );
             }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e("ymy", Objects.requireNonNull(e.getMessage()));
        } catch (IOException e) {
            e.printStackTrace();
             Log.e("ymy", Objects.requireNonNull(e.getMessage()));
        }        finally {
            try {
                     if (bos!=null){
                         bos.close();
                     }
                     if (inputStream!=null){
                            inputStream.close();
                     }
            } catch (Exception e){
                                Log.e("ymy", Objects.requireNonNull(e.getMessage())); 
            }

        }

            }
        }).start();
    }

     @Get(value = "http://sxhgz.snzfnm.com/s3xcertificate/webapp/portal/getEntClassifyListWithSmallClassifyConfig.do?urlFlag=ylweb")
     private void getIpMsg(){
     }

    public void jumpActivity(View view){
        Intent intent = new Intent(this,MainActivity2.class);
        startActivity(intent);
    }


}
