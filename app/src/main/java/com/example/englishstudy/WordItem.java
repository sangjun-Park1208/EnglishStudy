package com.example.englishstudy;

public class WordItem {
    private int id;
    private int day;
    private int wordNum;
    private int isMark;
    private String word;
    private String meaning;

//
//    public WordItem(int id, int day, int wordNum, int isMark, String word, String meaning) {
//        this.id = id;
//        this.day = day;
//        this.wordNum = wordNum;
//        this.isMark = isMark;
//        this.word = word;
//        this.meaning = meaning;
//
//    }

    public WordItem() {

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getWordNum() {
        return wordNum;
    }

    public void setWordNum(int wordNum) {
        this.wordNum = wordNum;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public int getIsMark() {
        return isMark;
    }

    public void setIsMark(int isMark) {
        this.isMark = isMark;
    }
}
