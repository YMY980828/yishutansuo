package com.example.yishutansuo;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ActivityC extends BaseActivity {
    private TextView tv;
    private NetWorkStateReceiver netWorkStateReceiver;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainc);
        netWorkStateReceiver = new NetWorkStateReceiver();
        //init();
     //   cacheTest();
        httpsTest();
    }
    //在onResume()方法注册
    @Override
    protected void onResume() {
        if (netWorkStateReceiver == null) {
            netWorkStateReceiver = new NetWorkStateReceiver();
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netWorkStateReceiver, filter);
        super.onResume();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void init() {
        super.init();
        tv = findViewById(R.id.box);
         ObjectAnimator animator = ObjectAnimator.ofArgb(tv,"backgroundColor" ,Color.RED,Color.BLACK,Color.BLUE);
        animator.setDuration(5000);
        animator.start();
    }
    private void cacheTest(){
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                File file = new File(getCacheDir(),"control");

                Cache cache = new Cache(file,1024 * 1024 * 100);//100M
                OkHttpClient okHttpClient = new OkHttpClient

                        .Builder()
                        .addInterceptor(new Interceptor() {
                            @NotNull
                            @Override
                            public Response intercept(@NotNull Chain chain) throws IOException {
                                Request request = chain.request();
                                Response response = chain.proceed(request);
                                String cacheControl = request.cacheControl().toString();
                                if (TextUtils.isEmpty(cacheControl)) {
                                    cacheControl = "public, max-age=60";
                                }
                                //这里设置了缓存时间为60秒
                                Response cacheResponse = response.newBuilder().header("Cache-Control", cacheControl).removeHeader("Pragma").build();
                                Log.e("yh",String.valueOf(request));
                                Log.e("yh",String.valueOf(request.headers()));
                                Log.e("yh",String.valueOf(request.method()));
                                Log.e("yh",String.valueOf(cacheResponse.headers()));
                                Log.e("yh",String.valueOf(cacheResponse.code()));
                                return  cacheResponse;
                            }
                        })
                        .cache(cache)
                        .build();

                Request request = new Request
                        .Builder()
                        .url("https://kyfw.12306.cn/otn/")
                        .get()
                        .build();
                String res = "";
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    res = response.body().string();
                    if (response.cacheResponse()==null){
                        Log.e("yh","缓存没有数据");
                    }
                    if (response.networkResponse()==null){
                        Log.e("yh","网络请求没有数据");
                    }

//                    response.cacheResponse();
//                    response.networkResponse()
                }catch (Exception e){
                    e.printStackTrace();
                    emitter.onError(e);
                }

                emitter.onNext(res);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Throwable {
                            Log.e("yh",s);
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
    private void netWorkState(){
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取WIFI连接的信息
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            Network[] networks = connMgr.getAllNetworks();
            StringBuilder sb = new StringBuilder();
            //通过循环将网络信息逐个取出来
            for (int i=0; i < networks.length; i++){
                //获取ConnectivityManager对象对应的NetworkInfo对象
                NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
                sb.append(networkInfo.getTypeName() + " connect is " + networkInfo.isConnected());
            }

        }else{
            NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            Boolean isWifiConn = networkInfo.isConnected();
            networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            Boolean isMobileConn = networkInfo.isConnected();
            if (isWifiConn){
                Log.e("yh","正在使用Wifi");
            }else if (isMobileConn){
                Log.e("yh","正在使用移动数据");
            }

        }

    }
    public class NetWorkStateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            System.out.println("网络状态发生变化");
            //检测API是不是小于23，因为到了API23之后getNetworkInfo(int networkType)方法被弃用
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {

                //获得ConnectivityManager对象
                ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

                //获取ConnectivityManager对象对应的NetworkInfo对象
                //获取WIFI连接的信息
                NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                //获取移动数据连接的信息
                NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if (wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                    Toast.makeText(context, "WIFI已连接,移动数据已连接", Toast.LENGTH_SHORT).show();
                } else if (wifiNetworkInfo.isConnected() && !dataNetworkInfo.isConnected()) {
                    Toast.makeText(context, "WIFI已连接,移动数据已断开", Toast.LENGTH_SHORT).show();
                } else if (!wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                    Toast.makeText(context, "WIFI已断开,移动数据已连接", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "WIFI已断开,移动数据已断开", Toast.LENGTH_SHORT).show();
                }
//API大于23时使用下面的方式进行网络监听
            }else {

                System.out.println("API level 大于23");
                //获得ConnectivityManager对象
                ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

                //获取所有网络连接的信息
                Network[] networks = connMgr.getAllNetworks();
                //用于存放网络连接信息
                StringBuilder sb = new StringBuilder();
                //通过循环将网络信息逐个取出来
                for (int i=0; i < networks.length; i++){
                    //获取ConnectivityManager对象对应的NetworkInfo对象
                    NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
                    sb.append(networkInfo.getTypeName() + " connect is " + networkInfo.isConnected());
                }
                Toast.makeText(context, sb.toString(),Toast.LENGTH_SHORT).show();
            }
        }
    }
    //onPause()方法注销
    @Override
    protected void onPause() {
        unregisterReceiver(netWorkStateReceiver);
        super.onPause();
    }
    private void httpsTest(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.sslSocketFactory(createSSLSocketFactory());
        builder.hostnameVerifier(new TrustAllHostnameVerifier());
        OkHttpClient okHttpClient = builder.build();
    }
        //在创建Okhttp的时候我们调用了sslSocketFactory设置一个SSL工厂
        //同时还调用hostnameVerifier设置了信任所有域名
    /**
     * 信任所有的证书
     */
    @SuppressLint("TrulyRandom")
    private static SSLSocketFactory createSSLSocketFactory() {

        SSLSocketFactory sSLSocketFactory = null;
        try {
            //创建SSL上下文
            SSLContext sc = SSLContext.getInstance("TLS");
            //使用我们定义的TrustAllManager来初始化
            sc.init(null, new TrustManager[]{new TrustAllManager()},
                    new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }
        return sSLSocketFactory;
    }

    /**
     * 信任所有证书的管理器
     */
    private static class TrustAllManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0]; //这里不能返回null，不然会空指针
        }
    }
    /**
     * 信任所有域名
     */
    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            //信任所有证书
            //正常情况是指信任需要的域名，比如像下面这样只信任baidu
//      if ("www.baidu.com".equals(hostname)) {
//        return true;
//      }
//      return false;
            return true;
        }
    }
}


