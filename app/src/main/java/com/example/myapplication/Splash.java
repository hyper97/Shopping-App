package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class Splash extends AppCompatActivity {

    private static int SPLASH_TIME = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do any action here. Now we are moving to next page
                Intent intent = new Intent(Splash.this, Main_Login.class);
                startActivity(intent);

                finish();
            }
        }, SPLASH_TIME);
    }
}
