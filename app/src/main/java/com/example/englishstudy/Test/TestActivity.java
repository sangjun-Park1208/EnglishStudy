package com.example.englishstudy.Test;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.print.PrintAttributes;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
    private ArrayList<WordItem> WrongWordItems;

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
    private ImageView imageView_Correct;
    private ImageView imageView_Wrong;
    int index = 0;
    int select_Stage;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        imageView_Correct = new ImageView(getApplicationContext());
        imageView_Correct.setImageResource(R.drawable.image_correct_retro);

        imageView_Wrong = new ImageView(getApplicationContext());
        imageView_Wrong.setImageResource(R.drawable.image_wrong_retro);

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

        int wrong_Stage = select_Stage/3 +1;
        WrongWordItems = new ArrayList<WordItem>();
        for (int i = 0; i < 30; i++) {
            WrongWordItems.add(i, mWordItem.get(i + wrong_Stage * 30));
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

        //Test 볼 30단어 무작위로 섞음
        Collections.shuffle(testWordItems);

        //Test 시작
        startTest();


        //버튼 or CardView 클릭리스너 (1,2,3,4)
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1번 클릭
                if (v == bt_select1 || v == cv_select1) {
                    stopTimerTask();
                    //정답일 경우 해당 카드뷰 빨간색 표시, selectWrongAnswer();
                    if (!testMeaning.get(0).equals(testWordItems.get(index))) {
                        cv_select1.setBackgroundColor(Color.RED);
                        selectWrongAnswer();
                    } else {
                    //오답일 경우 해당 카드뷰 파란색 표시, selectCorrectAnswer();
                        cv_select1.setBackgroundColor(Color.BLUE);
                        selectCorrectAnswer();
                    }
                    //사용자가 정답을 확인하기 위한 위한 지연 1초
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            startTest();
                        }
                    }, 2500);


                }
                //2번 클릭
                else if (v == bt_select2 || v == cv_select2) {
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
                    }, 2500);

                }
                //3번 클릭
                else if (v == bt_select3 || v == cv_select3) {
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
                    }, 2500);
                }
                //4번 클릭
                else if (v == bt_select4 || v == cv_select4) {
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
                    }, 2500);
                }
            }
        };

        //버튼, 카드뷰 클릭리스너 참조
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
            //카운트 시작 시간 10초
            int count = 11;

            @Override
            public void run() {
                count--;
                tv_timer.post(new Runnable() {
                    @Override
                    public void run() {
                        tv_timer.setText(count + " 초");
                        //0초일 때 Timeout(시간초과) 처리
                        if (count == 0) {
                            stopTimerTask();
                            selectNothing_Timeout();
                        }
                    }
                });
            }
        };
        //시작 초 10, 1초씩 감소
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

    //Test 초기 화면 설정 및 시작
    public void startTest() {
        Random random = new Random(System.currentTimeMillis());

        //정답,오답 표시 초기화
        cv_select1.setBackgroundColor(Color.TRANSPARENT);
        cv_select2.setBackgroundColor(Color.TRANSPARENT);
        cv_select3.setBackgroundColor(Color.TRANSPARENT);
        cv_select4.setBackgroundColor(Color.TRANSPARENT);

        //30단어를 다 Test 했을 경우 Test 종료
        if (index == 30) {
            endTest(index);
        }
        else{

            //타이머 시작
            startTimerTask();

            //진행률 표시
            tv_progress = findViewById(R.id.tv_progress);
            tv_progress.setText(Integer.toString(index + 1) + "/30");

            //단어 표시
            tv_word.setText(testWordItems.get(index).getWord());

            //정답 설정
            WordItem correctWordItem = testWordItems.get(index);
            testMeaning.add(correctWordItem);
            WordItem wrongWordItem;

            //해당 Stage 에서 중복되지 않는 오답 3개 설정
            int count=1;
            while (count<4) {
                wrongWordItem = WrongWordItems.get(random.nextInt(WrongWordItems.size()));
                for(int i=0; i<testMeaning.size(); i++){
                    Log.d("test",Integer.toString(testMeaning.size()));
                    if(!wrongWordItem.getMeaning().equals(testMeaning.get(i).getMeaning())){
                        testMeaning.add(wrongWordItem);
                        Log.d("wrong",wrongWordItem.getMeaning());
                        count++;
                        break;
                    }
                }

            }

            //0:정답, 1,2,3: 오답 섞기
            Collections.shuffle(testMeaning);

            //뜻 표시
            tv_mean1.setText("1. "+testMeaning.get(0).getMeaning());
            tv_mean2.setText("2. "+testMeaning.get(1).getMeaning());
            tv_mean3.setText("3. "+testMeaning.get(2).getMeaning());
            tv_mean4.setText("4. "+testMeaning.get(3).getMeaning());
        }


    }


    //정답을 골랐을 때
    public void selectCorrectAnswer() {
        Toast toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
        toast.setView(imageView_Correct);
        toast.setGravity(Gravity.CENTER,50, 50);
        toast.show();

        //정답: Mark  0으로/ testMeaning 초기화 / 다음 단어로
        testWordItems.get(index).setIsMark(0);
        testMeaning.clear();
        index++;
    }

    //오답을 골랐을 때
    public void selectWrongAnswer() {
        Toast toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
        toast.setView(imageView_Wrong);
        toast.setGravity(Gravity.CENTER, 50, 50);
        toast.show();

        //1,2,3,4번 중 정답을 찾아 파란색으로 표시
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

        //오답: Mark  1로/ testMeaning 초기화 / 다음 단어로
        testWordItems.get(index).setIsMark(1);
        testMeaning.clear();
        index++;

    }


    //시간초과 되었을 때
    public void selectNothing_Timeout() {
//        Toast toast = Toast.makeText(getApplicationContext(), "시간이 초과되었습니다.", Toast.LENGTH_SHORT);
//        //toast.setView(imageView);
//        toast.setGravity(Gravity.CENTER, 50, 50);
//        toast.show();

        //1,2,3,4번 중 정답을 찾아 파란색으로 표시
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

        //오답(=시간초과): Mark  1로/ testMeaning 초기화 / 다음 단어로
        testWordItems.get(index).setIsMark(1);
        testMeaning.clear();
        index++;

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                startTest();
            }
        }, 2500);

    }

//    public class Delay {
//        private final int MESSAGE_WHAT = 1;
//
//        Handler handler = new Handler(new Handler.Callback() {
//
//            @Override
//            public boolean handleMessage(Message msg) {
//                getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//                if (msg.what == MESSAGE_WHAT) {
//                    new Runnable() {
//                        @Override
//                        public void run() {
//                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//                            startTest();
//                        }
//                    };
//                }
//                return false;
//            }
//        });
//
//        private void sendDelayMessage() {
//            Message msg = handler.obtainMessage(MESSAGE_WHAT);
//            handler.sendMessageDelayed(msg, 3000);
//        }
//
//        private void removeMessage() {
//            handler.removeMessages(MESSAGE_WHAT);
//        }
//
//    }

    //Test 종료
    public void endTest(int index) {
        int id;
        int day;
        int wordNum;
        int isMark;
        String word;
        String meaning;

        int correct = 0;
        int wrong = 0;

        //30단어를 다 Test 하지 않고 도중에 나갔을 경우
        //DB에 반영하지 않고 종료
        if (index != 30) {
            Intent intent = new Intent(this, TestList.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
        //30단어를 다 Test 했을 경우
        else {
            //무작위로 섞었던 30단어 정렬
            Collections.sort(testWordItems, new WordItemIdComparator());

            //DB에 결과 Update
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

            //정답개수, 오답개수를 결과창인 TestResult 로 보내고 종료
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
            toast = Toast.makeText(this,"Test 종료",Toast.LENGTH_LONG);
            toast.show();
        }
    }
}