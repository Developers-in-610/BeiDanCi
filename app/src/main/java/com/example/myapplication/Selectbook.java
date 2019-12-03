package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

public class Selectbook extends Activity {

    public static final String []cetbook={"CET4words.db","words.db"};
    public final int wordsnum[]={3159,2089};
    public final int daywordsnum[]={10,20,30,40};
    public  static int totalword=2089;
    public static boolean needRunMain = false;
    public static final String  sharename="wordsdata";
    public static  final  String sharedbnamekey="db_name";
    public  static  final String sharedaywordkey="daywords";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Log.d("Select","oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectbook);
        Spinner sbook=(Spinner)findViewById(R.id.sbook);
        Spinner sday=(Spinner) findViewById(R.id.sday);
        Button check=(Button) findViewById(R.id.checkdone);
        sbook.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DBManager.DB_NAME=cetbook[position];
//                totalword=wordsnum[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sday.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.daywords=daywordsnum[position];
//                MainActivity.left_word_total=totalword;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("fuck","fucked");
                SharedPreferences sharedPreferences=getSharedPreferences(sharename, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString(sharedbnamekey,DBManager.DB_NAME);
                editor.putInt(sharedaywordkey,MainActivity.daywords);
                editor.apply();
                if(needRunMain){
                    Intent it = new Intent(Selectbook.this,MainActivity.class);
                    startActivity(it);
                }
                finish();
                return;
            }
        });





    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
}
