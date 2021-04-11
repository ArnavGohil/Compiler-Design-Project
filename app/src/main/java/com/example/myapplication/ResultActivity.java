package com.example.myapplication;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Parsers.lr0.LR0Parser;
import com.example.myapplication.Parsers.lr1.LR1Parser;
import com.example.myapplication.Parsers.util.Grammar;
import com.google.android.material.textfield.TextInputEditText;

public class ResultActivity extends AppCompatActivity {

    int parser = 1;
    String s = "";
    String grammar;
    String aug = "", first = "", follow = "", canon = "", goat = "", act = "";
    LR0Parser lr0Parser;
    LR1Parser lr1Parser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle bundle = getIntent().getExtras();
        //parser = bundle.getInt("PARSER");
        //grammar = bundle.getString("GRAMMAR");
//        grammar = grammar.replaceAll("=","->");

        TextView tt = findViewById(R.id.tt);
        TextView area = findViewById(R.id.textArea);

        TextInputEditText txt = findViewById(R.id.inp);
        // TODO Here

        //TODO - Works well
        String grammarText = "S -> A\n" +
                "S -> B\n" +
                "A -> b\n" +
                "B -> a";
        Grammar grammar = new Grammar(grammarText);

        area.setText("\"\"\"\"\"\"\"");

        switch (parser) {
            case 1:
                s = "LR(0)";
                tt.setText(s);
                lr0Parser = new LR0Parser(grammar);

                if (!lr0Parser.parserLR0())
                    cannotParse();

                aug = lr0Parser.getGrammar() + "";

                for (String s : lr0Parser.getGrammar().getFirstSets().keySet()) {
                    first += s + " : " + lr0Parser.getGrammar().getFirstSets().get(s) + "\n";
                }

                for (String s : lr0Parser.getGrammar().getFallowSets().keySet()) {
                    follow += s + " : " + lr0Parser.getGrammar().getFallowSets().get(s) + "\n";
                }

                canon = lr0Parser.canonicalCollectionStr();

                goat = lr0Parser.goToTableStr();

                act = lr0Parser.actionTableStr();

                break;


            case 2:
                s = "SLR(1)";
                tt.setText(s);
                lr0Parser = new LR0Parser(grammar);

                if (!lr0Parser.parserSLR1())
                    cannotParse();

                aug = lr0Parser.getGrammar() + "";

                for (String s : lr0Parser.getGrammar().getFirstSets().keySet()) {
                    first += s + " : " + lr0Parser.getGrammar().getFirstSets().get(s) + "\n";
                }

                for (String s : lr0Parser.getGrammar().getFallowSets().keySet()) {
                    follow += s + " : " + lr0Parser.getGrammar().getFallowSets().get(s) + "\n";
                }

                canon = lr0Parser.canonicalCollectionStr();

                goat = lr0Parser.goToTableStr();

                act = lr0Parser.actionTableStr();

                break;


            case 3:
                s = "LALR(1)";
                tt.setText(s);
                lr1Parser = new LR1Parser(grammar);

                if (!lr1Parser.parseLALR1())
                    cannotParse();

                aug = lr1Parser.getGrammar() + "";

                for (String s : lr1Parser.getGrammar().getFirstSets().keySet()) {
                    first += s + " : " + lr1Parser.getGrammar().getFirstSets().get(s) + "\n";
                }

                for (String s : lr1Parser.getGrammar().getFallowSets().keySet()) {
                    follow += s + " : " + lr1Parser.getGrammar().getFallowSets().get(s) + "\n";
                }

                canon = lr1Parser.canonicalCollectionStr();

                goat = lr1Parser.goToTableStr();

                act = lr1Parser.actionTableStr();

                break;


            case 4:
                s = "CLR(1)";
                tt.setText(s);
                lr1Parser = new LR1Parser(grammar);

                if (!lr1Parser.parseLALR1())
                    cannotParse();

                aug = lr1Parser.getGrammar() + "";

                for (String s : lr1Parser.getGrammar().getFirstSets().keySet()) {
                    first += s + " : " + lr1Parser.getGrammar().getFirstSets().get(s) + "\n";
                }

                for (String s : lr1Parser.getGrammar().getFallowSets().keySet()) {
                    follow += s + " : " + lr1Parser.getGrammar().getFallowSets().get(s) + "\n";
                }

                canon = lr1Parser.canonicalCollectionStr();

                goat = lr1Parser.goToTableStr();

                act = lr1Parser.actionTableStr();

                break;
        }









    }

    void cannotParse() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        AlertDialog ad = adb.create();
        ad.setTitle("ALERT");
        ad.setMessage("The grammar can not be parsed. Choose a different parser or grammar");
        ad.show();
        finish();
    }


}