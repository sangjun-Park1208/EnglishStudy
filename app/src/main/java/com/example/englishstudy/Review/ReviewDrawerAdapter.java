package com.example.englishstudy.Review;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishstudy.R;

import java.util.ArrayList;

public class ReviewDrawerAdapter extends RecyclerView.Adapter<ReviewDrawerAdapter.ViewHolder> implements ReviewVocaItemClickListener{
    //아이템 목록
    private ArrayList<ReviewVocaItem> mVocaList;
    ReviewVocaItemClickListener listener;

    //어댑터 뷰에 전달할 내용들
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.review_drawer_item,parent,false);
        return new ViewHolder(view,this);
    }

    @Override//데이터를 view와 연결
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(mVocaList.get(position));
    }

    public void setOnItemClicklistener(ReviewVocaItemClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position){
        if(listener!=null)
            listener.onItemClick(holder,view,position);
    }

    public void setmVocaList(ArrayList<ReviewVocaItem> list){
        this.mVocaList=list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mVocaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView voca;

        public ViewHolder(@NonNull View itemView, final ReviewVocaItemClickListener listener){
            super(itemView);

            voca=(TextView) itemView.findViewById(R.id.review_drawer_item);

            itemView.setOnClickListener(new View.OnClickListener() {//아이템에 클릭이벤트 넣어주기
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(listener!=null){
                        listener.onItemClick(ViewHolder.this,v,position);
                    }
                }
            });
        }

        public void onBind(ReviewVocaItem vocaItem){
            voca.setText(vocaItem.getVoca());
        }

    }

}
