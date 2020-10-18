package com.example.yishutansuo;
import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.lvrenyang.pos.Pos;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Set;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import util.Global;

public class Activity8 extends Activity {
    private boolean mScanning=false;
    private BluetoothAdapter bluetoothAdapter;
    private Set<BluetoothDevice> pairedDevices;
    private Boolean sdkIsLess431;
    private LeDeviceListAdapter mLeDeviceListAdapter;
    private ListView listView;
    private Button scan;
    private ProgressDialog dialog;
    private Handler mHandler1;
    private boolean connected;
    public  Bitmap getBitmap(String url) {
        URL imageURL = null;
        Bitmap bitmap = null;
        try {
            imageURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(8);
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) imageURL
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inSampleSize = 2;
          //  bitmap = BitmapFactory.decodeStream(is,null,options);
            bitmap = BitmapFactory.decodeStream(is);
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(8);
        }
        return bitmap;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_main8);
      //  getWindow().setFeatureInt(Window.FEATURE_INDETERMINATE_PROGRESS, R.layout.progress);

        scan = findViewById(R.id.scan);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkBluetoothAndLocationPermission();
        }
        int sdkVersion=0;
        sdkVersion = Integer.valueOf(Build.VERSION.SDK_INT);
        if (sdkVersion>=18){
            sdkIsLess431 =false;
        }else{
            sdkIsLess431 =true;
        }

        if (sdkIsLess431){
            bluetoothAdapter =BluetoothAdapter.getDefaultAdapter();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
            intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
            intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
            registerReceiver(broadcastReceiver, intentFilter);
        }else{
            BluetoothManager bluetoothManager = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
                bluetoothAdapter = bluetoothManager.getAdapter();
            }
        }
        mLeDeviceListAdapter = new LeDeviceListAdapter();

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!bluetoothAdapter.isEnabled()){
                    Toast.makeText(Activity8.this,"请先打开蓝牙",Toast.LENGTH_LONG).show();
                    return;
                }

                setProgressBarIndeterminateVisibility(true);
                if (!mScanning){
                    mLeDeviceListAdapter.clear();
                    mLeDeviceListAdapter.notifyDataSetChanged();
                    scanLeDevice(true);
                }else {
                    scanLeDevice(false);
                }
            }
        });
        listView = findViewById(R.id.list);
        listView.setAdapter(mLeDeviceListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (connected){
                    print();
                    return;
                }
                if (mScanning)
                {
                    scanLeDevice(false);
                }
                BluetoothDevice seletedDevice = mLeDeviceListAdapter.getDevice(i);
                String mDeviceAddress = seletedDevice.getAddress();
                dialog.setMessage("正在连接..." + mDeviceAddress);//
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                dialog.show();

               DrawerService.workThread.connectBt(mDeviceAddress);
            }
        });


    }

    private void print(){

        Observable.create(new ObservableOnSubscribe<Bitmap>() {

            @Override
            public void subscribe(ObservableEmitter<Bitmap> emitter) throws Exception {
                Bitmap bitmap = getBitmap( "https://img.snzfnm.com/20200929154129n4xk.jpg");
                int w = bitmap.getWidth();
                if (w>1600){
                    w=1600;
                }
                int finalW = w;
                Pos.POS_PrintPicture(bitmap, finalW, 0);
                emitter.onNext(bitmap);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        dialog.setMessage("发送中..." );
                        dialog.show();
                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        dialog.cancel();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(Activity8.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        dialog.cancel();
                    }

                    @Override
                    public void onComplete() {
                        dialog.cancel();
                    }
                });


    }


    private final class PrintImageTask extends AsyncTask<Bitmap, Void, Bitmap>{


        @Override
        protected void onCancelled()
        {
            super.onCancelled();
            dialog.dismiss();
        }

        @Override
        protected void onCancelled(Bitmap result)
        {
            super.onCancelled(result);
            dialog.dismiss();
        }

        @Override
        protected Bitmap doInBackground(Bitmap... bitmaps) {
            Bitmap bitmap = bitmaps[0];
            int w = bitmap.getWidth();
            if (w>1600){
                w=1600;
            }
            int finalW = w;
           Pos.POS_PrintPicture(bitmap, finalW, 0);

            return bitmap;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            if (dialog==null){
                dialog=new ProgressDialog(Activity8.this);
            }
            dialog.setMessage("正在打印..." );//
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

        }
        @Override
        protected void onPostExecute(Bitmap result)
        {
            super.onPostExecute(result);
            dialog.dismiss();

        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        dialog = new ProgressDialog(this);
        mHandler.sendEmptyMessageDelayed(6,2);

    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();
            BluetoothDevice device = intent
                    .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                if (device == null)
                    return;
              mLeDeviceListAdapter.addDevice(device);
               mLeDeviceListAdapter.notifyDataSetChanged();
            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED
                    .equals(action)) {
               mHandler.sendEmptyMessage(0);
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
                    .equals(action)) {
               mHandler.sendEmptyMessage(0);
            }

        }

    };
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (mScanning)
                    {
                        scan.setText("停止");

                    }
                    else
                    {
                      scan.setText("扫描");

                    }
                    break;
                case 6:
                    mHandler1 = new MHandler(Activity8.this);
                    DrawerService.addHandler(mHandler1);
                    Intent intent = new Intent(Activity8.this, DrawerService.class);
                    startService(intent);
                    break;
                case 7:
                    dialog.show();
                    break;
                case 8:
                    dialog.cancel();
                    break;
            }
        }
    };
    private class LeDeviceListAdapter extends BaseAdapter{
        private ArrayList<BluetoothDevice> mLeDevices=new ArrayList<BluetoothDevice>();

        public LeDeviceListAdapter()
        {
            super();


        }
        public void addDevice(BluetoothDevice device)
        {
            if (!mLeDevices.contains(device))
            {
                mLeDevices.add(device);
            }

        }
        public BluetoothDevice getDevice(int position)
        {
            return mLeDevices.get(position);
        }
        public void clear()
        {
            if (mLeDevices.size()>0){
                mLeDevices.clear();
            }

        }
        @Override
        public int getCount() {
            return mLeDevices.size();
        }

        @Override
        public Object getItem(int i) {
            return mLeDevices.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null)
            {
                view = LayoutInflater.from(Activity8.this).inflate(R.layout.listitem_device, viewGroup,false);
                viewHolder = new ViewHolder();
                viewHolder.pairStage = (TextView) view
                        .findViewById(R.id.pair_stage);
                viewHolder.deviceName = (TextView) view
                        .findViewById(R.id.device_name);
                view.setTag(viewHolder);
            }
            else
            {
                viewHolder = (ViewHolder) view.getTag();
            }

            BluetoothDevice device = mLeDevices.get(i);
            final String deviceName = device.getName();
            String deviceAddress = device.getAddress();
            viewHolder.deviceName.setText(deviceName+"("+deviceAddress+")");
            String pairStageString = "未配对";
//            if (pairedDevices.size() > 0) {
//                for (BluetoothDevice btdevice : pairedDevices) {{
//                    if (device.getAddress().equals(btdevice.getAddress())) {
//                        pairStageString = "已配对";
//
//                    }
//                }}
//            }
            viewHolder.pairStage.setText(pairStageString);
            return view;
        }
    }

    private static class ViewHolder
    {
        TextView deviceName;
        TextView pairStage;
    }
    private static final long SCAN_PERIOD = 12000;



    private void scanLeDevice(final boolean enable)
    {

        if (sdkIsLess431) {

            if (enable)
            {

                bluetoothAdapter.cancelDiscovery();
                bluetoothAdapter.startDiscovery();
                mScanning = true;
            }
            else
            {

                bluetoothAdapter.cancelDiscovery();
                mScanning = false;
            }

        }
        else {

            if (enable)
            {

                mHandler.postDelayed(new Runnable()
                {
                    //				@Override
                    public void run()
                    {
                        mScanning = false;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                            bluetoothAdapter.stopLeScan(null);
                        }
                        // invalidateOptionsMenu();
                        mHandler.sendEmptyMessage(0);
                    }
                },SCAN_PERIOD );

                mScanning = true;
                bluetoothAdapter.startLeScan((device, rssi, scanRecord) -> Activity8.this.runOnUiThread(() -> {

                    mLeDeviceListAdapter.addDevice(device);
                  mLeDeviceListAdapter.notifyDataSetChanged();
                }));
            }
            else
            {

                mScanning = false;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    bluetoothAdapter.stopLeScan(null);
                }
            }
        }
        mHandler.sendEmptyMessage(0);
        // invalidateOptionsMenu();

    }
    private class MHandler extends Handler{
        WeakReference<Activity8> mActivity;
        public MHandler(Activity8 activity){
            mActivity = new WeakReference<Activity8>(activity);
        }
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Global.MSG_WORKTHREAD_SEND_CONNECTBTRESULT: {
                    int result = msg.arg1;
                    if (result==1) {
                        Toast.makeText(Activity8.this, (result == 1) ? "连接成功" : "连接失败",
                                Toast.LENGTH_SHORT).show();
                //        mActivity.get().dialog.cancel();
                        connected = true;
                        for (int i=0;i<2;i++){
                            print();
                        }


                    }
                }
            }

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkBluetoothAndLocationPermission(){
        if((checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                || (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)){
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},1);
        }

    }

    private void show(String s){
        Toast.makeText(Activity8.this,s,Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        DrawerService.delHandler(mHandler1);
        mHandler1 = null;
        if (sdkIsLess431) {
            unregisterReceiver(broadcastReceiver);
        }
        if (DrawerService.workThread!=null) {
            DrawerService.workThread.disconnectBt();
            DrawerService.workThread.disconnectNet();
        }
    }
}
