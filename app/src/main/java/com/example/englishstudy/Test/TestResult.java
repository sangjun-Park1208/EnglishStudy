package com.example.englishstudy.Test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.englishstudy.R;

public class TestResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);


        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        int stage;
        int correct;
        int wrong;

        //Intent 로 stage, 정답 개수, 오답 개수 전달 받음
        Intent intent = getIntent();
        stage = intent.getIntExtra("stage", 0);
        correct = intent.getIntExtra("correct", 0);
        wrong = intent.getIntExtra("wrong", 0);

        Log.d("TESTLOG","TestResult");
        Log.d("TESTLOG","결과창에 전달 완료");

        //TextView, Button 참조
        TextView test_tv_Stage = findViewById(R.id.test_tv_Stage);
        test_tv_Stage.setText("Stage " + Integer.toString(stage+1));

        TextView test_tv_Correct = findViewById(R.id.test_tv_Correct);
        test_tv_Correct.setText("정답: " + Integer.toString(correct));

        TextView test_tv_Wrong = findViewById(R.id.test_tv_Wrong);
        test_tv_Wrong.setText("오답: " + Integer.toString(wrong));

        Button test_bt_OK = findViewById(R.id.test_bt_ok);
        Button test_bt_Retry = findViewById(R.id.test_bt_Retry);

        //DONE 버튼 클릭 시
        test_bt_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //stage, 정답 개수, 오답 개수 TestList 로 전달 후 종료
                Intent intent = new Intent(v.getContext(), TestList.class);
                intent.putExtra("stage",stage);
                intent.putExtra("correct", correct);
                intent.putExtra("wrong", wrong);

                Log.d("TESTLOG","DONE 클릭");
                Log.d("TESTLOG","Stage:"+(stage+1));
                Log.d("TESTLOG","정답:"+correct);
                Log.d("TESTLOG","오답:"+wrong);
                Log.d("TESTLOG","TestList 에 전달");

                startActivity(intent);
                finish();
            }
        });

        //Retry 버튼 클릭 시
        test_bt_Retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //해당 Stage Test 다시 시작
                Intent intent = new Intent(v.getContext(), TestActivity.class);
                intent.putExtra("Stage",stage);

                Log.d("TESTLOG","Retry 클릭");
                Log.d("TESTLOG","Stage:"+(stage+1)+"재시작");

                startActivity(intent);
                finish();
            }
        });
    }
}