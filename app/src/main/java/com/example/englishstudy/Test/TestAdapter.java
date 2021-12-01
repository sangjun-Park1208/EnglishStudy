package com.example.englishstudy.Test;


import android.content.Context;
import android.graphics.Color;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishstudy.R;
import com.example.englishstudy.global.DBHelper;
import com.example.englishstudy.global.Stage_Item;

import org.w3c.dom.Text;

import java.util.ArrayList;

//RecyclerView 를 위한 Adapter
public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder>
{
    private int oldPosition = -1;
    private int selectedPosition = -1;


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
        String correct = Integer.toString(mDayList.get(position).getCorrect())+"/30";
        viewHolder.text_day.setText(mDayList.get(position).getStage());
        viewHolder.text_state.setText(mDayList.get(position).getRunning());
        viewHolder.test_StageProgress.setProgress(mDayList.get(position).getCorrect());
        viewHolder.test_tv_Running.setText("진행률: "+correct);
        //isSelected(viewHolder,position);
       //selectOnKey(viewHolder,position);
    }

    @Override
    public int getItemCount() {
        return mDayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text_day;
        private TextView text_state;
        private TextView test_tv_Running;
        private ProgressBar test_StageProgress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text_day = itemView.findViewById(R.id.text_day);
            text_state = itemView.findViewById(R.id.text_state);
            test_StageProgress = itemView.findViewById(R.id.test_StageProgress);
            test_tv_Running = itemView.findViewById(R.id.test_tv_Running);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int curPos = getAdapterPosition();
                    if(curPos!= RecyclerView.NO_POSITION){
                        //RecyclerView Item 클릭 시 TestList.class 로 전달
                        onItemClickListener.onItemClick(v,curPos, mContext);

                    }
                }
            });

        }
    }

    //RecyclerView Item 클릭 이벤트를 TestList.class 에서 처리하기 위한 Interface
    public interface OnItemClickListener{
        void onItemClick(View view, int curPos, Context mContext);
    }

    private OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

//    public void isSelected(ViewHolder viewHolder, int position){
//        if(selectedPosition == position){
//            viewHolder.itemView.setBackgroundColor(Color.GRAY);
//        }
//        else{
//            viewHolder.itemView.setBackgroundColor(Color.TRANSPARENT);
//        }
//
//        viewHolder.itemView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                oldPosition = selectedPosition;
//                selectedPosition = position;
//
//                notifyItemChanged(oldPosition);
//                notifyItemChanged(selectedPosition);
//                return false;
//            }
//
//        });
//
//    }

//    public void selectOnKey(ViewHolder viewHolder, int position){
//
//        viewHolder.itemView.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if((event.getAction() == KeyEvent.ACTION_DOWN)&&(keyCode == KeyEvent.KEYCODE_ENTER)){
//                    isSelected(viewHolder,position);
//
//                }
//                return false;
//            }
//        });
//
//    }


}
