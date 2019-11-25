package com.example.testlitepal;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

public class DatabaseUtil {
    //中文库用于复习
    public  static HashSet<String> chineselibrary=new HashSet<>();
    public  static HashSet<String> Englishlibraty=new HashSet<>();
    //英文库用于复习

     //用与查找背过或没有背过的单词
    //visornot --1背过 0没背过
    //num 查找的单词的个数
    public static ArrayList<Words> GetUnvistedWord(int visornot,int num) {
        SQLiteDatabase readableDatabase = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/" +
                DBManager.DB_NAME, null);
        Cursor c = readableDatabase.rawQuery("select * from words where vis = ?", new String[]{""+visornot});
        int getnum = 0;
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
            al.add(words);
            if (al.size()==getnum)
                break;
        }
        c.close();
        return al;
    }


    public static void SetWordvis(Words word){
        int id=word.getId();
        SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/" +
                DBManager.DB_NAME, null);
        db.execSQL("update words set vis=? where id=?",new String[]{"1",""+id});
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
            stnum.add(new Integer(random.nextInt())%mod);
        }
        String ans[]=new String[]{};
        Object []randomnum=stnum.toArray();
        Iterator iterator=chineselibrary.iterator();
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




}
