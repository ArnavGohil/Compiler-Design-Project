package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    int parser ;
    String grammar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle bundle = getIntent().getExtras();
        parser = bundle.getInt("PARSER");
        grammar = bundle.getString("GRAMMAR");

        TextView tt = findViewById(R.id.tt);
        String s = "";
        switch (parser)
        {
            case 1 : s = "LL(1)" ;


                break;
            case 2 : s = "LR(0)" ;


                break;
            case 3 : s = "SLR(1)" ;


                break;
            case 4 : s = "LALR(1)" ;


                break;
            case 5 : s = "CLR(1)" ;


                break;
        }
        tt.setText(s);

    }



}