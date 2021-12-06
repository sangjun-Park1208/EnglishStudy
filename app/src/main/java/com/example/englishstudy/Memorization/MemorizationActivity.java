package com.example.englishstudy.Memorization;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.englishstudy.R;

import com.example.englishstudy.global.DBHelper;
import com.example.englishstudy.global.Stage_Item;
import com.example.englishstudy.global.WordItem;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MemorizationActivity extends AppCompatActivity {

    private TextView mem_StageNum1; // StageList 화면에서의 Stage number
    private TextView mem_StageNum2; // 암기 화면에서의 Stage number
    private TextView mem_progressText; // 진척도
    private ProgressBar mem_progressBar1; // StageList 화면에서의 진행바
    private ProgressBar mem_progressBar2; // 암기 화면에서의 진행바
    private TextView mem_word; // 영단어
    private TextView mem_meaning; // 한글 뜻
    private Button mem_known; // O 버튼
    private Button mem_unknown; // X 버튼
    private Button mem_list;

    private DBHelper mDBHelper; // DB
    private Context mContext;
    private ArrayList<WordItem> mWordItem; // DB 호출 후 저장할 공간
    private ArrayList<WordItem> mSelectedItem;
    private Intent intent;

    private MemorizationAdapter mAdapter;
    private ArrayList<Stage_Item> mStageList;


    private int selected_StageNum, wordIndex;
    private int progressNum;

    protected int index=0;

    // drawer
    private ArrayList<MemorizationVocaItem> mMemorizationVocaItems;
    private DrawerLayout drawerLayout;
    private View drawerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorization);

        mem_StageNum1 = findViewById(R.id.mem_stageNum1);
        mem_StageNum2 = findViewById(R.id.mem_stageNum2);
        mem_progressText = findViewById(R.id.mem_progressText);
        mem_progressBar1 = findViewById(R.id.mem_progressBar1);
        mem_progressBar2 = findViewById(R.id.mem_progressBar2);
        mem_word = findViewById(R.id.mem_word);
        mem_meaning = findViewById(R.id.mem_meaning);
        mem_known = findViewById(R.id.mem_known);
        mem_unknown = findViewById(R.id.mem_unknown);
        // 레이아웃의 참조들과 연결

        intent = getIntent();
        selected_StageNum = intent.getIntExtra("Stage",0);
        wordIndex = selected_StageNum*30;
        progressNum = 1;

        mem_StageNum2.setText(Integer.toString(selected_StageNum + 1));
        // 선택한 StageNumber를 Intent로 받아, 기본값을 0으로 설정함(Stage1, Stage2, ... , Stage30)

        mDBHelper = new DBHelper(this);
        mWordItem = new ArrayList<>();
        mWordItem = mDBHelper.getWordList();
        // DB에서 단어 데이터 불러오기




        drawerLayout = findViewById(R.id.mem_drawer_layout);
        drawerView = findViewById(R.id.mem_drawerView);
        drawerLayout.setDrawerListener(listener);

        RecyclerView recyclerView = findViewById(R.id.mem_drawer_recyclerView);
        MemorizationDrawerAdapter mMemorizationDrawerAdapter = new MemorizationDrawerAdapter();
//
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mMemorizationDrawerAdapter);
        ArrayList<MemorizationVocaItem> mMemorizationVocaItems = new ArrayList<>();

        for(int i = 0; i < 30; i++){
            mMemorizationVocaItems.add(new MemorizationVocaItem(mWordItem.get(i+wordIndex).getWord()));
        }

        mMemorizationDrawerAdapter.setmVocaList(mMemorizationVocaItems);
        mMemorizationDrawerAdapter.setOnItemClicklistener(new MemorizationVocaItemClickListener() {
            @Override
            public void onItemClick(MemorizationDrawerAdapter.ViewHolder holder, View view, int position) {
                mem_word.setText(mWordItem.get(wordIndex+position).getWord());
                mem_meaning.setText(mWordItem.get(wordIndex+position).getMeaning());
                index = position;
                progressNum = position + 1;
                mem_progressText.setText(String.valueOf(progressNum));
            }
        });


        mem_list = findViewById(R.id.mem_list);
        mem_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);
            }
        });








        // 첫 화면
        mem_progressText.setText(1+""); // 단어 progress 띄워주기

        mem_word.setText(mWordItem.get(wordIndex+1).getWord());
        mem_meaning.setText(mWordItem.get(wordIndex+1).getMeaning());
        progressNum++;        // 여기까지 첫 화면


        // O 버튼 클릭 시
        mem_known.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progressNum <= 30){
                    mWordItem = mDBHelper.getWordList();
                    mem_word.setText(mWordItem.get(wordIndex).getWord());
                    mem_meaning.setText(mWordItem.get(wordIndex).getMeaning());
                    mem_progressText.setText(""+progressNum++);
                    mWordItem.get(wordIndex).setIsMark(0); // O 버튼 누르면 isMarked 를 0으로
//                    Log.d("O버튼클릭시 : ",  String.valueOf(mWordItem.get(wordIndex).getIsMark()));
                    mDBHelper.UpdateWord(mWordItem.get(wordIndex).getDay(), mWordItem.get(wordIndex).getWordNum(), 0, mWordItem.get(wordIndex).getWord(), mWordItem.get(wordIndex).getMeaning(), mWordItem.get(wordIndex).getId());

                    wordIndex++;
                    index++;
                    mem_progressBar2.setProgress(index);
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    String[] strChoiceItems = {"확인"};
                    builder.setTitle("완료하였습니다.");
                    builder.setItems(strChoiceItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int dialogposition) {
                            //O가 선택되었을 때
                            if (dialogposition == 0) {
                                startActivity(new Intent(v.getContext(), MemorizationList.class));
                            }
                        }
                    });
                    builder.create();
                    builder.show();
                }
            }
        });

        // X 버튼 클릭 시
        mem_unknown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progressNum <= 30){
                    mWordItem = mDBHelper.getWordList();
                    mem_word.setText(mWordItem.get(index).getWord());
                    mem_meaning.setText(mWordItem.get(index).getMeaning());
                    mem_progressText.setText(""+progressNum++);
                    mWordItem.get(index).setIsMark(1); // X 버튼 누르면 isMarked 를 1로
//                    Log.d("X버튼클릭시 : ",  String.valueOf(mWordItem.get(wordIndex).getIsMark()));
                    mDBHelper.UpdateWord(mWordItem.get(wordIndex).getDay(), mWordItem.get(wordIndex).getWordNum(), 1, mWordItem.get(wordIndex).getWord(), mWordItem.get(wordIndex).getMeaning(), mWordItem.get(wordIndex).getId());
                    wordIndex++;
                    index++;
                    mem_progressBar2.setProgress(index);

                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    String[] strChoiceItems = {"확인"};
                    builder.setTitle("완료하였습니다.");
                    builder.setItems(strChoiceItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int dialogposition) {
                            //O가 선택되었을 때
                            if (dialogposition == 0) {
                                startActivity(new Intent(v.getContext(), MemorizationList.class));

                            }
                        }
                    });
                    builder.create();
                    builder.show();
                }
            }
        });









//        mSelectedItem = new ArrayList<>();
//        for(int i=0; i<30;i++) {
//            mSelectedItem.add(i, mWordItem.get(i+selected_StageNum * 30));
//            Log.d("TAGTAG",mSelectedItem.get(i).getWord());
//        }
//        // 화면에 표시할 30개 단어 불러오기(Primary key 값으로 불러옴)
//
//        for(int i=0; i<30; i++){
//            mem_word.setText(mWordItem.get(selected_StageNum+i).getWord());
//            mem_meaning.setText(mWordItem.get(selected_StageNum+i).getMeaning());
//
//            mem_known.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//
//                }
//            });
//
//        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MemorizationList.class));

    }
    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
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
}