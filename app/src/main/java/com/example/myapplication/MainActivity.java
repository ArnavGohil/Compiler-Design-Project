package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(() -> {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
            Intent intent = new Intent(MainActivity.this, CameraActivity.class);
            startActivity(intent, options.toBundle());
        },1000);

    }
}