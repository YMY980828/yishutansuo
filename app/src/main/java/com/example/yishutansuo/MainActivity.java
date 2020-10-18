package com.example.yishutansuo;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import butterknife.BindView;
import butterknife.OnClick;
import util.Stu;
import util.User;
import util.UserManger;

public class MainActivity extends BaseActivity {

    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.btn4)
    Button btn4;
    @BindView(R.id.btn5)
    Button btn5;
    @BindView(R.id.btn6)
    Button btn6;
    @BindView(R.id.btn7)
    Button btn7;
    @BindView(R.id.btn8)
    Button btn8;
    @BindView(R.id.btn9)
    Button btn9;
    @BindView(R.id.btnA)
    Button btnA;
    @BindView(R.id.btnB)
    Button btnB;
    @BindView(R.id.btnC)
    Button btnC;
    @BindView(R.id.btnD)
    Button btnD;
    @BindView(R.id.btnE)
    Button btnE;
    @BindView(R.id.btnF)
    Button btnF;
    @BindView(R.id.btnG)
    Button btnG;
    @BindView(R.id.btnH)
    Button btnH;
    @BindView(R.id.btnI)
    Button btnI;
    @BindView(R.id.btnJ)
    Button btnJ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("ymy","MainActivity->onCreate");
        setContentView(R.layout.activity_main);
        init();
        btn1.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(),Activtiy1.class);
            startActivity(intent);
        });

        Log.e("ymy",getTaskId()+"");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("ymy","MainActivity->onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("ymy","MainActivity->onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("ymy","MainActivity->onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("ymy","MainActivity->onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("ymy","MainActivity->onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("ymy","MainActivity->onDestroy");
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @OnClick(
            {
            R.id.btn2,R.id.btn3,R.id.btn4,R.id.btn5,R.id.btn6,R.id.btn7,R.id.btn8,R.id.btn9,R.id.btnA,R.id.btnB,R.id.btnC,R.id.btnD,R.id.btnE,R.id.btnF,R.id.btnG
            ,R.id.btnH,R.id.btnI,R.id.btnJ
            })
    public void click(View view){
        if (view==btn2){
            Intent intent = new Intent("com.ymy");
            startActivity(intent);
        }else if (view==btn3){
            UserManger.userId++;
            Log.e("ymy", String.valueOf(UserManger.userId));
            Intent intent = new Intent(getActivity(),Activity3.class);
            startActivity(intent);
        }else if (view==btn4){
            User user = new User(41,"yh",true);
            Intent intent = new Intent(getActivity(),Activity4.class);
            intent.putExtra("user",user);
            startActivity(intent);
        }else if (view==btn5){
            Intent intent = new Intent(getActivity(),Activity5.class);
            startActivity(intent);
        }else if (view==btn6){
            Intent intent = new Intent(getActivity(),Activity6.class);
            Stu stu = new Stu();
            stu.setUsername("yh");
            stu.setAge(20);
            intent.putExtra("stu",stu);
            startActivity(intent);
        }else if (view==btn7){
            Intent intent = new Intent(getActivity(),Activity7.class);
            startActivity(intent);
        }else if (view==btn8){
            Intent intent = new Intent(getActivity(),Activity8.class);
            startActivity(intent);
        }else if (view==btn9){
            Intent intent = new Intent(getActivity(),Activity9.class);
            startActivity(intent);
        }else if (view==btnA){
            Intent intent = new Intent(getActivity(),ActivityA.class);
            startActivity(intent);
        }else if (view==btnB){
            Intent intent = new Intent(getActivity(),ActivityB.class);
            startActivity(intent);
        }else if (view==btnC){
            Intent intent = new Intent(getActivity(),ActivityC.class);
            startActivity(intent);
        }else if (view==btnD){
            Intent intent = new Intent(getActivity(),ActivityD.class);
            startActivity(intent);
        }else if (view==btnE){
            Intent intent = new Intent(getActivity(),ActivityE.class);
            startActivity(intent);
        }else if (view==btnF){
            Intent intent = new Intent(getActivity(),ActivityF.class);
            startActivity(intent);
        }else if (view==btnG){
            Intent intent = new Intent(getActivity(),ActivityG.class);
            startActivity(intent);
        }else if (view==btnH){
            Intent intent = new Intent(getActivity(),ActivityH.class);
            startActivity(intent);
        }else if (view==btnI){
            Intent intent = new Intent(getActivity(),ActivityI.class);
            startActivity(intent);
        }else if (view==btnJ){
            Intent intent = new Intent(getActivity(),ActivityJ.class);
            startActivity(intent);
        }

    }

}