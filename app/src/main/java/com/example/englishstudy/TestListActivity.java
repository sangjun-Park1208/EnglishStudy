package com.example.englishstudy;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TestListActivity extends AppCompatActivity {
    private  DBHelper mDBHelper;
    private ArrayList<WordItem> mWordItem;
    private TestListAdapter testListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);

        mDBHelper = new DBHelper(this);
        mWordItem = new ArrayList<>();
        //DB 내용 불러오기
        mWordItem = mDBHelper.getWordList();
        if(testListAdapter == null){
            testListAdapter = new TestListAdapter(mWordItem,this);
        }

        //RecyclerView 연결
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(testListAdapter);




    }
}
