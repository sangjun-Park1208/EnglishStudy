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

public class ReviewActivity extends AppCompatActivity {
    private Intent intent;
    private int stage,index;

    private TextView txt_stage, txt_progress, txt_word, txt_meaning;
    private ArrayList<WordItem> mWordItem;
    private DBHelper mDBHelper;
    private Button btn_1, btn_2;
    private int total = 0, progress = 1,check,i,before;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voca_learn);

        mDBHelper = new DBHelper(this);
        mWordItem = new ArrayList<>();

        mWordItem = mDBHelper.getWordList();//DB 아이템들 끌고오기
        intent = getIntent();
        stage = intent.getIntExtra("Stage", 0);
        index = stage*30;

        txt_stage=(TextView) findViewById(R.id.memorization_text);
        txt_stage.setText("Stage"+Integer.toString(stage+1));
        txt_progress=(TextView) findViewById(R.id.vocalearn_progress);
        txt_word=(TextView) findViewById(R.id.word);
        txt_meaning=(TextView) findViewById(R.id.meaning);

        //단어 화면에 띄우기. id는 1부터, index는 0부터
        for(i=0;i<30;i++) {
            if(mWordItem.get(index+i).getIsMark()==1)
                total++;
        }
//        if(total==0)
//            startActivity(new Intent(this, ReviewList.class));

        //처음 들어오자마자 화면
        txt_progress.setText(1+"/"+total);
        for(i=0;i<30;i++) {
            if(mWordItem.get(index+i).getIsMark()==1) {//처음 들어오자마자 화면 띄우기
                txt_word.setText(mWordItem.get(index+i).getWord());
                txt_meaning.setText(mWordItem.get(index+i).getMeaning());
                txt_progress.setText(progress+"/"+total);
                progress++;
                check=i+index;
                break;
            }
        }

        btn_1=(Button) findViewById(R.id.known);
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!end(index)) {//ismarked 0으로 바꿔주기
                    WordItem cur = mWordItem.get(check);
                    mDBHelper.UpdateWord(cur.getDay(), cur.getWordNum(), 0, cur.getWord(), cur.getMeaning(), cur.getId());
                }

                for(i=check+1;i<index+30;i++){
                    if(mWordItem.get(i).getIsMark()==1) {
                        check = i;
                        break;
                    }
                }
                if(progress<=total) {
                    txt_word.setText(mWordItem.get(check).getWord());
                    txt_meaning.setText(mWordItem.get(check).getMeaning());
                    txt_progress.setText(progress + "/" + total);
                    progress++;
                    check = i;
                }

                else if(progress>total&&end(index)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    String[] strChoiceItems = {"확인"};
                    builder.setTitle("완료하였습니다.");
                    builder.setItems(strChoiceItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int dialogposition) {
                            //O가 선택되었을 때
                            if (dialogposition == 0) {
                                startActivity(new Intent(view.getContext(), ReviewList.class));
                            }
                        }
                    });
                    builder.create();
                    builder.show();
                }
                else{
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
                                Intent intent = new Intent(view.getContext(), ReviewActivity.class);
                                intent.putExtra("Stage", stage);
                                startActivity(intent);
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
            }
        });

        btn_2=(Button) findViewById(R.id.unknown);
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(i=check+1;i<index+30;i++){
                    if(mWordItem.get(i).getIsMark()==1) {
                        check = i;
                        break;
                    }
                }
                if(progress<=total) {
                    txt_word.setText(mWordItem.get(check).getWord());
                    txt_meaning.setText(mWordItem.get(check).getMeaning());
                    txt_progress.setText(progress + "/" + total);
                    progress++;
                    check = i;
                }
                else{
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
                                Intent intent = new Intent(view.getContext(), ReviewActivity.class);
                                intent.putExtra("Stage", stage);
                                startActivity(intent);
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
            }
        });
    }

    private boolean end(int index){
        mWordItem = mDBHelper.getWordList();//DB 아이템들 끌고오기
        for(i=index;i<index+30;i++) {
            if (mWordItem.get(i).getIsMark() == 1) {
                return false;
            }
        }
        return true;
    }
}