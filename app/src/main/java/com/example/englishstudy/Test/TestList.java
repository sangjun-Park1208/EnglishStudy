package com.example.englishstudy.Test;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
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
    private Context mContext;
    private ArrayList<WordItem> mWordItem;
    private ArrayList<Stage_Item> mDayList;
    private TestAdapter testAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mDBHelper = new DBHelper(this);
        mWordItem = new ArrayList<>();
        mDayList = new ArrayList<>();

        //DB 내용 불러오기
        mWordItem = mDBHelper.getWordList();

        //Complete or Retry 여부 체크
        int index;
        int repeat;
        boolean isMark;


        //총 단어 : 900개, Day 개수 : 30개
        for (int day = 0; day < 30; day++) {
            isMark = false;
            index = day * 30;
            repeat = day * 30 + 30;
            int correct=0;
            int wrong=0;

            //index~repeat 범위에 Mark(틀렸거나, 사용자가 북마크한 것)가 있는지 탐색
            for (; index < repeat; index++) {
                if (1 == mWordItem.get(index).getIsMark()) {
                    wrong++;
                    isMark = true;
                }
                else if(0==mWordItem.get(index).getIsMark()){
                    correct++;
                }
            }
            //Mark 가 적어도 하나 있을 경우 Complete X : Challenge 표시
            if (isMark == true) {
                mDayList.add(new Stage_Item("Stage" + Integer.toString(day + 1), "Challenge",correct,wrong));

//                Log.d("TEST","Stage:" + Integer.toString(day + 1));
//                Log.d("TEST","running:Challenge");
//                Log.d("TEST","correct:"+correct);
//                Log.d("TEST","correct:"+wrong);
            }
            //Mark 가 없을 경우 모두 정답 : Complete 표시
            else {
                mDayList.add(new Stage_Item("Stage" + Integer.toString(day + 1), "Complete",correct,wrong));
//
//                Log.d("TEST","Stage:" + Integer.toString(day + 1));
//                Log.d("TEST","running:Complete");
//                Log.d("TEST","correct:"+correct);
//                Log.d("TEST","correct:"+wrong);
            }
        }

        //설정한 mDayList 를 Adapter 에 대입
        if (testAdapter == null) {
            testAdapter = new TestAdapter(mDayList, this);
        }


        //RecyclerView 연결
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(testAdapter);


        //RecyclerView 아이템이 선택되었을 때 처리
        testAdapter.setOnItemClickListener(
                new TestAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int curPos, Context mContext) {

                        Log.d("TEST","Stage"+(curPos+1)+"클릭");

                        //AlertDialog 를 이용한 팝업 창
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        String[] strChoiceItems = {"O", "X"};
                        builder.setTitle("게임을 시작하시겠습니까?");
                        builder.setItems(strChoiceItems, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int position) {
                                //O가 선택되었을 때
                                if (position == 0) {
                                    //TestActivity 로 이동
                                    //이때 현재 선택된 Stage 값을 Intent 로 전달
                                    Intent intent = new Intent(view.getContext(), TestActivity.class);
                                    intent.putExtra("Stage", curPos);

                                    Log.d("TEST","Stage"+(curPos+1)+"로 이동");

                                    startActivity(intent);
                                }
                                //X가 선택되었을 때
                                else if (position == 1) {
                                    //현상 유지
                                    Log.d("TEST","Stage"+(curPos+1)+"로 이동 안함");
                                }
                            }
                        });
                        builder.create();
                        builder.show();
                    }


                }
        );

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

        //Intent 로 stage, 정답 개수, 오답 개수 전달받음
        String stage = "Stage"+intent.getIntExtra("stage",0)+1;
        String running;
        int correct =  intent.getIntExtra("correct",0);
        int wrong =  intent.getIntExtra("wrong",0);

        Log.d("TEST","TestList onNewIntent");
        Log.d("TEST","Test 결과: 정답"+correct+" 오답:"+wrong+" 전달 완료");

        //모두 정답일 경우 Complete / 아닐 경우 : Challenge
        if(correct==30){
            running = "Complete";

            //stage, running, 정답 개수, 오답 개수
            //해당 Stage Update
            Stage_Item stageItem = new Stage_Item(stage,running,correct,wrong);
            mDayList.set(intent.getIntExtra("stage",0),stageItem);

            //수정한 결과 RecyclerView 에 반영
            if (testAdapter == null) {
                testAdapter = new TestAdapter(mDayList, this);
            }
            recyclerView.setAdapter(testAdapter);

            Log.d("TEST","해당 Stage Test 결과 RecyclerView 에 반영 완료");

        }
        else{
            running = "Challenge";
        }


    }
}
