package com.example.animation_test;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity implements  SensorEventListener {

    Sensor my_sensor;
    SensorManager s_m;

    void animate() {


        final ImageView coin = (ImageView) findViewById(R.id.coin_animation);
        final ImageView result = (ImageView) findViewById(R.id.result);

        coin.setImageResource(R.drawable.coin_toss);

        final AnimationDrawable flipping_coin = (AnimationDrawable) coin.getDrawable();

        coin.setAlpha(255);
        result.setAlpha(0);

        // Set instances for my result and random index.

        final int[] flip_list = new int[5000];
        final int rand_ind, rand_time;

        // Create new Random object to generate index.

        final Random ran_ind_gen = new Random();
        rand_ind = ran_ind_gen.nextInt(5000);
        rand_time = ThreadLocalRandom.current().nextInt(10000, 15000);

        // Create new handler object to handle the flipping animation time.

        final Handler handler = new Handler();

        // Set up list of zero's and one's.

        for (int c = 0; c < 2500; c++) {
            flip_list[c] = 0;
        }
        for (int c = 2500; c < 5000; c++) {
            flip_list[c] = 1;
        }
        // Start flipping.

        System.out.println("Hi We are in the console00000000000000");

        flipping_coin.start();

        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");

        // Set handler object to wait 5000 milliseconds and stop animation.

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                flipping_coin.stop();

                if (flip_list[rand_ind] == 0) {
                    coin.setAlpha(0);
                    result.setAlpha(255);
                    result.setImageResource(R.drawable.heads_result);
                } else {
                    coin.setAlpha(0);
                    result.setAlpha(255);
                    result.setImageResource(R.drawable.tails_result);
                }
            }
        }, rand_time);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        final MediaPlayer coin_sound = MediaPlayer.create(getApplicationContext(), R.raw.coin_sound);

        s_m = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Accellormoter sensor.

        my_sensor = s_m.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Register out sensor listener.

        s_m.registerListener(this, my_sensor, SensorManager.SENSOR_DELAY_NORMAL);

        final Button start = (Button) findViewById(R.id.button);

        // Set listener for button press.

        System.out.println("Z: " + event.values[2]);
        System.out.println("Y: " + event.values[1]);
        System.out.println("X: " + event.values[0]);

        if (event.values[2] < 2.0) {
            //System.out.println("\nYO YO YO WE DETECTED THE TILT!\n");
            coin_sound.setVolume(50, 50);
            coin_sound.start();
            animate();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used.
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MediaPlayer tune = MediaPlayer.create(getApplicationContext(), R.raw.mat_girl);

        // Set sensor to detect tilt.

        s_m = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Accellormoter sensor.

        my_sensor = s_m.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Register out sensor listener.

        s_m.registerListener(this, my_sensor, SensorManager.SENSOR_STATUS_ACCURACY_HIGH);

        final Button start = (Button) findViewById(R.id.button);

        // Set listener for button press (secret tune).

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            animate();

            }
        });
    }
}
