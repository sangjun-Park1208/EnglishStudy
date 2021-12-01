package com.example.englishstudy.Review;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.englishstudy.R;
import com.example.englishstudy.Test.TestActivity;
import com.example.englishstudy.global.DBHelper;
import com.example.englishstudy.global.Stage_Item;
import com.example.englishstudy.global.WordItem;

import java.util.ArrayList;

public class ReviewList extends AppCompatActivity {
    private DBHelper mDBHelper;
    private ArrayList<WordItem> mWorditem;
    private int check=1;

    public static Context context_stage;
    public static int stage_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voca_review_day);

        mDBHelper=new DBHelper(this);
        mWorditem=new ArrayList<>();
        mWorditem=mDBHelper.getWordList();//DB 아이템들 끌고오기

        Log.d("superoid","superoid");

        RecyclerView recyclerView = findViewById(R.id.day_list_review);
        ReviewAdapter mReviewAdapter=new ReviewAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mReviewAdapter);

        ArrayList<Stage_Item> mreviewDayItems=new ArrayList<>();
        for(int i=1;i<=30;i++){//스테이지랑 complete 여부 체크
//            for(int j=0;j<30;j++){
//                if(mWorditem.get(i).getIsMark()==0)
//                    check=0;
//            }
//            if(check==1)//
                mreviewDayItems.add(new Stage_Item("Stage "+i,"complete"));//Stage 아이템 추가
//            else//
//                mreviewDayItems.add(new Stage_Item("Stage "+i,"challenge"));
//            check=1;
        }
        mReviewAdapter.setmDayList(mreviewDayItems);

        mReviewAdapter.setOnItemClicklistener(new ReviewOnStageItemClickListener() {
            @Override
            public void onItemClick(ReviewAdapter.ViewHolder holder, View view, int position) {
                if (!stage_check(position)) {//반복할 단어가 없으면
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    String[] strChoiceItems = {"O", "X"};
                    builder.setTitle("복습을 시작하시겠습니까?");
                    builder.setItems(strChoiceItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int dialogposition) {
                            //O가 선택되었을 때
                            if (dialogposition == 0) {
                                //ReviewActivity 로 이동
                                //이때 현재 선택된 Stage 값을 Intent 로 전달
                                Intent intent = new Intent(view.getContext(), ReviewActivity.class);
                                intent.putExtra("Stage", position);
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

    private boolean stage_check(int position){//반복할 단어가 있으면 true, 없으면 false
        for(int i=0;i<30;i++) {
            if(mWorditem.get(position+i).getIsMark()==1)
                return true;
        }
        return false;
    }
}