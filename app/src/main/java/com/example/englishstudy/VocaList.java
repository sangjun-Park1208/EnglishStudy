package com.example.englishstudy;

import com.example.englishstudy.global.DBHelper;
import com.example.englishstudy.global.WordItem;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class VocaList {//넣을 단어들
    private String[] word=new String[900];
    private String[] meaning=new String[900];
    private String wordtoken="apple,banana,grape";
    private String meaingtoken="사과,바나나,포도";

    private StringTokenizer tword=new StringTokenizer(wordtoken,", ");
    private StringTokenizer tmeaning=new StringTokenizer(meaingtoken,", ");

    public VocaList() {
        for(int i=0;i<900;i++){
            word[i]=tword.nextToken();
            meaning[i]=tmeaning.nextToken();
        }
    }

    public String getWord(int index) {
        return word[index];
    }

    public String getMeaning(int index) {
        return meaning[index];
    }
}
