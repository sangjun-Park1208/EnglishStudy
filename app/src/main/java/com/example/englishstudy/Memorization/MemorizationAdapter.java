package com.example.englishstudy.Memorization;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishstudy.R;
import com.example.englishstudy.Test.TestAdapter;
import com.example.englishstudy.global.DBHelper;
import com.example.englishstudy.global.Stage_Item;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MemorizationAdapter extends RecyclerView.Adapter<MemorizationAdapter.ViewHolder> {

    private ArrayList<Stage_Item> mStageList;
    private Context mContext;
    private DBHelper mDBHelper;
    private MemorizationAdapter.OnItemClickListener onItemClickListener = null;


    public interface OnItemClickListener {
        void onItemClick(View v, int curPos, Context mContext);
    }
    // 클릭 시 클릭된 위치와 해당 Context 를 넘겨 처리해 주는 인터페이스

    public void setMemOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

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


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mem_StageNum;
        private TextView mem_completeOrNot;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            mem_StageNum = itemView.findViewById(R.id.mem_stageNum1);
            mem_completeOrNot = itemView.findViewById(R.id.mem_completeOrNot);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int curPos = getAdapterPosition();
                    if(curPos != RecyclerView.NO_POSITION){
                        onItemClickListener.onItemClick(v, curPos, mContext);
                    }
                }
            });
        }
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.mem_StageNum.setText(mStageList.get(position).getStage());
        holder.mem_completeOrNot.setText(mStageList.get(position).getRunning());
    }

    @Override
    public int getItemCount() {
        return mStageList.size();
    } // 총 Stage 개수 리턴

}
