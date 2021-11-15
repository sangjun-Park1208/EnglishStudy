package com.example.englishstudy;

import android.content.Context;

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


