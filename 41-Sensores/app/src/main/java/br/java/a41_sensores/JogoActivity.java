package br.java.a41_sensores;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class JogoActivity extends Activity
         implements SensorEventListener, Callback {

    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private SensorManager mSensorManager;
    private ThreadJogo mThreadJogo;
    private Bolinha mBolinha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mSurfaceView = new SurfaceView(this);
        mSurfaceView.setKeepScreenOn(true);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        setContentView(mSurfaceView);
        mBolinha = new Bolinha(this);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Sensor accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, accelerometer,
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mBolinha.setTamanhoTela(width, height);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        mThreadJogo = new ThreadJogo(this, mBolinha, holder);
    }

    public void surfaceDestroyed(SurfaceHolder holder) {

        try {
            mBolinha.setTamanhoTela(0,0 );
            mThreadJogo.parar();

        } finally {
            mThreadJogo = null;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        mBolinha.setAceleracao(sensorEvent.values[0], sensorEvent.values[1]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}