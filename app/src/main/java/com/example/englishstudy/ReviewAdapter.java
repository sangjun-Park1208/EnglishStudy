package com.example.englishstudy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private ArrayList<ReviewDayItem> mDayList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(mDayList.get(position));
    }

    public void setmDayList(ArrayList<ReviewDayItem> list){
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            stage=(TextView) itemView.findViewById(R.id.tv_number_review);
            p_f=(TextView) itemView.findViewById(R.id.tv_address_review);
        }

        public void onBind(ReviewDayItem reviewDayItem) {
            stage.setText(reviewDayItem.getStage());
            p_f.setText(reviewDayItem.getRunnig());
        }
    }
}
