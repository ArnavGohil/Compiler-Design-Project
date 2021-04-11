package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.Parsers.lr0.LR0Parser;
import com.example.myapplication.Parsers.util.Grammar;
import com.google.android.material.textfield.TextInputEditText;

public class ResultActivity extends AppCompatActivity {

    int parser ;
    String s = "";
    String grammar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle bundle = getIntent().getExtras();
        parser = bundle.getInt("PARSER");
        grammar = bundle.getString("GRAMMAR");

        TextView tt = findViewById(R.id.tt);

        TextInputEditText txt = findViewById(R.id.inpSTR);
        // TODO Here

        //TODO - Works well
        String grammarText =  "S -> A\n" +
                "S -> B\n" +
                "A -> b\n" +
                "B -> a" ;
        Grammar grammar = new Grammar(grammarText);
        LR0Parser lr0Parser = new LR0Parser(grammar);
        //area.setText(lr0Parser.getGrammar()+"");

        switch (parser)
        {
            case 1 : s = "LR(0)" ;
                tt.setText(s);
                break;
            case 2 : s = "SLR(1)" ;
                tt.setText(s);
                //SLRparser();
                break;
            case 3 : s = "LALR(1)" ;
                tt.setText(s);
                //LALRparser();
                break;
            case 4 : s = "CLR(1)" ;
                tt.setText(s);
                //CLRparser();
                break;
        }
    }



}