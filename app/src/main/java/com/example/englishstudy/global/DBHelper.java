package com.example.englishstudy.global;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.englishstudy.global.WordItem;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "words.db";

    public DBHelper(@Nullable Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    //DB가 생성될 때 호출
    @Override
    public void onCreate(SQLiteDatabase db) {

        //DB -> 테이블 -> 컬럼 -> 값
        //값을 하나씩 불러올 때 고유 번호 존재. 그 값을 id에 넣을 것임 , AUTOINCREMENT 자동증가 옵션, NOT NULL 빈 값 허용X
        //컬럼 규약
        db.execSQL("CREATE TABLE IF NOT EXISTS WordList(id INTEGER PRIMARY KEY AUTOINCREMENT, day INTEGER NOT NULL, wordNum INTEGER NOT NULL,isMark INTEGER NOT NULL, word TEXT NOT NULL,meaning TEXT NOT NULL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }


    //SELECT 문 (영단어 목록 조회)
    public ArrayList<WordItem> getWordList(){
        ArrayList<WordItem> wordItems = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        //Word에서 단어를 가져온다 writeData를 내림차순해서
        Cursor cursor = db.rawQuery("SELECT * FROM WordList ORDER BY id DESC",null);

        //조회한 데이터가 있을 때 내부 수행
        if(cursor.getCount() != 0){
            while (cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                int day = cursor.getInt(cursor.getColumnIndex("day"));
                int wordNum = cursor.getInt(cursor.getColumnIndex("wordNum"));
                int isMark = cursor.getInt(cursor.getColumnIndex("isMark"));
                String word = cursor.getString(cursor.getColumnIndex("word"));
                String meaning = cursor.getString(cursor.getColumnIndex("meaning"));

                WordItem wordItem = new WordItem();
                wordItem.setId(id);
                wordItem.setDay(day);
                wordItem.setWordNum(wordNum);
                wordItem.setIsMark(isMark);
                wordItem.setWord(word);
                wordItem.setMeaning(meaning);
                wordItems.add(wordItem);

            }
        }
        cursor.close();

        return wordItems;
    }


    //INSERT 문 (단어를 DB에 넣는다.)
    public void InsertWord(int _day, int _wordNum, int _isMark, String _word, String _meaning){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO WordList(day,wordNum,isMark,word,meaning ) VALUES('"+_day+"','"+_wordNum+"','"+_isMark+"','"+_word+"','"+_meaning+"');");
    }

    //UPDATE 문 (단어를 수정한다.)
    public void  UpdateWord(int _day, int _wordNum, int _isMark, String _word, String _meaning, int _id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO WordList SET day= '"+_day+"',wordNum= '"+_wordNum+"',isMark= '"+_isMark+"',word= '"+_word+"',meaning= '"+_meaning+"' WHERE id = '"+_id+"'");
    }


    //DELETE 문 (단어를 DB에서 제거 한다.)
    public void  DeleteWord(int _id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM WordList WHERE id ='"+_id+"'");
    }
}

