package com.example.englishstudy.Test;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishstudy.R;
import com.example.englishstudy.global.DBHelper;
import com.example.englishstudy.global.Stage_Item;
import com.example.englishstudy.global.WordItem;

import java.util.ArrayList;

public class TestList extends AppCompatActivity {
    private DBHelper mDBHelper;
    private ArrayList<WordItem> mWordItem;
    private ArrayList<Stage_Item> mDayList;
    private TestAdapter testAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);

        mDBHelper = new DBHelper(this);
        mWordItem = new ArrayList<>();
        mDayList = new ArrayList<>();

        //DB 내용 불러오기
        mWordItem = mDBHelper.getWordList();

        int index;
        int repeat;
        boolean isMark;
        //Complete or Retry 여부 체크
        for(int day=0; day< 30 ; day++){
            isMark = false;
            index= day*30;
            repeat = day*30+30;

            for(; index<repeat;index++) {
                if (1 == mWordItem.get(index).getIsMark()) {
                    isMark = true;
                }
            }

            if(isMark==true){
                mDayList.add(new Stage_Item("Stage" + Integer.toString(day+1), "Challenge"));

            }
            else{
                mDayList.add(new Stage_Item("Stage" +Integer.toString(day+1), "Complete"));
            }
        }

        if(testAdapter==null){
            testAdapter = new TestAdapter(mDayList,this);
        }


        //RecyclerView 연결
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(testAdapter);




    }
}
