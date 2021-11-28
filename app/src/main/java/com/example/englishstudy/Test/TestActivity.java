package com.example.englishstudy.Test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.englishstudy.R;
import com.example.englishstudy.global.DBHelper;
import com.example.englishstudy.global.WordItem;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class TestActivity extends AppCompatActivity {
    //Intent 및 Stage 관련 변수
    private Intent intent;
    private TextView tv_Stage;

    //DB 관련 변수
    private DBHelper mDBHelper;
    private Context mContext;
    private ArrayList<WordItem> mWordItem;
    private ArrayList<WordItem> testWordItems;
    private ArrayList<WordItem> testMeaning;

    //Timer 관련 변수
    private TimerTask timerTask;
    private Timer timer = new Timer();
    private TextView tv_timer;

    private Button bt_select1;
    private Button bt_select2;
    private Button bt_select3;
    private Button bt_select4;

    private CardView cv_select1;
    private CardView cv_select2;
    private CardView cv_select3;
    private CardView cv_select4;

    private TextView tv_word;
    private TextView tv_mean1;
    private TextView tv_mean2;
    private TextView tv_mean3;
    private TextView tv_mean4;

    private TextView tv_progress;
    private ImageView imageView;
    int index = 0;
    int select_Stage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(R.drawable.ic_launcher_foreground);

        //선택한 Stage 번호를 Intent 로 받음
        //0: Stage 1, 1: Stage 2 ... 29: Stage 30
        intent = getIntent();
        select_Stage = intent.getIntExtra("Stage", 0);

        //Layout 상단에 Stage 표시
        tv_Stage = findViewById(R.id.tv_Stage);
        tv_Stage.setText("Stage" + Integer.toString(select_Stage + 1));

        //DB 에서 Data 불러오기
        mDBHelper = new DBHelper(this);
        mWordItem = new ArrayList<WordItem>();
        mWordItem = mDBHelper.getWordList();

        //Test 할 30 단어 불러오기
        testWordItems = new ArrayList<WordItem>();
        for (int i = 0; i < 30; i++) {
            testWordItems.add(i, mWordItem.get(i + select_Stage * 30));
        }

        //Button 참조
        bt_select1 = findViewById(R.id.bt_select1);
        bt_select2 = findViewById(R.id.bt_select2);
        bt_select3 = findViewById(R.id.bt_select3);
        bt_select4 = findViewById(R.id.bt_select4);

        //CardView 참조
        cv_select1 = findViewById(R.id.cv_select1);
        cv_select2 = findViewById(R.id.cv_select2);
        cv_select3 = findViewById(R.id.cv_select3);
        cv_select4 = findViewById(R.id.cv_select4);


        //TextView 참조
        tv_word = findViewById(R.id.tv_word);
        tv_mean1 = findViewById(R.id.tv_mean1);
        tv_mean2 = findViewById(R.id.tv_mean2);
        tv_mean3 = findViewById(R.id.tv_mean3);
        tv_mean4 = findViewById(R.id.tv_mean4);

        //Timer TextView
        tv_timer = findViewById(R.id.tv_timer);

        testMeaning = new ArrayList<WordItem>();
        //Collections.shuffle(testWordItems);

        startTest();

        ////////////////////////////////////////////////////////////

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v == bt_select1 || v == cv_select1) {
                    stopTimerTask();
                    //틀렸을 경우
                    if (!testMeaning.get(0).equals(testWordItems.get(index))) {
                        cv_select1.setBackgroundColor(Color.RED);
                        SelectWrongAnswer();
                    } else {
                        cv_select1.setBackgroundColor(Color.BLUE);
                        SelectCorrectAnswer();
                    }
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    new Handler ().postDelayed (new Runnable() {
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            startTest();
                        }
                    }, 3000);


                } else if (v == bt_select2 || v == cv_select2) {
                    stopTimerTask();
                    //틀렸을 경우
                    if (!testMeaning.get(1).equals(testWordItems.get(index))) {
                        cv_select2.setBackgroundColor(Color.RED);
                        SelectWrongAnswer();
                    } else {
                        cv_select2.setBackgroundColor(Color.BLUE);
                        SelectCorrectAnswer();
                    }
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    new Handler ().postDelayed (new Runnable() {
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            startTest();
                        }
                    }, 3000);

                } else if (v == bt_select3 || v == cv_select3) {
                    stopTimerTask();
                    //틀렸을 경우
                    if (!testMeaning.get(2).equals(testWordItems.get(index))) {
                        cv_select3.setBackgroundColor(Color.RED);
                        SelectWrongAnswer();
                    } else {
                        cv_select3.setBackgroundColor(Color.BLUE);
                        SelectCorrectAnswer();
                    }
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    new Handler ().postDelayed (new Runnable() {
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            startTest();
                        }
                    }, 3000);
                } else if (v == bt_select4 || v == cv_select4) {
                    stopTimerTask();
                    //틀렸을 경우
                    if (!testMeaning.get(3).equals(testWordItems.get(index))) {
                        cv_select4.setBackgroundColor(Color.RED);
                        SelectWrongAnswer();
                    } else {
                        cv_select4.setBackgroundColor(Color.BLUE);
                        SelectCorrectAnswer();
                    }
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    new Handler ().postDelayed (new Runnable() {
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            startTest();
                        }
                    }, 3000);
                }
            }
        };
        bt_select1.setOnClickListener(onClickListener);
        bt_select2.setOnClickListener(onClickListener);
        bt_select3.setOnClickListener(onClickListener);
        bt_select4.setOnClickListener(onClickListener);

        cv_select1.setOnClickListener(onClickListener);
        cv_select2.setOnClickListener(onClickListener);
        cv_select3.setOnClickListener(onClickListener);
        cv_select4.setOnClickListener(onClickListener);


    }


    @Override
    protected void onDestroy() {
        //Activity onDestroy() 시 Timer 종료
        timer.cancel();
        super.onDestroy();
    }

    //Timer 시작 메소드
    private void startTimerTask() {
        stopTimerTask();

        timerTask = new TimerTask() {
            int count = 10;

            @Override
            public void run() {
                count--;
                tv_timer.post(new Runnable() {
                    @Override
                    public void run() {
                        tv_timer.setText(count + " 초");
                        if (count == 0) {
                            stopTimerTask();
                            SelectWrongAnswer();
                            startTest();
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    //Timer 종료 및 초기화 메소드
    private void stopTimerTask() {
        if (timerTask != null) {
            tv_timer.setText("10 초");
            timerTask.cancel();
            timerTask = null;
        }
    }

    public void startTest() {
        Random random = new Random();

        cv_select1.setBackgroundColor(Color.TRANSPARENT);
        cv_select2.setBackgroundColor(Color.TRANSPARENT);
        cv_select3.setBackgroundColor(Color.TRANSPARENT);
        cv_select1.setBackgroundColor(Color.TRANSPARENT);

        if (index == 29) {
            endTest(testMeaning, testWordItems, index);
        }
        //타이머 시작
        startTimerTask();

        //TextView
        tv_progress = findViewById(R.id.tv_progress);
        tv_progress.setText(Integer.toString(index + 1) + "/30");

        //단어
        tv_word.setText(testWordItems.get(index).getWord());

        //뜻
        WordItem correctWordItem = testWordItems.get(index);
        testMeaning.add(correctWordItem);
        WordItem wrongWordItem;
        int count = 1;
        while (true) {

            wrongWordItem = mWordItem.get(random.nextInt(mWordItem.size()));
            //Log.d("wrong",wrongWordItem.getMeaning());
            if (!(wrongWordItem.getMeaning().equals(correctWordItem.getMeaning()))) {
            for(int i=0; i<testMeaning.size(); i++){
                    Log.d("wrong",Integer.toString(i)); //010123
                    //Log.d("wrong",wrongWordItem.getMeaning());
                    if(!wrongWordItem.getMeaning().equals(testMeaning.get(i).getMeaning())){
                        testMeaning.add(wrongWordItem);
                        //Log.d("wrong",testMeaning.get(i).getMeaning());
                        count++;
                    }
                }
            }

            if (count == 4) {
                break;
            }

        }
        //Collections.shuffle(testMeaning);
        tv_mean1.setText(testMeaning.get(0).getMeaning());
        tv_mean2.setText(testMeaning.get(1).getMeaning());
        tv_mean3.setText(testMeaning.get(2).getMeaning());
        tv_mean4.setText(testMeaning.get(3).getMeaning());


    }


    //정답 Toast 이미지로 사용해서 추가하기
    public void SelectCorrectAnswer() {
        Toast toast = Toast.makeText(getApplicationContext(), "정답입니다", Toast.LENGTH_SHORT);
        toast.setView(imageView);
        toast.setGravity(Gravity.CENTER, 50, 50);
        toast.show();

        testWordItems.get(index).setIsMark(0);
        testMeaning.clear();
        index++;
    }

    public void SelectWrongAnswer() {
        Toast toast = Toast.makeText(getApplicationContext(), "틀렸습니다", Toast.LENGTH_SHORT);
        toast.setView(imageView);
        toast.setGravity(Gravity.CENTER, 50, 50);
        toast.show();

        for(int i=0; i< 4;i++){
            if(testMeaning.get(i).getId()==testWordItems.get(index).getId()){
                switch (i){
                    case 1: cv_select1.setBackgroundColor(Color.BLUE);
                        break;
                    case 2: cv_select2.setBackgroundColor(Color.BLUE);
                        break;
                    case 3: cv_select3.setBackgroundColor(Color.BLUE);
                        break;
                    case 4: cv_select4.setBackgroundColor(Color.BLUE);
                        break;
                }
                break;
            }
        }

        testWordItems.get(index).setIsMark(1);
        testMeaning.clear();
        index++;

    }

    public void Delay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void endTest(ArrayList<WordItem> testMeaning, ArrayList<WordItem> testWordItems, int index) {
        for (int i = 0; i < testWordItems.size(); i++) {
            Collections.sort(testWordItems, new WordItemIdComparator());
        }

    }


    public class WordItemIdComparator implements Comparator<WordItem> {


        @Override
        public int compare(WordItem o1, WordItem o2) {
            return 0;
        }

        @Override
        public boolean equals(Object obj) {
            return false;
        }
    }
}