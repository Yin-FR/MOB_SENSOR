package fr.imt_atlantique.mysensor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    SensorManager mSensorManager;
    List<Sensor> deviceSensors;
    SensorEventListener accelerometerListener;

    RadioButton positionButton;

    private void initView() {
        positionButton = (RadioButton) findViewById(R.id.radioButton);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();


        accelerometerListener = new SensorEventListener(){
            public void onAccuracyChanged(Sensor sensor, int accuracy) {}
            public void onSensorChanged(SensorEvent event) {
                float[] values = event.values;
                positionButton.setText("x: "+values[0]+"\ty: "+values[1]);
                positionButton.setX(values[0]);
                positionButton.setY(values[1]);
            }
        };

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if(deviceSensors.size()>0){
            mSensorManager.registerListener(accelerometerListener, (Sensor) deviceSensors.get(0), SensorManager.SENSOR_DELAY_NORMAL);
        }else{
            Toast.makeText(getBaseContext(), "Error: No Accelerometer.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        if(deviceSensors.size()>0){
            mSensorManager.unregisterListener(accelerometerListener);
        }
        super.onPause();
    }
}