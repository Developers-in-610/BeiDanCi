package com.example.testlitepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import org.litepal.LitePal;
import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public SQLiteDatabase readableDatabase;
    public DBManager dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper
                = new DBManager(this);
        dbHelper.openDatabase();
        ArrayList<Words> al= DatabaseUtil.GetWord(0,5);
        for(Words words:al){
            Log.i("DB","WORD"+words.getId()+"visted"+words.getVisted());
        }
        Words words=al.get(0);
        DatabaseUtil.SetWordvis(words);
        ArrayList<Words> tmp=DatabaseUtil.GetWord(1,2);
        String [] str=DatabaseUtil.getchinese(words,3);
        for(String i:str){
            Log.i("chine",i);
        }



    }
}


