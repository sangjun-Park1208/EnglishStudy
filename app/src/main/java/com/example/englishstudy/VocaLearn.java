package com.example.englishstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class VocaLearn extends AppCompatActivity{

    private TextView mword;
    private TextView mmeaning;
    private ArrayList<WordItem> mWordItem;
    private DBHelper mDBHelper;
    private Button bt1;
    private Button bt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voca_learn);

        mDBHelper=new DBHelper(this);
        mWordItem=new ArrayList<>();

        mWordItem=mDBHelper.getWordList();

        mword=(TextView) findViewById(R.id.word);
        mmeaning=(TextView) findViewById(R.id.meaning);
        mword.setText(mWordItem.get(0).getWord().toString());
        mmeaning.setText(mWordItem.get(0).getMeaning().toString());

        bt1=(Button) findViewById(R.id.known);
        bt1.setText("O");
        bt2=(Button) findViewById(R.id.unknown);
        bt2.setText("x");


    }



}