package com.example.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class ReviewLetterShare {


    public static void print1(TextView t,int allNum){
        t.setText("今日需背单词数："+allNum);
    }
    public static void print2(TextView t,int num){
        t.setText("今日已背单词数："+num);
    }
    public static boolean jump(ArrayList<Words>list){
        for(int i=0;i<list.size();i++){
            if(list.get(i).getVisted()==DatabaseUtil.UNKNOWN_WORD_CANT){
                return false;

            }
        }
        return true;
    }


}
