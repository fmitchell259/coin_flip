package com.example.animation_test;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up my Imageview to contain animation.xml file.
        // Set up the initial image resource.
        // Create AnimationDrawable object and asign Imageview to .getDrawable().
        // Finally create instance of Button and attach to transparent coin button.

        final ImageView coin = (ImageView) findViewById(R.id.coin_animation);

        coin.setImageResource(R.drawable.coin_toss);

        final AnimationDrawable flipping_coin = (AnimationDrawable) coin.getDrawable();

        final Button start  = (Button) findViewById(R.id.button);

        // Set listener for button press.

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Set instances for my result and random index.

                final int[] flip_list = new int[50000];
                final int rand_ind, rand_time;

                // Create new Random object to generate index.

                final Random ran_ind_gen = new Random();
                rand_ind = ran_ind_gen.nextInt(50000);
                rand_time = ThreadLocalRandom.current().nextInt(5000,10000);

                // Create new handler object to handle the flipping animation time.

                final Handler handler = new Handler();

                // Set up list of zero's and one's.

                for(int c=0; c < 25000; c++) {
                    flip_list[c] = 0;
                }
                for(int c=25000; c <50000; c++) {
                    flip_list[c] = 1;
                }

                // Start flipping.

                flipping_coin.start();

                // Set handler object to wait 5000 milliseconds and stop animation.

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        flipping_coin.stop();

                        if(flip_list[rand_ind] == 0) {
                            coin.setImageResource(R.drawable.heads_result);
                        }else {
                            coin.setImageResource(R.drawable.tails_result);
                        }
                    }
                }, rand_time);

            }

        });
    }
}
