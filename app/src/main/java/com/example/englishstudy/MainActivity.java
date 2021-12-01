package com.example.englishstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.englishstudy.Memorization.MemorizationList;
import com.example.englishstudy.Review.ReviewActivity;
import com.example.englishstudy.Review.ReviewList;
import com.example.englishstudy.Test.TestList;
import com.example.englishstudy.global.DBHelper;
import com.example.englishstudy.global.WordItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DBHelper mDBHelper;
    private ArrayList<WordItem> mWorditem;
    private ProgressBar progressBar;
    private Button memorization1;
    private Button memorization2;
    private Button test1;
    private Button test2;
    private Button review1;
    private Button review2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setInit();//DB 세팅

        //오늘의 달성률
        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(50);

        //test 화면 넘어가기
        test1 = findViewById(R.id.test1);
        test2 = findViewById(R.id.test2);

        test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 startActivity(new Intent(view.getContext(), TestList.class));
            }
        });
        test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), TestList.class));
            }
        });

        review1=findViewById(R.id.review1);
        review2=findViewById(R.id.review2);

        review1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ReviewList.class));
            }
        });
        review2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ReviewList.class));
            }
        });

        //버튼 누르면 다른 화면으로
        memorization1=findViewById(R.id.memorization1);
        memorization1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//암기하기 눌렀을때
                startActivity(new Intent(view.getContext(), MemorizationList.class));
            }
        });
        memorization2=findViewById(R.id.memorization2);
        memorization2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//암기하기 눌렀을때
                startActivity(new Intent(view.getContext(), MemorizationList.class));
            }
        });
    }


    private void setInit() {
        mDBHelper = new DBHelper(this);
        mWorditem = new ArrayList<>();
        mWorditem=mDBHelper.getWordList();//DB 아이템들 끌고오기

        if (mWorditem.size() != 0) return;//단어가 db에 이미 있으면 setInit 종료

        //단어 끌고 오기
        VocaList vocaList = new VocaList();
        for (int i = 0; i < 900; i++) {
            mDBHelper.InsertWord(1, 1, 1, vocaList.getWord(i%90), vocaList.getMeaning(i%90));//isMark,word,meaning 순으로
        }

    }

}