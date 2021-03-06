package com.example.englishstudy.Memorization;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.englishstudy.R;
import com.example.englishstudy.global.DBHelper;
import com.example.englishstudy.global.Stage_Item;
import com.example.englishstudy.global.WordItem;

import java.util.ArrayList;

public class MemorizationList extends AppCompatActivity {
    private ArrayList<WordItem> mWordItem;
    private ArrayList<Stage_Item> mStageList;
    private MemorizationAdapter memorizationAdapter;
    private DBHelper mDBHelper;
    private int check=1, progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorization_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mDBHelper = new DBHelper(this);
        mWordItem = new ArrayList<>();
        mWordItem = mDBHelper.getWordList();

        mStageList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.mem_StageListRecyclerView);
        memorizationAdapter = new MemorizationAdapter(mStageList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(memorizationAdapter);

        int nothing = 0;
        for(int i = 1; i <= 30; i++){
            progress = 0;

            for(int j = (i - 1) * 30; j < (i - 1) * 30 +30; j++){
                if(mWordItem.get(j).getIsMark() == 0) {
                    progress++;
                }
            }
            if(progress == 30) {
                mWordItem = mDBHelper.getWordList();
                mStageList.add(new Stage_Item("" + i, "Complete", progress, nothing));//Stage ????????? ??????
            }
            else {
                mWordItem = mDBHelper.getWordList();
                mStageList.add(new Stage_Item("" + i, "Try", progress, nothing));
            }
        }
        memorizationAdapter.setmStageList(mStageList);

        memorizationAdapter.setMemOnItemClickListener(new MemorizationOnStageItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(MemorizationAdapter.ViewHolder holder, View v, int curPos) {
                if (!memorizationCheck(curPos)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    String[] strChoiceItems = {"??????"};
                    builder.setTitle("?????? ?????????????????????.");
                    builder.setItems(strChoiceItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int dialogposition) {
                            Log.d("MEMLOG","?????? ???????????????");
                            if (dialogposition == 0) {}
                        }
                    });
                    builder.create();
                    builder.show();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    String[] strChoiceItems = {"???", "?????????"};
                    builder.setTitle("????????? ?????????????????????????");
                    builder.setItems(strChoiceItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int dialogposition) {
                            if (dialogposition == 0) {
                                Log.d("MEMLOG","?????? ??????");
                                Intent intent = new Intent(v.getContext(), MemorizationActivity.class);
                                intent.putExtra("Stage", curPos);
                                startActivity(intent);
                            }
                            else if (dialogposition == 1) {}
                        }
                    });
                    builder.create();
                    builder.show();
                }
            }
        });




    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    private boolean memorizationCheck(int position){
        for(int i=position*30;i<position*30+30;i++) {
            if(mWordItem.get(i).getIsMark()==1)
                return true;
        }
        return false;
    }
}

