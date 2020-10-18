package com.example.yishutansuo;

import android.app.Service;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import adapter.SensorAdapter;
import butterknife.BindView;

/**
 * 安卓传感器
 */
public class Activity5 extends BaseActivity {
    @BindView(R.id.lv)
    ListView lv;
    List<Sensor> sensors;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        init();
        sensors = new ArrayList<>();
        handleSensor();
        Acceralter();

    }

    private void handleSensor(){
        SensorManager sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        SensorAdapter sensorAdapter = new SensorAdapter(sensors,this);
        lv.setAdapter(sensorAdapter);

    }
    SensorEventListener sensorEventListener;
    private Vibrator vibrator;
    /**
     * 加速度传感器
     *
     */
    private void Acceralter(){
        vibrator = (Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
        SensorManager sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
         sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                int sensorType = sensorEvent.sensor.getType();
                switch (sensorType){
                    case Sensor.TYPE_ACCELEROMETER:
                        float [] values = sensorEvent.values;
                        Log.e("ymy",values[0]+"--"+values[1]+"--"+values[2]);
                        if (Math.abs(values[0])>12|Math.abs(values[1])>12|Math.abs(values[2])>12){
                            vibrator.vibrate(800);
                        }
                        break;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        sensorManager.registerListener(sensorEventListener,sensor,SensorManager.SENSOR_DELAY_UI);

    }

    @Override
    protected void onPause() {
        super.onPause();
        SensorManager sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensorManager.unregisterListener(sensorEventListener);
    }
}
