package com.example.android.bwowtask;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView sleepingTime,heartRate,trainingTime,footSteps;
    Sensor mStepCounter;
    SensorManager sensorManager;
    boolean isCounterSensorPresent;
    int stepCount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        footSteps=findViewById(R.id.footSteps);
        sensorManager= (SensorManager) getSystemService(SENSOR_SERVICE);
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null){
            mStepCounter=sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isCounterSensorPresent=true;
        }else {
            footSteps.setText("Counter Sensor is not present");
            isCounterSensorPresent = false;
        }
        sleepingTime=findViewById(R.id.sleepingTime);
        heartRate=findViewById(R.id.heartRate);
        trainingTime=findViewById(R.id.trainingTime);
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.jsonbin.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api=retrofit.create(Api.class);
        Call<JSONResponse> call =api.getData();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                if(!response.isSuccessful()){
                    sleepingTime.setText("Code: "+response.code());
                    heartRate.setText("Code: "+response.code());
                    trainingTime.setText("Code: "+response.code());
                    return;
                }
                if(response.body().getData()!=null){
                    heartRate.setText(response.body().getData().getHeartRate());
                    sleepingTime.setText(response.body().getData().getSleepTime());
                    trainingTime.setText(response.body().getData().getTrainingTime());
                }
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
               sleepingTime.setText(t.getMessage());
               heartRate.setText(t.getMessage());
               trainingTime.setText(t.getMessage());
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null){
            sensorManager.unregisterListener(this,mStepCounter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null){
            sensorManager.registerListener(this,mStepCounter,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor == mStepCounter){
            stepCount=(int) sensorEvent.values[0];
            footSteps.setText(String.valueOf(stepCount));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}