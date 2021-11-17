package com.example.englishstudy.Review;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.englishstudy.MainActivity;
import com.example.englishstudy.R;
import com.example.englishstudy.global.DBHelper;
import com.example.englishstudy.global.WordItem;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity{

    private TextView mword;
    private TextView mmeaning;
    private ArrayList<WordItem> mWordItem;
    private DBHelper mDBHelper;
    private Button bt1;
    private Button bt2;
    int index=((MainActivity)MainActivity.context_main).voca_index;//index를 Memorization에서 받아와야함;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voca_learn);

        mDBHelper=new DBHelper(this);
        mWordItem=new ArrayList<>();

        mWordItem=mDBHelper.getWordList();//DB 아이템들 끌고오기


        mword=(TextView) findViewById(R.id.word);
        mmeaning=(TextView) findViewById(R.id.meaning);
        mword.setText(mWordItem.get(index).getWord());
        mmeaning.setText(mWordItem.get(index).getMeaning());


        //o,x버튼 눌렀을때
        bt1=(Button) findViewById(R.id.known);
        bt1.setOnClickListener(new View.OnClickListener() {//O버튼 눌렀을때
            @Override
            public void onClick(View view) {
                mWordItem.get(index).setIsMark(1);
                index++;
                mword.setText(mWordItem.get(index).getWord().toString());
                mmeaning.setText(mWordItem.get(index).getMeaning().toString());
            }
        });
        bt2=(Button) findViewById(R.id.unknown);
        bt2.setOnClickListener(new View.OnClickListener() {//X버튼 눌렀을때
            @Override
            public void onClick(View view) {
                mWordItem.get(index).setIsMark(0);
                index++;
                mword.setText(mWordItem.get(index).getWord().toString());
                mmeaning.setText(mWordItem.get(index).getMeaning().toString());
            }
        });


    }



}