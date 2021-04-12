package com.example.myapplication;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.myapplication.Parsers.lr0.LR0Parser;
import com.example.myapplication.Parsers.lr1.LR1Parser;
import com.example.myapplication.Parsers.util.Grammar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    int parser = 2;
    String s = "";
    String grammarText;
    String aug = "", first = "", follow = "", canon = "", goat = "", act = "";
    LR0Parser lr0Parser;
    LR1Parser lr1Parser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle bundle = getIntent().getExtras();
        parser = bundle.getInt("PARSER");
        grammarText = bundle.getString("GRAMMAR");

        TextView tt = findViewById(R.id.tt);
        TextView area = findViewById(R.id.textArea);
        area.setMovementMethod(new ScrollingMovementMethod());


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


        TabLayout tabLayout = findViewById(R.id.tabLayout);
        area.setText(aug);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int i = tab.getPosition();
                switch (i) {
                    case 0:
                        area.setText(aug);
                        break;
                    case 1:
                        area.setText(first);
                        break;
                    case 2:
                        area.setText(follow);
                        break;
                    case 3:
                        area.setText(canon);
                        break;
                    case 4:
                        area.setText(goat);
                        break;
                    case 5:
                        area.setText(act);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        TextInputLayout lay = findViewById(R.id.inpSTR);
        TextInputEditText txt = findViewById(R.id.inp);
        txt.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence str, int start, int before, int count) {
                if (count == 0) {
                    lay.setError(null);
                    lay.setHintTextColor(getColorStateList(R.color.colorPrimary));
                    lay.setBoxStrokeColor(getColor(R.color.colorPrimary));
                    lay.setHelperText(null);
                    return;
                }
                ArrayList<String> words = new ArrayList<>();
                String[] split = str.toString().trim().split("\\s+");
                for (String s : split) {
                    words.add(s);
                }
                if (parser == 1 || parser == 2) {
                    boolean accept = lr0Parser.accept(words);
                    if (accept) {
                        lay.setError(null);
                        lay.setHintTextColor(getColorStateList(R.color.okay));
                        lay.setBoxStrokeColor(getColor(R.color.okay));
                        lay.setHelperText("Accepted");
                        lay.setHelperTextColor(getColorStateList(R.color.okay));
                    } else {
                        lay.setError("Not Accepted");
                    }
                } else {
                    boolean accept = lr1Parser.accept(words);
                    if (accept) {
                        lay.setError(null);
                        lay.setHintTextColor(getColorStateList(R.color.okay));
                        lay.setBoxStrokeColor(getColor(R.color.okay));
                        lay.setHelperText("Accepted");
                        lay.setHelperTextColor(getColorStateList(R.color.okay));
                    } else {
                        lay.setError("Not Accepted");
                    }
                }
            }
        });

        String toWrite = s + "\n\n"
                + "Augmented Grammar - \n" + aug + "\n\n"
                + "First Set - \n" + first + "\n\n"
                + "Follow Set - \n" + follow + "\n\n"
                + canon + "\n\n"
                + goat + "\n\n"
                + act + "\n\n";

        File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File gpxfile = new File(root, "parser.txt");
        Log.e("File", gpxfile.getAbsolutePath());
        FileWriter writer = null;
        try {

            writer = new FileWriter(gpxfile);
            writer.append(toWrite);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ExtendedFloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/*");
            sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri uri = FileProvider.getUriForFile(ResultActivity.this, getApplicationContext().getPackageName() + ".provider", gpxfile);
            sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
            startActivity(Intent.createChooser(sharingIntent, "Share file with"));
        });

    }

    void cannotParse() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        AlertDialog ad = adb.create();
        ad.setTitle("ALERT");
        ad.setMessage("The grammar can not be parsed. Choose a different parser or grammar");
        ad.show();
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(ResultActivity.this);
        Intent intent = new Intent(ResultActivity.this, CameraActivity.class);
        startActivity(intent, options.toBundle());
        finish();
    }
}