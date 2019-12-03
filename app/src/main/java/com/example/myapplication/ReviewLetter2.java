package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReviewLetter2 extends AppCompatActivity implements View.OnClickListener {

    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button ok;
    TextView textView;
    TextView need;
    TextView reviewed;
    AlertDialog.Builder dialog;
    private static int fence;
    private String question;
    private String answer;
    private int count;
    int num;
    int flag;
    SQLiteDatabase db;
    Cursor c;
    ArrayList<Words>QList;
    private static int n;

    public void actionStart(Context context, int f){
        Intent intent=new Intent(context,MainActivity.class);
        intent.putExtra("newFence",f);
        context.startActivity(intent);


    }


    private void setAlert(String s){

        dialog.setMessage(s);
        dialog.setCancelable(false);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                actionStart(ReviewLetter2.this,fence);
            }
        });
        dialog.show();
    }

    private void init(){
        c=null;
        n=0;
        num=0;
        count=1;
        flag=0;
        db=SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/" + DBManager.DB_NAME,null);
        dialog=new AlertDialog.Builder(ReviewLetter2.this);;
        button1=(Button)findViewById(R.id.answer1);
        button2=(Button)findViewById(R.id.answer2);
        button3=(Button)findViewById(R.id.answer3);
        button4=(Button)findViewById(R.id.answer4);
        ok=(Button)findViewById(R.id.okk);
        textView=(TextView)findViewById(R.id.chinese);
        need=(TextView)findViewById(R.id.letterNum);
        reviewed=(TextView)findViewById(R.id.correctNum);
    }

    private void disruptOrder(String[]s){
        for(int i=0;i<s.length;i++){
            int index=(int)(Math.random()*s.length);
            String temp=s[i];
            s[i]=s[index];
            s[index]=temp;
        }
    }

    private int[] getRandomNum(int n,int num){
        int[] intRandom = new int[num];
        List mylist = new ArrayList(); //生成数据集，用来保存随即生成数，并用于判断
        Random rd = new Random();
        while(mylist.size() < num) {
            int temp = rd.nextInt(n);
            if(!mylist.contains(temp)) {
                mylist.add(temp); //往集合里面添加数据。
            }

        }
        for(int i = 0;i <mylist.size();i++) {

            intRandom[i] = (Integer)(mylist.get(i));

        }
        return intRandom;
    }

    private String[] getChinese(Words word){
        String s[];
        s=new String[4];
        ArrayList<Words>words=DatabaseUtil.GetWord(DatabaseUtil.All,2088);
        s[0]=word.getWord();
        for(int j=1;j<4;j++){
            int index[]=getRandomNum(2088,3);
            if(index[j-1]==word.getId()) {
                j--;
            }else{
                Words w=words.get(index[j-1]);
                s[j]=w.getWord();
            }
        }
        return s;
    }

    private void flush(Words words){

        textView.setText(words.getChineses());
        String[]s;
        s=new String[4];
        s=getChinese(words);
        disruptOrder(s);

        button1.setText(s[0]);
        button2.setText(s[1]);
        button3.setText(s[2]);
        button4.setText(s[3]);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_letter2);
        init();

        fence=getIntent().getIntExtra("Fence",0);
        //Log.v("hhh","count:"+DatabaseUtil.countNum(fence));
        //Log.v("hh","letter_create: "+fence);

        if(DatabaseUtil.countNum(fence)<=0){
            setAlert("当前没有新词可复习，赶紧去背新词吧！");
            return;

        }else  if(DatabaseUtil.countNum(fence)<5){
            num=DatabaseUtil.countNum(fence);

        }else {
            num=5;
        }
        ReviewLetterShare.print1(need,num);
        ReviewLetterShare.print2(reviewed,0);
        QList=DatabaseUtil.ReviewWord(num,fence);
        flush(QList.get(0));

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {



        switch (view.getId()){
            case R.id.answer1:answer=button1.getText().toString();flag=0;break;
            case R.id.answer2:answer=button2.getText().toString();flag=0;break;
            case R.id.answer3:answer=button3.getText().toString();flag=0;break;
            case R.id.answer4:answer=button4.getText().toString();flag=0;break;
            case R.id.okk:DatabaseUtil.SetWordvis(QList.get(count-1),DatabaseUtil.KNOWN_WORD);
                QList.remove(count-1);
                n++;
                ReviewLetterShare.print2(reviewed,n);
               flag=1;
            default:break;
        }
//        Log.v("hh","count:"+count);
//        Log.v("hh","answer:"+answer);
//        Log.v("hh","word:"+QList.get(count-1).getWord());
        if(flag==0){
            if(answer.equals(QList.get(count-1).getWord())){

                DatabaseUtil.SetWordvis(QList.get(count-1),DatabaseUtil.UNKNOWN_WORD_CAN);
                n++;
                fence++;
                ReviewLetterShare.print2(reviewed,n);
                QList.remove(count-1);
            }else {
                DatabaseUtil.SetWordvis(QList.get(count-1),DatabaseUtil.UNKNOWN_WORD_CANT);
            }
        }

Log.v("hh","n:"+n);
        if(n==num){
            setAlert("今日任务已经完成啦！");
            return;
        }
        else {
            if(count<QList.size()){

                    flush(QList.get(count));
                count++;
            }else {
                count=1;

                flush(QList.get(count-1));
            }



        }

        }

//        Log.v("hh","n:"+n);
//        Log.v("hh","size:"+QList.size());




}
