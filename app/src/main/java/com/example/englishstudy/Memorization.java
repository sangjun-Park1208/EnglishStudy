package com.example.englishstudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Memorization extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorization);

        RecyclerView recyclerView=findViewById(R.id.day_list);

        List<String> list=new ArrayList<>();
        for(int i=0;i<30;i++){
            list.add("stage"+i);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new CustomAdapter(list));
            recyclerView.addItemDecoration(new CustomItemDecoration());
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView textView;

        ViewHolder(View view){
            super(view);
            textView=view.findViewById(android.R.id.text1);
        }
       TextView getTextView(){return textView;}
    }

    private class CustomAdapter extends RecyclerView.Adapter<ViewHolder>{
        private List<String> dataSet;
        CustomAdapter(List<String>dataSet){this.dataSet=dataSet;}

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
            View view= LayoutInflater.from(viewGroup.getContext()).inflate(android.R.layout.simple_list_item_1,viewGroup,false);
            return new ViewHolder(view);
        }

        @Override
        public int getItemCount(){return dataSet.size();}

        @Override
        public void onBindViewHolder(ViewHolder viewHolder,final int position){
            viewHolder.getTextView().setText(dataSet.get(position));
        }
    }

    private class CustomItemDecoration extends RecyclerView.ItemDecoration{
        @Override
        public void onDraw(Canvas c,RecyclerView parent, RecyclerView.State state){
            super.onDraw(c,parent,state);
            int width=parent.getWidth();
            int height=parent.getHeight();
            Paint paint=new Paint();
        }
        @Override
        public void getItemOffsets(Rect outRect,View view,RecyclerView parent,RecyclerView.State state){
            super.getItemOffsets(outRect,view,parent,state);
            int index=parent.getChildAdapterPosition(view)+1;
            if (index%3==0) outRect.set(20,20,20,60);//여백
            else outRect.set(20,20,20,20);

            view.setBackgroundColor(0xFFECE9E9);
            ViewCompat.setElevation(view,20.0f);//떠있는듯 효과
        }
    }
}