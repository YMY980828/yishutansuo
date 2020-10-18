package com.example.yishutansuo;

import android.os.Bundle;
import android.os.Environment;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import util.User;

public class Activity4 extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        User user = (User) getIntent().getSerializableExtra("user");

        new Thread(()->{
            File file = new File(Environment.getExternalStorageDirectory(),"cache.txt");
            if (file.exists()){
                file.delete();
            }
            try {
                file.createNewFile();
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
                out.writeObject(user);
                out.close();

                ObjectInputStream rs = new ObjectInputStream(new FileInputStream(file));
                User u =(User)rs.readObject();
                rs.close();
                runOnUiThread(()->{
                    showToast( u.userName);
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();




    }
}
