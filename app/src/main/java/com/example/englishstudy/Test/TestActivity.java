package com.example.englishstudy.Test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
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

    private Handler handler;

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
        Collections.shuffle(testWordItems);

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
                        selectWrongAnswer();
                    } else {
                        cv_select1.setBackgroundColor(Color.BLUE);
                        selectCorrectAnswer();
                    }
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            startTest();
                        }
                    }, 1000);


                } else if (v == bt_select2 || v == cv_select2) {
                    stopTimerTask();
                    //틀렸을 경우
                    if (!testMeaning.get(1).equals(testWordItems.get(index))) {
                        cv_select2.setBackgroundColor(Color.RED);
                        selectWrongAnswer();
                    } else {
                        cv_select2.setBackgroundColor(Color.BLUE);
                        selectCorrectAnswer();
                    }
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            startTest();
                        }
                    }, 1000);

                } else if (v == bt_select3 || v == cv_select3) {
                    stopTimerTask();
                    //틀렸을 경우
                    if (!testMeaning.get(2).equals(testWordItems.get(index))) {
                        cv_select3.setBackgroundColor(Color.RED);
                        selectWrongAnswer();
                    } else {
                        cv_select3.setBackgroundColor(Color.BLUE);
                        selectCorrectAnswer();
                    }
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            startTest();
                        }
                    }, 1000);
                } else if (v == bt_select4 || v == cv_select4) {
                    stopTimerTask();
                    //틀렸을 경우
                    if (!testMeaning.get(3).equals(testWordItems.get(index))) {
                        cv_select4.setBackgroundColor(Color.RED);
                        selectWrongAnswer();
                    } else {
                        cv_select4.setBackgroundColor(Color.BLUE);
                        selectCorrectAnswer();
                    }
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            startTest();
                        }
                    }, 1000);
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
        //timer.cancel();
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
                            selectNothing_Timeout();
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
        Random random = new Random(System.currentTimeMillis());

        cv_select1.setBackgroundColor(Color.TRANSPARENT);
        cv_select2.setBackgroundColor(Color.TRANSPARENT);
        cv_select3.setBackgroundColor(Color.TRANSPARENT);
        cv_select4.setBackgroundColor(Color.TRANSPARENT);

        if (index == 30) {
            endTest(index);
        }
        else{

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

            int count;
            for (count = 1; count < 4; count++) {
                wrongWordItem = testWordItems.get(random.nextInt(testWordItems.size()));

                for (int i = 0; i < testMeaning.size(); i++) {
                    if (testMeaning.get(i).getWordNum() == wrongWordItem.getWordNum()) {
                        count--;
                    } else {
                        testMeaning.add(wrongWordItem);
                    }
                    break;
                }
            }

            Collections.shuffle(testMeaning);
            tv_mean1.setText(testMeaning.get(0).getMeaning());
            tv_mean2.setText(testMeaning.get(1).getMeaning());
            tv_mean3.setText(testMeaning.get(2).getMeaning());
            tv_mean4.setText(testMeaning.get(3).getMeaning());
        }


    }


    //정답을 골랐을 때
    public void selectCorrectAnswer() {
        Toast toast = Toast.makeText(getApplicationContext(), "정답입니다", Toast.LENGTH_SHORT);
        //toast.setView(imageView);
        //toast.setGravity(Gravity.CENTER, 50, 50);
        toast.show();

        testWordItems.get(index).setIsMark(0);
        testMeaning.clear();
        index++;
    }

    //오답을 골랐을 때
    public void selectWrongAnswer() {
        Toast toast = Toast.makeText(getApplicationContext(), "틀렸습니다", Toast.LENGTH_SHORT);
        //toast.setView(imageView);
        //toast.setGravity(Gravity.CENTER, 50, 50);
        toast.show();

        for (int i = 0; i < 4; i++) {
            if (testMeaning.get(i).getWordNum() == testWordItems.get(index).getWordNum()) {
                switch (i) {
                    case 0:
                        cv_select1.setBackgroundColor(Color.BLUE);
                        break;
                    case 1:
                        cv_select2.setBackgroundColor(Color.BLUE);
                        break;
                    case 2:
                        cv_select3.setBackgroundColor(Color.BLUE);
                        break;
                    case 3:
                        cv_select4.setBackgroundColor(Color.BLUE);
                        break;
                }
                break;
            }
        }

        testWordItems.get(index).setIsMark(1);
        testMeaning.clear();

        index++;

    }


    //시간초과 되었을 때
    public void selectNothing_Timeout() {
        Toast toast = Toast.makeText(getApplicationContext(), "시간이 초과되었습니다.", Toast.LENGTH_SHORT);
        //toast.setView(imageView);
        toast.setGravity(Gravity.CENTER, 50, 50);
        toast.show();


        for (int i = 0; i < 4; i++) {
            if (testMeaning.get(i).getWordNum() == testWordItems.get(index).getWordNum()) {
                switch (i) {
                    case 0:
                        cv_select1.setBackgroundColor(Color.BLUE);
                        break;
                    case 1:
                        cv_select2.setBackgroundColor(Color.BLUE);
                        break;
                    case 2:
                        cv_select3.setBackgroundColor(Color.BLUE);
                        break;
                    case 3:
                        cv_select4.setBackgroundColor(Color.BLUE);
                        break;
                }
                break;
            }
        }

        testWordItems.get(index).setIsMark(1);
        testMeaning.clear();

        index++;

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                startTest();
            }
        }, 3000);

    }

    public class Delay {
        private final int MESSAGE_WHAT = 1;

        Handler handler = new Handler(new Handler.Callback() {

            @Override
            public boolean handleMessage(Message msg) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                if (msg.what == MESSAGE_WHAT) {
                    new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            startTest();
                        }
                    };
                }
                return false;
            }
        });

        private void sendDelayMessage() {
            Message msg = handler.obtainMessage(MESSAGE_WHAT);
            handler.sendMessageDelayed(msg, 3000);
        }

        private void removeMessage() {
            handler.removeMessages(MESSAGE_WHAT);
        }

    }

    public void endTest(int index) {
        int id;
        int day;
        int wordNum;
        int isMark;
        String word;
        String meaning;

        int correct = 0;
        int wrong = 0;
        //////////////////////////////////////////////////
        //도중에 나갈경우 DataUpdate 를 할것인가 말것인가?
        //반영안되게
        if (index != 30) {
            Intent intent = new Intent(this, TestList.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            Collections.sort(testWordItems, new WordItemIdComparator());
            for (int i = 0; i < testWordItems.size(); i++) {
                Log.d("test", testWordItems.get(i).getMeaning());
            }

            for (int i = 0; i < testWordItems.size(); i++) {

                id = (i + 1) + select_Stage * 30;
                day = testWordItems.get(i).getDay();
                wordNum = testWordItems.get(i).getWordNum();
                isMark = testWordItems.get(i).getIsMark();
                word = testWordItems.get(i).getWord();
                meaning = testWordItems.get(i).getMeaning();

                mDBHelper.UpdateWord(day, wordNum, isMark, word, meaning, id);

                if (testWordItems.get(i).getIsMark() == 1) {
                    wrong++;
                } else if (testWordItems.get(i).getIsMark() == 0) {
                    correct++;
                }
            }


            Intent intent = new Intent(this, TestResult.class);
            intent.putExtra("stage",select_Stage);
            intent.putExtra("correct", correct);
            intent.putExtra("wrong", wrong);
            startActivity(intent);
            finish();
        }


    }


    // 마지막으로 뒤로 가기 버튼을 눌렀던 시간 저장
    private long backKeyPressedTime = 0;
    // 첫 번째 뒤로 가기 버튼을 누를 때 표시
    private Toast toast;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        // 기존 뒤로 가기 버튼의 기능을 막기 위해 주석 처리 또는 삭제

        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간에 2.5초를 더해 현재 시간과 비교 후
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간이 2.5초가 지났으면 Toast 출력
        // 2500 milliseconds = 2.5 seconds
        if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "뒤로 가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간에 2.5초를 더해 현재 시간과 비교 후
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간이 2.5초가 지나지 않았으면 종료
        if (System.currentTimeMillis() <= backKeyPressedTime + 2500) {
            endTest(index);
            toast.cancel();
            toast = Toast.makeText(this, "이용해 주셔서 감사합니다.", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}