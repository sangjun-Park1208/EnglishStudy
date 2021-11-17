package com.example.englishstudy.Memorization;

import android.content.Context;

import com.example.englishstudy.global.DBHelper;
import com.example.englishstudy.global.WordItem;

import java.util.ArrayList;

public class CustomAdapter {
    private ArrayList<WordItem> mwordItems;
    private Context mcontext;
    private DBHelper mDBHelper;

    public CustomAdapter(ArrayList<WordItem> mwordItems, Context mcontext) {
        this.mwordItems = mwordItems;
        this.mcontext = mcontext;
        mDBHelper=new DBHelper(mcontext);
    }



}


