package com.example.englishstudy.Memorization;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.englishstudy.R;
import com.example.englishstudy.global.DBHelper;
import com.example.englishstudy.global.StageItem;
import com.example.englishstudy.global.WordItem;

import java.util.ArrayList;

public class MemorizationList extends AppCompatActivity {
    private ArrayList<WordItem> mWordItem;
    private ArrayList<StageItem> mStageList;
    private MemorizationAdapter memorizationAdapter;
    private DBHelper mDBHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorization_list);

        mDBHelper = new DBHelper(this);
        mWordItem = new ArrayList<>();
        mWordItem = mDBHelper.getWordList();

        mStageList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.mem_StageListRecyclerView);
        memorizationAdapter = new MemorizationAdapter(mStageList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(memorizationAdapter);


        for(int i=1;i<=30;i++){
            mStageList.add(new StageItem(""+i, "complete"));
        }
        memorizationAdapter.setmStageList(mStageList);

        memorizationAdapter.setMemOnItemClickListener(new MemorizationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int curPos, Context mContext) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                String[] choice = {"O","X"};
                builder.setTitle("암기를 시작하시겠습니까?");
                builder.setItems(choice, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0){
                            Intent intent = new Intent(v.getContext(), MemorizationActivity.class);
                            intent.putExtra("Stage", curPos);
                            startActivity(intent);
                        }
                        else if(which == 1){}
                    }
                });
                builder.create();
                builder.show();
            }
        });
    }
}