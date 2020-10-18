package com.example.yishutansuo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.format.Time;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rxjava.Action;
import rxjava.OnSubscribe;
import rxjava.Subscribe;


public class ActivityI extends AppCompatActivity {
    @BindView(R.id.r_tv)
    TextView r_tv;
    private final static String TAG = "ActivityI";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i);
        ButterKnife.bind(this);
        init();

        //创建操作符

        //类似js中setInterval
       // interval();
        //类似js中setTimeOut
     //   timer();

        //变换操作符
   //     map();

        //rxJava搭配OkHttp
    //    okHttpWithRxJava();

        //rxJava搭配retrofit



    }
    private void init(){
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {

                MyThread myThread = new MyThread();
                myThread.setEmitter(emitter);
                myThread.start();
            }
        });
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {

                        Toast.makeText(ActivityI.this,s,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
    private class MyThread extends Thread implements Runnable{
        ObservableEmitter<String> emitter ;

        public ObservableEmitter<String> getEmitter() {
            return emitter;
        }

        public void setEmitter(ObservableEmitter<String> emitter) {
            this.emitter = emitter;
        }

        @Override
        public void run() {
            super.run();
            Looper.prepare();
            CountDownTimer countDownTimer = new CountDownTimer(3000,1000) {
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    MyThread.this.emitter.onNext("于明仪是我老婆!");
                }
            };
            countDownTimer.start();
            Looper.loop();

            new HandlerThread("TestThread") {
                @Override
                protected void onLooperPrepared() {
                    super.onLooperPrepared();
                    CountDownTimer countDownTimer = new CountDownTimer(3000,1000) {
                        @Override
                        public void onTick(long l) {
                            Log.e(TAG,String.valueOf(l));
                        }

                        @Override
                        public void onFinish() {
                            Log.e(TAG,"123");
                            MyThread.this.emitter.onNext("我爱于明仪");
                        }
                    };
                    countDownTimer.start();
                }
            }.start();
        }
    }
    private void interval(){
        Observable
                .interval(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Throwable {
                        r_tv.setText( "我爱于明仪-"+aLong );
                    }
                });

    }
    private void timer(){
        Observable.timer(3, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Throwable {
                        r_tv.setText("我爱于明仪!");
            }
        });
    }
    private void map(){
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                emitter.onNext("于明仪");
            }
        }).map(new Function<String, String>() {
            @Override
            public String apply(String s) throws Throwable {
                s+="是我老婆";
                return s;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String o) throws Throwable {
                    r_tv.setText(o);
            }
        });
    }
    private void okHttpWithRxJava(){
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                String res;
                OkHttpClient client = new OkHttpClient.Builder().build();
                Request request = new Request
                        .Builder()
                        .get()
                        .url("http://sxhgz.snzfnm.com/s3xcertificate/webapp/portal/getArticleLoopImageList.do?urlFlag=ylweb")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    res = response.body().string();
                    emitter.onNext(res);
                }catch (Exception e){
                    e.printStackTrace();
                    emitter.onError(e);
                }

            }
        }).subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
           .subscribe(new Consumer<String>() {
               @Override
               public void accept(String s) throws Throwable {
                   Log.e(TAG, s);
               }
           }, new Consumer<Throwable>() {
               @Override
               public void accept(Throwable throwable) throws Throwable {
                   Log.e(TAG, "错误");
               }
           });



    }


}