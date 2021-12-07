package com.example.englishstudy.Review;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.englishstudy.R;
import com.example.englishstudy.global.DBHelper;
import com.example.englishstudy.global.Stage_Item;
import com.example.englishstudy.global.WordItem;

import java.util.ArrayList;

public class ReviewList extends AppCompatActivity {
    //DB
    private DBHelper mDBHelper;
    private ArrayList<WordItem> mWorditem;

    //스테이지가 완료했는지, 얼마나 했는지 표시하기 위한 필드
    private int check=1, progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voca_review_day);

        //액션바 숨기기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //DB연동
        mDBHelper=new DBHelper(this);
        mWorditem=new ArrayList<>();
        mWorditem=mDBHelper.getWordList();//DB 아이템들 끌고오기

        //리사이클러뷰
        RecyclerView recyclerView = findViewById(R.id.day_list_review);//xml연결
        ReviewAdapter mReviewAdapter=new ReviewAdapter();//어댑터 연결
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//레이아웃 연결
        recyclerView.setAdapter(mReviewAdapter);
        ArrayList<Stage_Item> mreviewDayItems=new ArrayList<>();//스테이지 정보 아이템


        int nothing=0;//빈자리 채우기 위한 필드(의미x)
        for(int i=1;i<=30;i++){//스테이지 아이템 추가, 완료했는지 여부 체크
            progress=0;
            for(int j=(i-1)*30;j<(i-1)*30+30;j++){//각 스테이지별로 마크 체크
                if(mWorditem.get(j).getIsMark()==1) {
                    check = 0;//완료, 다시시도 체크
                    progress++;//개수 체크해서 progressbar에 반영
                }
            }
            if(check==1)//완료했을 경우
                mreviewDayItems.add(new Stage_Item("Stage"+i,"complete",progress,nothing));//Stage 아이템 추가
            else//완료하지 못했거나 안한 경우
                mreviewDayItems.add(new Stage_Item("Stage"+i,"try",progress,nothing));
            check=1;
        }
        mReviewAdapter.setmDayList(mreviewDayItems);//스테이지 아이템들 세팅

        //스테이지 클릭리스너
        mReviewAdapter.setOnItemClicklistener(new ReviewOnStageItemClickListener() {
            @Override
            public void onItemClick(ReviewAdapter.ViewHolder holder, View view, int position) {
                if (!stage_check(position)) {//반복할 단어가 없으면
                    //다이얼로그
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    String[] strChoiceItems = {"확인"};
                    builder.setTitle("이미 완료하였습니다.");
                    builder.setItems(strChoiceItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int dialogposition) {
                            if (dialogposition == 0) {
                                //현상유지
                            }
                        }
                    });
                    //다이얼로그 생성
                    builder.create();
                    builder.show();
                }
                else {
                    //다이얼로그
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    String[] strChoiceItems = {"예", "아니오"};
                    builder.setTitle("복습을 시작하시겠습니까?");
                    builder.setItems(strChoiceItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int dialogposition) {
                            //'예'가 선택되었을 때
                            if (dialogposition == 0) {
                                //ReviewActivity 로 이동
                                //이때 현재 선택된 Stage 값을 Intent 로 전달. 스테이지 값을 통해 DB에서 단어 위치 찾기
                                Intent intent = new Intent(view.getContext(), ReviewActivity.class);
                                intent.putExtra("Stage", position);
                                startActivity(intent);
                            }
                            //'아니오'가 선택되었을 때
                            else if (dialogposition == 1) {
                                //현상 유지
                            }
                        }
                    });
                    //다이얼로그 생성
                    builder.create();
                    builder.show();
                }
            }
        });
    }

    private boolean stage_check(int position){//반복할 단어가 있으면 true, 없으면 false
        for(int i=position*30;i<position*30+30;i++) {
            if(mWorditem.get(i).getIsMark()==1)
                return true;
        }
        return false;
    }

    @Override
    protected void onStop(){//테스크 관리. 항상 업데이트 해주기 위해 화면에 가려지면 종료시킴
        super.onStop();
        finish();
    }
}