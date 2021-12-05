package com.example.englishstudy.Memorization;

import android.view.View;


import com.example.englishstudy.Review.ReviewAdapter;
import com.example.englishstudy.global.Stage_Item;


import java.util.ArrayList;

public interface MemorizationOnStageItemClickListener {

    void onItemClick(ReviewAdapter.ViewHolder holder, View view, int position);

    void setMemOnItemClickListener(OnItemClickListener onItemClickListener);

    void setmStageList(ArrayList<Stage_Item> list);


    interface OnItemClickListener {
        void onItemClick(MemorizationAdapter.ViewHolder holder, View v, int curPos);
    }
}
