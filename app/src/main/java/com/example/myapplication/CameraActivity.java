package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.ChipGroup;
import com.google.android.material.progressindicator.CircularProgressIndicator;

public class CameraActivity extends AppCompatActivity {

    CircularProgressIndicator cir ;
    ChipGroup cg;
    TextView gra ;
    String str =" E = aAb | c \n" +
                " F = aa \n" +
                " T = b " ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,0);
        setContentView(R.layout.activity_camera);

        cg = findViewById(R.id.chip_group);
        cir = findViewById(R.id.cir);
        gra = findViewById(R.id.grammar);




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");

        new Handler().postDelayed(() -> {
            cir.setVisibility(View.INVISIBLE);
            gra.setText(str);
        },2000);

    }
}