package com.example.englishstudy.Test;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishstudy.R;
import com.example.englishstudy.global.DBHelper;
import com.example.englishstudy.global.WordItem;

import java.util.ArrayList;

public class TestList extends AppCompatActivity {
    private DBHelper mDBHelper;
    private ArrayList<WordItem> mWordItem;
    private TestAdapter testAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);

        mDBHelper = new DBHelper(this);
        mWordItem = new ArrayList<>();
        //DB 내용 불러오기
        mWordItem = mDBHelper.getWordList();
        if(testAdapter == null){
            testAdapter = new TestAdapter(mWordItem,this);
        }

        //RecyclerView 연결
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(testAdapter);




    }
}
