package com.example.englishstudy.Memorization;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishstudy.R;



import java.util.ArrayList;

public class MemorizationDrawerAdapter extends RecyclerView.Adapter<MemorizationDrawerAdapter.ViewHolder> implements MemorizationVocaItemClickListener{

    private ArrayList<MemorizationVocaItem> mVocaList;
    MemorizationVocaItemClickListener listener;

//    public MemorizationDrawerAdapter(ArrayList<MemorizationVocaItem> mVocaList, MemorizationVocaItemClickListener listener) {
//        this.mVocaList = mVocaList;
//        this.listener = listener;
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.memorization_drawer_item, parent, false);
        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(mVocaList.get(position));
    }

    public void setOnItemClicklistener(MemorizationVocaItemClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder, view, position);
        }
    }


    public void setmVocaList(ArrayList<MemorizationVocaItem> list){
        this.mVocaList=list;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return mVocaList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView voca;
        public ViewHolder(@NonNull View itemView, final MemorizationVocaItemClickListener listener) {
            super(itemView);
            voca = itemView.findViewById(R.id.mem_drawer_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(listener!=null){
                        listener.onItemClick(ViewHolder.this,v,position);
                    }
                }
            });
        }

        public void onBind(MemorizationVocaItem vocaItem){
            voca.setText(vocaItem.getVoca());
        }

    }
}
