package com.example.englishstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DBHelper mDBHelper;
    private ArrayList<WordItem> mWorditem;
    private ProgressBar progressBar;
    private Button mbutton;

    //다른 Activity에서 변수 접근하기
    public static Context context_main;
    public int voca_index=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context_main=this;//다른 Activity에서 변수 접근하기

        //오늘의 달성률
        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(50);

        //버튼 누르면 다른 화면으로
        mbutton=(Button)findViewById(R.id.memorization1);
        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(),VocaLearn.class));
            }
        });

        setInit();

    }

    private void setInit(){
        mDBHelper=new DBHelper(this);
        mWorditem=new ArrayList<>();

        mDBHelper.InsertWord(1,1,0,"apple","사과");
        mDBHelper.InsertWord(1,2,0,"banana","바나나");
        mDBHelper.InsertWord(1,3,0,"grape","포도");
        mDBHelper.InsertWord(1,4,0,"cherry","체리");
        mDBHelper.InsertWord(1,5,0,"dog","강아지");
    }

}