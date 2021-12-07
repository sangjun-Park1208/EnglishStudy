package com.example.englishstudy.Review;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.englishstudy.R;
import com.example.englishstudy.global.DBHelper;
import com.example.englishstudy.global.WordItem;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {
    //ReviewList에서 받아온 인텐트
    private Intent intent;
    private int stage, index;//stage,

    //단어 리스트 인덱스 관리를 위한 배열 및 필드
    private int[] inoutbox=new int[30];
    private int inoutboxsum,listindex;

    //네비게이션뷰
    private ArrayList<ReviewVocaItem> mReviewVocaItems;
    private DrawerLayout drawerLayout;
    private View drawerView;

    //DB
    private ArrayList<WordItem> mWordItem;
    private DBHelper mDBHelper;

    //텍스트뷰,버튼뷰
    private TextView txt_stage, txt_progress, txt_word, txt_meaning;
    private Button btn_1, btn_2,btn_voca;

    //액티비티 화면에 띄워지는 단어 인덱스 관리를 위한 필드
    private int total = 0, progress = 1, check, i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voca_learn);

        //액션바 숨기기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //DB연동
        mDBHelper = new DBHelper(this);
        mWordItem = new ArrayList<>();
        mWordItem = mDBHelper.getWordList();//DB 아이템들 끌고오기

        //stage에서 받은 인텐트
        intent = getIntent();
        stage = intent.getIntExtra("Stage", 0);
        index = stage * 30;//DB에 접근할 index

        //텍스트뷰 id 연결
        txt_stage = (TextView) findViewById(R.id.memorization_text);
        txt_stage.setText("Stage" + (stage + 1));
        txt_progress = (TextView) findViewById(R.id.vocalearn_progress);
        txt_word = (TextView) findViewById(R.id.word);
        txt_meaning = (TextView) findViewById(R.id.meaning);

        //네비게이션뷰
        mReviewVocaItems=new ArrayList<>();//리스트 정보
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);//네비게이션뷰
        drawerView = (View) findViewById(R.id.drawerView);
        drawerLayout.setDrawerListener(listener);//네비게이션뷰 리스너
        RecyclerView recyclerView = findViewById(R.id.review_drawer_recyclerView);
        ReviewDrawerAdapter mReviewDrawerAdapter=new ReviewDrawerAdapter();//네비게이션뷰 어댑터 연결
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//레이아웃연결
        recyclerView.setAdapter(mReviewDrawerAdapter);
        ArrayList<ReviewVocaItem> mReviewVocaItems=new ArrayList<>();//아이템 연결

        for(i=0;i<30;i++){//조건에 따라 아이템 추가
            if(mWordItem.get(i+index).getIsMark()==1) {//mark가 1인 경우에만 리스트에 아이템 추가
                mReviewVocaItems.add(new ReviewVocaItem(mWordItem.get(i+index).getWord()));
                inoutbox[i%30]=1;//0과 1체크
            }
        }
        
        mReviewDrawerAdapter.setmVocaList(mReviewVocaItems);
        mReviewDrawerAdapter.setOnItemClicklistener(new ReviewVocaItemClickListener() {//리스트들 클릭 리스너
            @Override
            public void onItemClick(ReviewDrawerAdapter.ViewHolder holder, View view, int position) {
                //리스트들 인덱스 계산
                inoutboxsum=0;
                listindex=0;
                for(i=0;;i++){
                    if(inoutboxsum==position+1)
                        break;
                    if(inoutbox[i]==1) {
                        inoutboxsum++;
                        listindex=i;
                    }
                }
                
                //해당 아이템 클릭시 액티비티 화면에 띄우기
                txt_word.setText(mWordItem.get(listindex + index).getWord());
                txt_meaning.setText(mWordItem.get(listindex + index).getMeaning());
                progress=inoutboxsum;
                txt_progress.setText(progress + "/" + total);
                progress++;
                check = listindex + index;//인덱스 흔적 남기기
                drawerLayout.closeDrawer(drawerView);//리스트 누르면 바로 네비게이션뷰 안보이게하기
            }
        });

        //단어 화면에 띄우기. id는 1부터, index는 0부터, total은 복습해야할 단어의 총 개수
        for (i = 0; i < 30; i++) {
            if (mWordItem.get(index + i).getIsMark() == 1)
                total++;
        }

        //처음 들어오자마자 화면
        txt_progress.setText(1 + "/" + total);
        for (i = 0; i < 30; i++) {
            if (mWordItem.get(index + i).getIsMark() == 1) {//처음 들어오자마자 화면 띄우기
                txt_word.setText(mWordItem.get(index + i).getWord());
                txt_meaning.setText(mWordItem.get(index + i).getMeaning());
                txt_progress.setText(progress + "/" + total);
                progress++;
                check = i + index;
                break;
            }
        }

        //목록 버튼(네비게이션뷰 open)
        btn_voca=(Button) findViewById(R.id.vocalearn_list);
        btn_voca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(drawerView);
            }
        });

        //외웠어요 버튼
        btn_1 = (Button) findViewById(R.id.known);
        btn_1.setOnClickListener(new View.OnClickListener() {//외웠어요 버튼 클릭리스너
            @Override
            public void onClick(View view) {
                if (!end(index)) {//ismarked 0으로 바꿔주기
                    WordItem cur = mWordItem.get(check);
                    mDBHelper.UpdateWord(cur.getDay(), cur.getWordNum(), 0, cur.getWord(), cur.getMeaning(), cur.getId());
                }
                //복습해야 하는 단어중 다음 단어의 인덱스 구하기
                for (i = check + 1; i < index + 30; i++) {
                    if (mWordItem.get(i).getIsMark() == 1) {
                        check = i;
                        break;
                    }
                }
                if (progress <= total) {
                    //화면 띄우기 및 필드 계산
                    txt_word.setText(mWordItem.get(check).getWord());
                    txt_meaning.setText(mWordItem.get(check).getMeaning());
                    txt_progress.setText(progress + "/" + total);
                    progress++;
                    check = i;
                } else if (progress > total && end(index)) {
                    //다이얼로그
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    String[] strChoiceItems = {"확인"};
                    builder.setTitle("완료하였습니다.");
                    builder.setItems(strChoiceItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int dialogposition) {
                            // 완료되었기 때문에 스테이지 선택하는 액티비티로
                            if (dialogposition == 0) {
                                startActivity(new Intent(view.getContext(), ReviewList.class));
                            }
                        }
                    });
                    //다이얼로그 생성
                    builder.create();
                    builder.show();
                } else {
                    //다이얼로그
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    String[] strChoiceItems = {"예", "아니오"};
                    builder.setTitle("다시 시작하시겠습니까?");
                    builder.setItems(strChoiceItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int position) {
                            //예가 선택되었을 때
                            if (position == 0) {
                                //ReviewActivity 로 이동
                                //이때 현재 선택된 Stage 값을 Intent 로 전달
                                Intent intent = new Intent(view.getContext(), ReviewActivity.class);
                                intent.putExtra("Stage", stage);
                                startActivity(intent);
                            }
                            //아니오가 선택되었을 때 : 스테이지 선택 액티비티로 이동
                            else if (position == 1) {
                                startActivity(new Intent(view.getContext(), ReviewList.class));
                            }
                        }
                    });
                    //다이얼로그 생성
                    builder.create();
                    builder.show();
                }
            }
        });

        //못 외웠어요 버튼
        btn_2 = (Button) findViewById(R.id.unknown);
        btn_2.setOnClickListener(new View.OnClickListener() {//버튼 클릭리스너
            @Override
            public void onClick(View view) {
                //다음 복습할 단어의 인덱스 구하기
                for (i = check + 1; i < index + 30; i++) {
                    if (mWordItem.get(i).getIsMark() == 1) {
                        check = i;
                        break;
                    }
                }
                //화면에 띄우기+필드값 정리
                if (progress <= total) {
                    txt_word.setText(mWordItem.get(check).getWord());
                    txt_meaning.setText(mWordItem.get(check).getMeaning());
                    txt_progress.setText(progress + "/" + total);
                    progress++;
                    check = i;
                } else {
                    //다이얼로그
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    String[] strChoiceItems = {"예", "아니오"};
                    builder.setTitle("다시 시작하시겠습니까?");
                    builder.setItems(strChoiceItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int position) {
                            //예가 선택되었을 때
                            if (position == 0) {
                                //ReviewActivity 로 이동
                                //이때 현재 선택된 Stage 값을 Intent 로 전달
                                Intent intent = new Intent(view.getContext(), ReviewActivity.class);
                                intent.putExtra("Stage", stage);
                                startActivity(intent);
                            }
                            //아니오가 선택되었을 때 : 스테이지 선택 액티비티로 이동
                            else if (position == 1) {
                                startActivity(new Intent(view.getContext(), ReviewList.class));
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

    private boolean end(int index) {//스테이지의 모든 단어가 0인지 확인
        mWordItem = mDBHelper.getWordList();//DB 아이템들 끌고오기
        for (i = index; i < index + 30; i++) {
            if (mWordItem.get(i).getIsMark() == 1) {
                return false;
            }
        }
        return true;
    }

    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {//네비게이션 드로어 리스너
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {
        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {
        }

        @Override
        public void onDrawerStateChanged(int newState) {
        }
    };

    @Override
    public void onBackPressed(){//뒤로가기 버튼을 눌렀을 경우 스테이지 액티비티 다시 만들기
        super.onBackPressed();
        startActivity(new Intent(this, ReviewList.class));
    }

}