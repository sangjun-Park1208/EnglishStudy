package com.example.englishstudy.Test;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishstudy.R;
import com.example.englishstudy.global.DBHelper;
import com.example.englishstudy.global.Stage_Item;
import com.example.englishstudy.global.WordItem;

import java.util.ArrayList;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder>
{

    private ArrayList<Stage_Item> mDayList = new ArrayList<Stage_Item>();
    private Context mContext;
    private DBHelper mDBHelper;

    public TestAdapter(ArrayList<Stage_Item> mDayList, Context mContext) {
        this.mDayList = mDayList;
        this.mContext = mContext;
        mDBHelper = new DBHelper(mContext);
    }



    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.test_list,parent,false);


        return  new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.text_day.setText(mDayList.get(position).getStage());
        viewHolder.text_state.setText(mDayList.get(position).getRunning());
    }

    @Override
    public int getItemCount() {
        return mDayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text_day;
        private TextView text_state;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text_day = itemView.findViewById(R.id.text_day);
            text_state = itemView.findViewById(R.id.text_state);

        }
    }

}
