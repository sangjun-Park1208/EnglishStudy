package com.example.englishstudy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class VocaReviewDay extends AppCompatActivity {
    private DBHelper mDBHelper;
    private ArrayList<WordItem> mWorditem;
    private int check=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voca_review_day);

        mWorditem=mDBHelper.getWordList();//DB 아이템들 끌고오기
//        for(int i=0;i<30;i++) {//30개 확인
//            if(mWorditem.get(i).getIsMark()==0)
//
//        }

        RecyclerView recyclerView = findViewById(R.id.day_list_review);

        ReviewAdapter mReviewAdapter=new ReviewAdapter();


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mReviewAdapter);


        ArrayList<ReviewDayItem> mreviewDayItems=new ArrayList<>();
        for(int i=1;i<=30;i++){//스테이지랑 complete 여부 체크
            for(int j=0;j<30;j++){
                if(mWorditem.get(i).getIsMark()==0)
                    check=0;
            }
            if(check==1)//
                mreviewDayItems.add(new ReviewDayItem("Stage "+i,"complete"));
            else//
                mreviewDayItems.add(new ReviewDayItem("Stage "+i,"challenge"));
            check=1;
        }
        mReviewAdapter.setmDayList(mreviewDayItems);
    }
}