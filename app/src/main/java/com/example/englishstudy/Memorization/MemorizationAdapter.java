package com.example.englishstudy.Memorization;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishstudy.R;
import com.example.englishstudy.Review.ReviewAdapter;
import com.example.englishstudy.global.DBHelper;
import com.example.englishstudy.global.Stage_Item;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MemorizationAdapter extends RecyclerView.Adapter<MemorizationAdapter.ViewHolder> implements MemorizationOnStageItemClickListener {

    private ArrayList<Stage_Item> mStageList;
    private Context mContext;
    private DBHelper mDBHelper;
    private MemorizationAdapter.OnItemClickListener onItemClickListener = null;
    MemorizationOnStageItemClickListener listener;

    @Override
    public void onItemClick(ReviewAdapter.ViewHolder holder, View view, int position){
        if(listener!=null)
            listener.onItemClick(holder,view,position);
    }

    @Override
    public void setMemOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void setmStageList(ArrayList<Stage_Item> list){
        this.mStageList = list;
        notifyDataSetChanged();
    }

    public MemorizationAdapter(ArrayList<Stage_Item> mStageList, Context mContext){
        this.mStageList = mStageList;
        this.mContext = mContext;
        this.mDBHelper = new DBHelper(mContext);
    }
    // Adapter 생성자


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.memorization_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.mem_StageNum.setText(mStageList.get(position).getStage());
        holder.mem_State.setText(mStageList.get(position).getRunning());
        holder.mem_progressBar1.setProgress(mStageList.get(position).getCorrect());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mem_StageNum;
        private TextView mem_State;
        private ProgressBar mem_progressBar1;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            mem_StageNum = itemView.findViewById(R.id.mem_stageNum1);
            mem_State = itemView.findViewById(R.id.mem_State);
            mem_progressBar1 = itemView.findViewById(R.id.mem_progressBar1);
            Log.d("MEMLOG","Progress Bar DB연동 완료");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int curPos = getAdapterPosition();
                    if(curPos != RecyclerView.NO_POSITION){
                        onItemClickListener.onItemClick(ViewHolder.this, v, curPos);
                        Log.d("MEMLOG","Stage " + curPos + "실행");
                    }
                }
            });
        }
    }




    @Override
    public int getItemCount() {
        return mStageList.size();
    }

}
