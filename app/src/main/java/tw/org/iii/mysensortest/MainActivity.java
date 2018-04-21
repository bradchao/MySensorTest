package tw.org.iii.mysensortest;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private Sensor sensor;
    private MyListener listener;
    private TextView vx, vy, vz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vx = findViewById(R.id.vx);
        vy = findViewById(R.id.vy);
        vz = findViewById(R.id.vz);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        List<Sensor> deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor : deviceSensors){
            String name = sensor.getName();
            if (Build.VERSION.SDK_INT>=20) {
                String type = sensor.getStringType();
                Log.v("brad", name + ":" + type);
            }else{
                Log.v("brad", name);
            }

        }

        sensor = sensorManager.getDefaultSensor(
                Sensor.TYPE_PROXIMITY);



    }

    @Override
    protected void onResume() {
        super.onResume();

        listener = new MyListener();
        sensorManager.registerListener(listener, sensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (listener!=null) {
            sensorManager.unregisterListener(listener);
        }
    }

    private class MyListener implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;
            float x = values[0];
            float y = values[1];
            float z = values[2];

            vx.setText("X = " + (int)x);
            vy.setText("Y = " + (int)y);
            vz.setText("Z = " + (int)z);

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }


}
