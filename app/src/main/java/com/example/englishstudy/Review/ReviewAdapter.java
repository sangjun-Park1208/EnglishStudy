package com.example.englishstudy.Review;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishstudy.R;
import com.example.englishstudy.global.Stage_Item;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> implements ReviewOnStageItemClickListener {

    private int oldPosition=-1;
    private int selectedPosition=-1;

    private ArrayList<Stage_Item> mDayList;
    ReviewOnStageItemClickListener listener;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list,parent,false);
        return new ViewHolder(view,this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(mDayList.get(position));
    }

    public void setOnItemClicklistener(ReviewOnStageItemClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position){
        if(listener!=null)
            listener.onItemClick(holder,view,position);
    }

    public void setmDayList(ArrayList<Stage_Item> list){
        this.mDayList=list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView stage;
        TextView p_f;
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView, final ReviewOnStageItemClickListener listener) {
            super(itemView);

            stage=(TextView) itemView.findViewById(R.id.tv_number_review);
            p_f=(TextView) itemView.findViewById(R.id.tv_address_review);
            progressBar=(ProgressBar) itemView.findViewById(R.id.review_prgressbar);

            itemView.setOnClickListener(new View.OnClickListener(){//아이템에 클릭이벤트 넣어주기
                @Override
                public void onClick(View v){
                    int position = getAdapterPosition();
                    if(listener!=null){
                        listener.onItemClick(ViewHolder.this,v,position);
                    }
                }
            });
        }

        public void isSelected(ReviewAdapter.ViewHolder viewHolder, int position){//이부분 질문
            if(selectedPosition == position){
                viewHolder.itemView.setBackgroundColor(Color.GRAY);
            }
            else{
                viewHolder.itemView.setBackgroundColor(Color.TRANSPARENT);
            }

            viewHolder.itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    oldPosition = selectedPosition;
                    selectedPosition = position;

                    notifyItemChanged(oldPosition);
                    notifyItemChanged(selectedPosition);
                    return false;
                }

            });

        }

        public void onBind(Stage_Item stageItem) {
            stage.setText(stageItem.getStage());
            p_f.setText(stageItem.getRunning());
            progressBar.setProgress(30-stageItem.getCorrect());
        }
    }
}
