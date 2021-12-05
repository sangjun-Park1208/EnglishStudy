package com.example.englishstudy.Memorization;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.englishstudy.R;
import com.example.englishstudy.Review.ReviewActivity;
import com.example.englishstudy.global.DBHelper;
import com.example.englishstudy.global.Stage_Item;
import com.example.englishstudy.global.WordItem;

import java.util.ArrayList;

public class MemorizationList extends AppCompatActivity {
    private ArrayList<WordItem> mWordItem;
    private ArrayList<Stage_Item> mStageList;
    private MemorizationAdapter memorizationAdapter;
    private DBHelper mDBHelper;
    private int check=1, progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorization_list);

        mDBHelper = new DBHelper(this);
        mWordItem = new ArrayList<>();
        mWordItem = mDBHelper.getWordList();

        mStageList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.mem_StageListRecyclerView);
        memorizationAdapter = new MemorizationAdapter(mStageList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(memorizationAdapter);

        int nothing=0;
        for(int i = 1; i <= 30; i++){//스테이지랑 complete 여부 체크
            progress=0;
            for(int j = (i - 1) * 30; j < (i - 1) * 30 +30; j++){//각 스테이지별로 마크 체크
                if(mWordItem.get(j).getIsMark() == 1) {
                    check = 0;//완료, 다시시도 체크
                    progress++;//개수 체크해서 progressbar에 반영
                }
            }
            if(check==1)//
                mStageList.add(new Stage_Item(""+i,"Complete",progress,nothing));//Stage 아이템 추가
            else//
                mStageList.add(new Stage_Item(""+i,"Try",progress,nothing));
            check=1;
        }
        memorizationAdapter.setmStageList(mStageList);

        memorizationAdapter.setMemOnItemClickListener(new MemorizationOnStageItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(MemorizationAdapter.ViewHolder holder, View v, int curPos) {
                if (!memorizationCheck(curPos)) {//반복할 단어가 없으면
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    String[] strChoiceItems = {"확인"};
                    builder.setTitle("이미 완료하였습니다.");
                    builder.setItems(strChoiceItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int dialogposition) {
                            //O가 선택되었을 때
                            if (dialogposition == 0) {
                                //현상유지
                            }
                        }
                    });
                    builder.create();
                    builder.show();
                }
                else {
                    //AlertDialog 를 이용한 팝업 창
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    String[] strChoiceItems = {"O", "X"};
                    builder.setTitle("암기를 시작하시겠습니까?");
                    builder.setItems(strChoiceItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int dialogposition) {
                            //O가 선택되었을 때
                            if (dialogposition == 0) {
                                //ReviewActivity 로 이동
                                //이때 현재 선택된 Stage 값을 Intent 로 전달
                                Intent intent = new Intent(v.getContext(), MemorizationActivity.class);
                                intent.putExtra("Stage", curPos);
                                startActivity(intent);
                            }
                            //X가 선택되었을 때
                            else if (dialogposition == 1) {
                                //현상 유지
                            }
                        }
                    });
                    builder.create();
                    builder.show();
                }
            }
        });


    }
    private boolean memorizationCheck(int position){//반복할 단어가 있으면 true, 없으면 false
        for(int i=position*30;i<position*30+30;i++) {
            if(mWordItem.get(i).getIsMark()==1)
                return true;
        }
        return false;
    }
}