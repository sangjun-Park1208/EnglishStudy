package com.example.englishstudy.Test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.englishstudy.R;

public class TestResult extends AppCompatActivity {
    private Intent intent;

    private TextView test_tv_Stage;
    private TextView test_tv_Correct;
    private TextView test_tv_Wrong;

    private Button test_bt_Retry;
    private Button test_bt_OK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);

        int stage;
        int correct;
        int wrong;

        //Intent 로 stage, 정답 개수, 오답 개수 전달 받음
        intent = getIntent();
        stage = intent.getIntExtra("stage", 0);
        correct = intent.getIntExtra("correct", 0);
        wrong = intent.getIntExtra("wrong", 0);

        //TextView, Button 참조
        test_tv_Stage = findViewById(R.id.test_tv_Stage);
        test_tv_Stage.setText("Stage " + Integer.toString(stage+1));

        test_tv_Correct = findViewById(R.id.test_tv_Correct);
        test_tv_Correct.setText("정답: " + Integer.toString(correct));

        test_tv_Wrong = findViewById(R.id.test_tv_Wrong);
        test_tv_Wrong.setText("오답: " + Integer.toString(wrong));

        test_bt_OK = findViewById(R.id.test_bt_ok);
        test_bt_Retry = findViewById(R.id.test_bt_Retry);

        //OK 버튼 클릭 시
        test_bt_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //stage, 정답 개수, 오답 개수 TestList 로 전달 후 종료
                Intent intent = new Intent(v.getContext(), TestList.class);
                intent.putExtra("stage",stage);
                intent.putExtra("correct", correct);
                intent.putExtra("wrong", wrong);
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
                startActivity(intent);
                finish();
            }
        });
    }
}