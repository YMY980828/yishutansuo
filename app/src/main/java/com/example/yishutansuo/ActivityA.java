package com.example.yishutansuo;

import android.app.ProgressDialog;
import android.os.Bundle;

import android.util.Log;

import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;

import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;

import io.reactivex.rxjava3.disposables.Disposable;

import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class ActivityA extends BaseActivity{
   ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
      //  init();
      //  init_1();
      //  init_2();
      //    init_3();
        init_4();


    }
    public class User {
        public String say(){
            try {
                Thread.sleep(2000);
            }catch (Exception e){
                e.printStackTrace();
            }

            throw new Error("出错啦");
        }
    }

    Observer<String> observable = new Observer<String>() {

        @Override
        public void onSubscribe(@NonNull Disposable d) {

        }

        @Override
        public void onNext(@NonNull String s) {

        }

        @Override
        public void onError(@NonNull Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };

    public void init_4(){
     List<Students> students = new ArrayList<>();
        List<String> courses = new ArrayList<>();
        courses.add("语文");
        courses.add("数学");
        courses.add("英语");
        Students stu1 = new Students("ymy",courses);
        Students stu2 = new Students("yh",courses);
        Students stu3 = new Students("wy",courses);
        students.add(stu1);
        students.add(stu2);
        students.add(stu3);

        Observable.fromArray(students)
                .flatMap(new Function<List<Students>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(List<Students> stus) throws Throwable {
                        return Observable.fromIterable(stus);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(o -> {
                    Students s = (Students) o;
                    Log.e("ymy", s.name);
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {

                        Log.e("ymy",  throwable.getMessage());
                    }
                });


    }



    public void init_3(){
        Observable.just("ymy","wy","yh")
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Throwable {
                        if (s.startsWith("y")){
                            return s;
                        }
                        return "";
                    }
                })
                .subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Throwable {
                Log.e("ymy",s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Throwable {

            }
        }, new Action() {
            @Override
            public void run() throws Throwable {

            }
        });


    }
    public void init_2(){
        Observable.just("ymy","wy","yh")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {

                }, throwable -> {

                }, () -> {

                });

    }
    public void init_1(){
        String[] words = {"Hello", "Hi", "Aloha"};
         Observable.fromArray(words)
                 .subscribe(new Observer<String>() {
                     @Override
                     public void onSubscribe(@NonNull Disposable d) {

                     }

                     @Override
                     public void onNext(@NonNull String s) {
                            Log.e("ymy",s);
                     }

                     @Override
                     public void onError(@NonNull Throwable e) {

                     }

                     @Override
                     public void onComplete() {

                     }
                 });

    }


    /**
     * Observable  被观察者
     * observer 观察者
     *
     */
    @Override
    public void init() {
        super.init();
            Observable.create((ObservableOnSubscribe<String>) emitter -> {
                User user = new User();
                emitter.onNext(user.say());


            }).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Object>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            progressDialog.setMessage("加载中...");
                            progressDialog.show();
                        }

                        @Override
                        public void onNext(@NonNull Object o) {
                            progressDialog.cancel();
                                    showToast(String.valueOf(o));
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            progressDialog.cancel();
                            showToast(e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                            progressDialog.cancel();
                        }
                    });


    }

    public class Students{

        public List<String> courses = new ArrayList<>();
        public String name;
        public Students(String name , List<String> courses ){
            this.name=name;
            this.courses=courses;
        }

        public String getName(){
            return name;
        }
        public List<String> getCourses(){
            return courses;
        }


    }


}
