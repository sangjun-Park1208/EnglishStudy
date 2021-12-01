package com.example.englishstudy.Memorization;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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

    private DBHelper mDBHelper; // DB
    private Context mContext;
    private ArrayList<WordItem> mWordItem; // DB 호출 후 저장할 공간
    private ArrayList<WordItem> mSelectedItem;
    private Intent intent;


    private int selected_StageNum;


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
        mem_StageNum2.setText(Integer.toString(selected_StageNum + 1));
        // 선택한 StageNumber를 Intent로 받아, 기본값을 0으로 설정함(Stage0, Stage1, ... , Stage29)

        mDBHelper = new DBHelper(this);
        mWordItem = new ArrayList<>();
        mWordItem = mDBHelper.getWordList();
        // DB에서 단어 데이터 불러오기

        mSelectedItem = new ArrayList<>();
        for(int i=0; i<30;i++) { mSelectedItem.add(i, mWordItem.get(i+selected_StageNum * 30)); }
        // 화면에 표시할 30개 단어 불러오기(Primary key 값으로 불러옴)

        for(int i=0; i<30; i++){
            String tmpWord = mSelectedItem.get(i).getWord();
            String tmpMeaning = mSelectedItem.get(i).getMeaning();

            Log.d("단어 : ",i+" 번째 - " + tmpWord);
            Log.d("뜻 : ",i+" 번째 - " + tmpMeaning);
        }
//        mem_known.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                WordItem curWord = mSelectedItem.get()
//            }
//        });
    }


}