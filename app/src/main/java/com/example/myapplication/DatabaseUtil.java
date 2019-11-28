package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

public class DatabaseUtil {
    //中文库用于复习
    public  static HashSet<String> chineselibrary=new HashSet<>();
    public  static HashSet<String> Englishlibraty=new HashSet<>();
    //英文库用于复习

    public static final int NEW_WORD=0;//未学习的新词
    public static final int UNKNOWN_WORD_CAN=1;//学了会但还需要复习的
    public static final int UNKNOWN_WORD_CANT=2;//学了但还不会的单词
    public static final int KNOWN_WORD=3;//已经学会的单词
    public static final int All=4;//全部单词
    //用与查找背过或没有背过的单词

    //num 查找的单词的个数


    public static ArrayList<Words>ReviewWord(int num,int fence){
        SQLiteDatabase database=SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/" + DBManager.DB_NAME, null);
        Cursor c=null;
        c=database.rawQuery("select * from words where vis=? or vis=?",new String[]{""+UNKNOWN_WORD_CAN ,""+UNKNOWN_WORD_CANT});
        ArrayList<Words> al = new ArrayList<Words>();
        Log.v("hh","reviewWord:"+c.getCount());
        int count=0;
        if(c.moveToFirst()){
            do{

                if(count>=fence){
                    int _id = c.getInt(c.getColumnIndex("id"));
                    String word = c.getString(c.getColumnIndex("word"));
                    String chineses = c.getString(c.getColumnIndex("chineses"));
                    Words words = new Words();
                    words.setId(_id);
                    words.setWord(word);
                    words.setChineses(chineses);
                    words.setVisted(c.getInt(c.getColumnIndex("vis")));
                    al.add(words);
                    if (al.size()==num)
                        break;
                }
                count++;
            }while (c.moveToNext());
        }
        c.close();
        return al;
    }

    public static int countNum(int f){

        SQLiteDatabase database=SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/" + DBManager.DB_NAME, null);
        Cursor c=null;
        c=database.rawQuery("select * from words where vis=? or vis=?",new String[]{""+UNKNOWN_WORD_CAN ,""+UNKNOWN_WORD_CANT});

        return c.getCount()-f;
    }



    public static ArrayList<Words> GetWord(int visornot,int num) {
        SQLiteDatabase readableDatabase = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/" +
                DBManager.DB_NAME, null);
        Cursor c=null;
        if (visornot==All) {
             c = readableDatabase.query("words",null, null,null,null,null,null);
        } else {
            c = readableDatabase.rawQuery("select * from words where vis = ?", new String[]{"" + visornot});
        }
        ArrayList<Words> al = new ArrayList<Words>();
        while (c.moveToNext()) {
            int _id = c.getInt(c.getColumnIndex("id"));
            String word = c.getString(c.getColumnIndex("word"));
            String chineses = c.getString(c.getColumnIndex("chineses"));
            chineselibrary.add(chineses);
            Englishlibraty.add(word);
            Words words = new Words();
            words.setId(_id);
            words.setWord(word);
            words.setChineses(chineses);
            words.setVisted(c.getInt(c.getColumnIndex("vis")));
            al.add(words);
            if (al.size()==num)
                break;

        }
        c.close();
        return al;
    }


    public static void SetWordvis(Words word,int flag){
        int id=word.getId();
        SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/" +
                DBManager.DB_NAME, null);
        db.execSQL("update words set vis=? where id=?",new String[]{""+flag,""+id});
    }

    public  static String[] geteng(Words words,int num){
        int mod=Englishlibraty.size();
        String wordeng=words.getWord();
        Random random = new Random();
        HashSet<Integer> stnum=new HashSet<Integer>();
        while(stnum.size()<num){
            stnum.add(new Integer(random.nextInt())%mod);
        }
        String ans[]=new String[]{};
        Object []randomnum=stnum.toArray();
        Iterator iterator=Englishlibraty.iterator();
        int count=0;
        int index=0;
        while (iterator.hasNext()&&index<num){
            if(count==(int) randomnum[index]){
                String tmp=(String)iterator.next();
                if(!tmp.equals(wordeng)) {
                    ans[index] =tmp;
                    index++;
                }
            }
            count++;
        }
        return ans;
    }
    public  static String[] getchinese(Words words,int num){
        int mod=chineselibrary.size();
        String wordeng=words.getChineses();
        Random random = new Random();
        HashSet<Integer> stnum=new HashSet<Integer>();
        while(stnum.size()<num){

            stnum.add(new Integer(random.nextInt(mod)));
        }

        String [] ans=new String[num] ;
        Object []randomnum=stnum.toArray();
        Iterator iterator=chineselibrary.iterator();
        int count=0;
        int index=0;
        while (iterator.hasNext()&&index<num){
            if(count==(int) randomnum[index]){
                String tmp=(String)iterator.next();
                if(!tmp.equals(wordeng)) {
                    ans[index] =tmp;

                }
                else{
                    ans[index]=new String("备用的");
                }
                index++;
            }
            count++;
        }
        return ans;
    }





}
