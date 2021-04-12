package com.example.myapplication;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Parsers.util.Grammar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class CameraActivity extends AppCompatActivity {

    CircularProgressIndicator cir;
    ChipGroup cg;
    TextView gra;
    ExtendedFloatingActionButton fab;
    String str = "";
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

        MaterialButton retry = findViewById(R.id.retry);
        retry.setOnClickListener(view -> {
            Intent intent1 = getIntent();
            finish();
            startActivity(intent1);
        });


        fab.setOnClickListener(view -> {
            if (str.length() != 0) {
                try {
                    str = str.replaceAll("=", "->");
                    Grammar grammar = new Grammar(str);
                    startActivity(new Intent(getApplicationContext(), ResultActivity.class).putExtra("PARSER", but_id).putExtra("GRAMMAR", str),
                            ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                } catch (Exception e) {
                    Snackbar.make(findViewById(R.id.parent), "Invalid Input", Snackbar.LENGTH_INDEFINITE)
                            .setAction("RETRY", view1 -> {
                                Intent intent1 = getIntent();
                                finish();
                                startActivity(intent1);
                            })
                            .setActionTextColor(Color.parseColor("#F9AA33"))
                            .setBackgroundTint(Color.parseColor("#344955"))
                            .show();
                }
            } else
                Snackbar.make(findViewById(R.id.parent), "Grammar can not be empty", Snackbar.LENGTH_INDEFINITE)
                        .setAction("RETRY", view1 -> {
                            Intent intent1 = getIntent();
                            finish();
                            startActivity(intent1);
                        })
                        .setActionTextColor(Color.parseColor("#F9AA33"))
                        .setBackgroundTint(Color.parseColor("#344955"))
                        .show();
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        String postUrl = "https://vision.googleapis.com/v1/images:annotate?key=" + getString(R.string.key);
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = null;
        try {
            postData = new JSONObject("{'requests':[{'image':{'content':'" + encoded + "'},'features':[{'type':'DOCUMENT_TEXT_DETECTION'}]}]}");
        } catch (JSONException e) {
            e.printStackTrace();
            AlertDialog ad = adb.create();
            ad.setTitle("Error");
            ad.setMessage("Error Converting String to JSON");
            ad.show();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                postUrl,
                postData,
                response -> {

                    Log.e("API", response.toString());
                    try {
                        JSONArray arr = response.getJSONArray("responses");
                        JSONObject f = arr.getJSONObject(0);
                        JSONArray main = f.getJSONArray("textAnnotations");

                        str = main.getJSONObject(0).getString("description");

                        Log.e("API", "Processed");
                        Log.e("API", str);
                        cir.setVisibility(View.INVISIBLE);
                        gra.setText(str);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        AlertDialog ad = adb.create();
                        ad.setTitle("Error");
                        ad.setMessage("Error Parsing Data");
                        ad.show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    AlertDialog ad = adb.create();
                    ad.setTitle("Error");
                    ad.setMessage("Error calling API");
                    ad.show();
                });

        requestQueue.add(jsonObjectRequest);
        Log.e("API", "Response Received");


    }
}