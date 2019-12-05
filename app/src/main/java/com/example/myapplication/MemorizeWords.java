package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;

public class MemorizeWords extends AppCompatActivity {
    private ArrayList<Words> al;
    private int index;
    private Button bknow;
    private int todayleftwordnum;
    private Button bunknow;
    private TextView tvhintchinese,tvnewword,tvokword,tvdayword01,tvdayword02;
    private TextView tvenglish;
    private Words nowword;
    private int knownum,newwordnum;
    public static int totalnum;
    private Button bthrowit;
    private Button bplayvoice;
    public  static final String shkonwwordkey="knownum";
    public  static final String shnewwordkey="newwordnum";
    public  String timekey;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorize_words);
        init();
        bknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(knownum>=totalnum){
                    finish();
                    return;
                }
                nextword(1);
            }
        });
        bunknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String text=bunknow.getText().toString();
                if(text.equals("提示一下吧")){
                    tvhintchinese.setText(nowword.getChineses());
                    bknow.setText("记熟了！");
                    bunknow.setText("还是不太熟");
                }
                else if(text.equals("还是不太熟")){
                    nextword(2);
                }
            }
        });
        bthrowit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog();
            }
        });
        bplayvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startvoice();
            }
        });





    }
    //初始化
    public void init(){
        totalnum=MainActivity.daywords;
        bknow=(Button) findViewById(R.id.knowbutton);
        bunknow=(Button) findViewById(R.id.unknowbutton);
        bthrowit=(Button) findViewById(R.id.throwit);
        bplayvoice=(Button) findViewById(R.id.voiceplay);
        tvhintchinese=(TextView) findViewById(R.id.hintchinese);
        tvenglish=(TextView) findViewById(R.id.english);
        tvokword=(TextView) findViewById(R.id.okword);
        tvnewword=(TextView) findViewById(R.id.newword);
        Updatenowword();
        tvdayword01=(TextView) findViewById(R.id.daywords01);
        tvdayword02=(TextView) findViewById(R.id.dayword02);
        tvdayword02.setText(""+totalnum);
        tvdayword01.setText(""+totalnum);
        index=0;

        nowword=al.get(index);
        tvenglish.setText(nowword.getWord());
        bknow.setText("我认识");
        bunknow.setText("提示一下吧");
        if(!NetworkUtil.isNetworkAvailable(this)) {
            bplayvoice.setEnabled(false);
            Toast.makeText(this, "网络不可用，无法发音", Toast.LENGTH_SHORT).show();
        }

    }

    //新单词操作
    public void nextword(int flag){
        if(nowword.getVisted()==0) {
            newwordnum++;
            tvnewword.setText(""+newwordnum);
        }
        nowword.setVisted(flag);
        DatabaseUtil.SetWordvis(nowword,flag);
        if(flag==1||flag==3){
            knownum++;
            tvokword.setText(""+knownum);
            if(knownum==totalnum){
                showfinishdialog();
                return;
            }
        }
        index=(index+1)%todayleftwordnum;
        while (al.get(index).getVisted()==1||al.get(index).getVisted()==3) {
            index = (index + 1) % todayleftwordnum;
        }
        nowword=al.get(index);
        tvhintchinese.setText("");
        tvenglish.setText(nowword.getWord());
        bknow.setText("我认识");
        bunknow.setText("提示一下吧");

    }
    public  void showdialog(){
        AlertDialog.Builder bb=new AlertDialog.Builder(this);
        bb.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                nextword(3);
            }
        });
        bb.setNegativeButton("按错了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        bb.setMessage("你确定要丢掉"+nowword.getWord()+" "+nowword.getChineses());
        bb.setTitle("提示");
        bb.setCancelable(false);
        bb.show();

    }
    public  void showfinishdialog(){
        AlertDialog.Builder bbb=new AlertDialog.Builder(this);
        bbb.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        bbb.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        bbb.setTitle("消息");
        bbb.setMessage("完成啦！");
        bbb.setCancelable(false);
        bbb.show();
//        finish();
//        return;

    }
    public void  ShowExtendWordsDialog(){
        AlertDialog.Builder bbb=new AlertDialog.Builder(this);
        bbb.setPositiveButton("好", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        bbb.setTitle("消息");
        bbb.setMessage("今日已经完成啦！休息一下吧");
        bbb.setCancelable(false);
        bbb.show();


    }

    public void startvoice(){
        Intent intent = new Intent(this, wordvoice.class);
        intent.putExtra("query", nowword.getWord());
        startService(intent);


    }
    private void Updatenowword(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        timekey=year+""+month+""+day;
        SharedPreferences sharedPreferences=getSharedPreferences(timekey,MODE_PRIVATE);
        if(sharedPreferences.getInt(shkonwwordkey,0)!=0){
            knownum=sharedPreferences.getInt(shkonwwordkey,0);
            newwordnum=knownum;

        }
        else{
            knownum=0;
            newwordnum=0;

        }
        tvokword.setText(""+knownum);
        tvnewword.setText(""+newwordnum);
        if(knownum==MainActivity.daywords){
            ShowExtendWordsDialog();
        }
        al=DatabaseUtil.GetWord(0,totalnum-knownum);
        todayleftwordnum=totalnum-knownum;








    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences sharedPreferences=getSharedPreferences(timekey,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(shkonwwordkey,knownum);
        editor.putInt(shnewwordkey,newwordnum);
        editor.apply();

    }
}
