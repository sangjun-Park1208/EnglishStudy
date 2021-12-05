package com.example.englishstudy.Test;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.InputConnection;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishstudy.R;
import com.example.englishstudy.global.DBHelper;
import com.example.englishstudy.global.Stage_Item;
import com.example.englishstudy.global.Stage_Item;
import com.example.englishstudy.global.WordItem;

import java.util.ArrayList;

public class TestList extends AppCompatActivity {
    private DBHelper mDBHelper;
    private Context mContext;
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

        //Complete or Retry 여부 체크
        int index;
        int repeat;
        boolean isMark;

        //총 단어 : 900개, Day 개수 : 30개
        for (int day = 0; day < 30; day++) {
            isMark = false;
            index = day * 30;
            repeat = day * 30 + 30;

            //index~repeat 범위에 Mark(틀렸거나, 사용자가 북마크한 것)가 있는지 탐색
            for (; index < repeat; index++) {
                if (1 == mWordItem.get(index).getIsMark()) {
                    isMark = true;
                }
            }
            //Mark 가 적어도 하나 있을 경우 Complete X : Challenge 표시
            if (isMark == true) {
                mDayList.add(new Stage_Item("Stage" + Integer.toString(day + 1), "Challenge",0,0));

            }
            //Mark 가 없을 경우 모두 정답 : Complete 표시
            else {
                mDayList.add(new Stage_Item("Stage" + Integer.toString(day + 1), "Complete",0,0));
            }
        }

        //설정한 mDayList 를 Adapter 에 대입
        if (testAdapter == null) {
            testAdapter = new TestAdapter(mDayList, this);
        }


        //RecyclerView 연결
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(testAdapter);


        //RecyclerView 아이템이 선택되었을 때 처리
        testAdapter.setOnItemClickListener(
                new TestAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int curPos, Context mContext) {

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
                                    startActivity(intent);
                                }
                                //X가 선택되었을 때
                                else if (position == 1) {
                                    //현상 유지
                                }
                            }
                        });
                        builder.create();
                        builder.show();
                    }


                }
        );

//        bt_select.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Stage_Item stage_item = mDayList.get(clickPosition);
//            }
//        });
//
//        bt_down.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                clickPosition++;
//            }
//        });
//
//        bt_up.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Stage_Item stage_item = mDayList.get(clickPosition);
//                stage_item.;
//                clickPosition--;
//            }
//        });


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }
//    public void simulateSelectPress(View v){
//
//        InputConnection inputConnection = new BaseInputConnection(v, true);
//        KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_CALL);
//        inputConnection.sendKeyEvent(keyEvent);
//
//
//    }
//
//
//    public void simulateUpPress(View v){
//
//        InputConnection inputConnection = new BaseInputConnection(v, true);
//        KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_UP);
//        inputConnection.sendKeyEvent(keyEvent);
//        KeyEvent keyEvent1 = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DPAD_UP);
//        inputConnection.sendKeyEvent(keyEvent1);
//
//    }
//
//    public void simulateDownPress(View v){
//
//        InputConnection inputConnection = new BaseInputConnection(v, true);
//        KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_DOWN);
//        inputConnection.sendKeyEvent(keyEvent);
//        KeyEvent keyEvent1 = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DPAD_DOWN);
//        inputConnection.sendKeyEvent(keyEvent1);
//
//    }



}
