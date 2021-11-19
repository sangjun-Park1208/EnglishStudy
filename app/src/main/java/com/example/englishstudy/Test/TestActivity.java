package com.example.englishstudy.Test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.englishstudy.R;

public class TestActivity extends AppCompatActivity {
    private Intent intent;
    private TextView tv_Stage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //선택한 Stage 번호를 Intent 로 받음
        //0: Stage 1, 1: Stage 2 ... 29: Stage 30
        intent = getIntent();
        int select_Stage = intent.getIntExtra("Stage",0);

        //Layout 상단에 Stage 표시
        tv_Stage =findViewById(R.id.tv_Stage);
        tv_Stage.setText("Stage"+Integer.toString(select_Stage+1));
    }
}