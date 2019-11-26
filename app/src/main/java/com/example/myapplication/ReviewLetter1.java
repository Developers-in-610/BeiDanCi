package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ReviewLetter1 extends AppCompatActivity {

    TextView chinese;
    EditText answer;
    Button submit;
    Button iKnow;
    ImageView judge;
    ArrayList<Words> wordsArrayList;
    int count=1;
    AlertDialog.Builder dialog;
    private static final int COMPLETED = 0;

    private void init(){
        wordsArrayList=DatabaseUtil.GetWord(DatabaseUtil.NEW_WORD,10);
        chinese=(TextView)findViewById(R.id.chinese);
        answer=(EditText)findViewById(R.id.answer);
        submit=(Button)findViewById(R.id.submit);
        iKnow=(Button)findViewById(R.id.IKnow);
        dialog=new AlertDialog.Builder(ReviewLetter1.this);
        judge=(ImageView)findViewById(R.id.judge);

    }

    private void flush(int pos){
        Words word=wordsArrayList.get(pos);
        chinese.setText(word.getChineses());
    }

    private void setAlert(String s){

        dialog.setMessage(s);
        dialog.setCancelable(false);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(ReviewLetter1.this,MainActivity.class));
            }
        });
        dialog.show();
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what==COMPLETED){
                flush(count);
                count++;
                judge.setVisibility(View.INVISIBLE);
                answer.setText("");
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_letter1);
        init();
        int num=0,fence=1;
        //Log.v("hhh",""+DatabaseUtil.countNum());
        if(DatabaseUtil.countNum()<=-1){
           setAlert("当前没有新词可复习，赶紧去背新词吧！");
        }else  if(DatabaseUtil.countNum()<15){
            num=DatabaseUtil.countNum();
        }else {
            num=15;
        }
        Words word=wordsArrayList.get(0);
        chinese.setText(word.getChineses());

        //wordsArrayList=DatabaseUtil.ReviewWord(num,fence);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count<wordsArrayList.size()){
                    String ans=answer.getText().toString();
                    Words word=wordsArrayList.get(count-1);
                    if(ans.equals(word.getWord())){
                        judge.setImageResource(R.mipmap.correct);
                    }else {
                        judge.setImageResource(R.mipmap.wrong);
                    }

                    judge.setVisibility(View.VISIBLE);
                    Timer timer=new Timer();
                    TimerTask task=new TimerTask() {
                        @Override
                        public void run() {
                            Message msg = new Message();
                            msg.what = COMPLETED;
                            handler.sendMessage(msg);
                        }
                    };
                    timer.schedule(task,500);


                }else{
                    setAlert("恭喜你！今天的复习任务已经完成啦！");
                }

            }
        });

        iKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count<wordsArrayList.size()){
                    flush(count);
                    count++;
                }else {
                    setAlert("恭喜你！今天的复习任务已经完成啦！");
                }
            }
        });



    }
}
