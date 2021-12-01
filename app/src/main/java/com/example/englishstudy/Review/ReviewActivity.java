package com.example.englishstudy.Review;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.englishstudy.MainActivity;
import com.example.englishstudy.R;
import com.example.englishstudy.VocaList;
import com.example.englishstudy.global.DBHelper;
import com.example.englishstudy.global.WordItem;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity{

    private Intent intent;
    private int index;

    private TextView mstage;
    private TextView mprogress;
    private TextView mword;
    private TextView mmeaning;
    private ArrayList<WordItem> mWordItem;
    private DBHelper mDBHelper;
    private Button bt1;
    private Button bt2;
    private int total=0;
    private int progress=0;
    private int check;
    private int escape;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voca_learn);

        mDBHelper=new DBHelper(this);
        mWordItem=new ArrayList<>();

        mWordItem=mDBHelper.getWordList();//DB 아이템들 끌고오기
        intent=getIntent();
        index=intent.getIntExtra("Stage",0);

        mstage=(TextView) findViewById(R.id.memorization_text);
        mstage.setText("stage"+Integer.toString(index + 1));
        mprogress=(TextView) findViewById(R.id.vocalearn_progress);
        mword = (TextView) findViewById(R.id.word);
        mmeaning = (TextView) findViewById(R.id.meaning);
        Log.d("superoid","superoid2");

        //단어 화면에 띄우기. id는 1부터, index는 0부터
        for(int i=0;i<30;i++) {
            if(mWordItem.get(index+i).getIsMark()==1)
                total++;
        }

        for(int i=0;i<30;i++) {
            if(mWordItem.get(index+i).getIsMark()==1) {
                mword.setText(mWordItem.get(index+i).getWord());
                mmeaning.setText(mWordItem.get(index+i).getMeaning());
                progress++;
                mprogress.setText(progress+"/"+total);
                check=i+index;
                break;
            }
        }
        //o,x버튼 눌렀을때
        bt1=(Button) findViewById(R.id.known);
        bt1.setOnClickListener(new View.OnClickListener() {//O버튼 눌렀을때
            @Override
            public void onClick(View view) {
                WordItem curworditem=mWordItem.get(check);
                mDBHelper.UpdateWord(curworditem.getDay(),curworditem.getWordNum(),0,curworditem.getWord(),curworditem.getMeaning(),curworditem.getId());

                for(int i=check+1;i<index+31;i++){
                    escape=i;
                    if(mWordItem.get(i).getIsMark()==1) {
                        check = i;
                        break;
                    }
                }
                if(escape>=index+29) {
                    //AlertDialog 를 이용한 팝업 창
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    String[] strChoiceItems = {"O", "X"};
                    builder.setTitle("다시 시작하시겠습니까?");
                    builder.setItems(strChoiceItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int position) {
                            //O가 선택되었을 때
                            if (position == 0) {
                                //ReviewActivity 로 이동
                                //이때 현재 선택된 Stage 값을 Intent 로 전달
                                startActivity(new Intent(view.getContext(), ReviewActivity.class));
                            }
                            //X가 선택되었을 때
                            else if (position == 1) {
                                startActivity(new Intent(view.getContext(), ReviewList.class));
                            }
                        }
                    });
                    builder.create();
                    builder.show();
                }
                if(progress<total) {
                    mword.setText(mWordItem.get(check).getWord());
                    mmeaning.setText(mWordItem.get(check).getMeaning());
                    progress++;
                    mprogress.setText(progress + "/" + total);
                }
            }
        });
        bt2=(Button) findViewById(R.id.unknown);
        bt2.setOnClickListener(new View.OnClickListener() {//X버튼 눌렀을때
            @Override
            public void onClick(View view) {
                for (int i = check + 1; i < index + 31; i++) {
                    escape=i;
                    if (mWordItem.get(i).getIsMark() == 1) {
                        check = i;
                        break;
                    }
                }
                if (escape >= index + 29){
                    //AlertDialog 를 이용한 팝업 창
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    String[] strChoiceItems = {"O", "X"};
                    builder.setTitle("다시 시작하시겠습니까?");
                    builder.setItems(strChoiceItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int position) {
                            //O가 선택되었을 때
                            if (position == 0) {
                                //ReviewActivity 로 이동
                                //이때 현재 선택된 Stage 값을 Intent 로 전달
                                startActivity(new Intent(view.getContext(), ReviewActivity.class));
                            }
                            //X가 선택되었을 때
                            else if (position == 1) {
                                startActivity(new Intent(view.getContext(), ReviewList.class));
                            }
                        }
                    });
                    builder.create();
                    builder.show();
                }
                if (progress < total) {
                    mword.setText(mWordItem.get(check).getWord());
                    mmeaning.setText(mWordItem.get(check).getMeaning());
                    progress++;
                    mprogress.setText(progress + "/" + total);
                }
            }

        });


    }



}