package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;

public class CameraActivity extends AppCompatActivity {

    CircularProgressIndicator cir;
    ChipGroup cg;
    TextView gra;
    ExtendedFloatingActionButton fab;
    String str = " E = aAb | c \n" +
            " F = aa \n" +
            " T = b ";
    int but_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
        setContentView(R.layout.activity_camera);

        cg = findViewById(R.id.chip_group);
        cir = findViewById(R.id.cir);
        gra = findViewById(R.id.grammar);
        fab = findViewById(R.id.fab);

        cg.setOnCheckedChangeListener((group, checkedId) -> {
            but_id = checkedId;
            fab.setVisibility(View.VISIBLE);
        });

        fab.setOnClickListener(view -> {
            Toast.makeText(this, "" + but_id, Toast.LENGTH_SHORT).show();
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");

        new Handler().postDelayed(() -> {
            cir.setVisibility(View.INVISIBLE);
            gra.setText(str);
        }, 2000);

    }
}