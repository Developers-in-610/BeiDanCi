package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
    TextView correctAnswer;
    TextView textView1;
    TextView textView2;

    int count;
    int time;
    int num;

    Words word;
    int n;
    AlertDialog.Builder dialog;
    private static int fence;
    private static final int COMPLETED = 0;


    public void actionStart(Context context, int f){
        Intent intent=new Intent(context,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("newFence",f);

        context.startActivity(intent);

    }
    private void setAlert(String s){

        dialog.setMessage(s);
        dialog.setCancelable(false);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                actionStart(ReviewLetter1.this,fence);

            }
        });
        dialog.show();
    }
    private void init(){
        count=1;
        n=0;
        chinese=(TextView)findViewById(R.id.chinese);
        answer=(EditText)findViewById(R.id.answer);
        submit=(Button)findViewById(R.id.submit);
        iKnow=(Button)findViewById(R.id.IKnow);
        dialog=new AlertDialog.Builder(ReviewLetter1.this);
        judge=(ImageView)findViewById(R.id.judge);
        correctAnswer=(TextView)findViewById(R.id.correct_answer);
        textView1=(TextView)findViewById(R.id.letterNum);
        textView2=(TextView)findViewById(R.id.correctNum);


    }

    private void flush(int pos){
        Words word=wordsArrayList.get(pos);
        chinese.setText(word.getChineses());
    }



    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what==COMPLETED){
                if(wordsArrayList.size()!=0){
                    if(count<wordsArrayList.size()){

                        flush(count);
                        count++;
                    }else {
                        count=1;

                        flush(count-1);
                    }

                    judge.setVisibility(View.INVISIBLE);
                    answer.setText("");
                    correctAnswer.setVisibility(View.INVISIBLE);
                    submit.setVisibility(View.VISIBLE);
                    iKnow.setVisibility(View.VISIBLE);
                }else {
                    setAlert("恭喜你！今天的复习任务已经完成啦！");
                }


            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_letter1);
        init();

        fence=getIntent().getIntExtra("Fence",0);


        if(DatabaseUtil.countNum(fence)<=0){
           setAlert("当前没有新词可复习，赶紧去背新词吧！");
           return;

        }else  if(DatabaseUtil.countNum(fence)<3){
            num=DatabaseUtil.countNum(fence);

        }else {
            num=3;
        }


        wordsArrayList=DatabaseUtil.ReviewWord(num,fence);

        ReviewLetterShare.print1(textView1,num);
        ReviewLetterShare.print2(textView2,0);

        word=wordsArrayList.get(0);
        chinese.setText(word.getChineses());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(n<num){

                    String ans=answer.getText().toString();

                        word=wordsArrayList.get(count-1);
                    submit.setVisibility(View.GONE);
                    iKnow.setVisibility(View.GONE);
                    if(ans.equals(word.getWord())){
                        judge.setImageResource(R.mipmap.correct);

                        time=500;
                        DatabaseUtil.SetWordvis(word,DatabaseUtil.UNKNOWN_WORD_CAN);
                        n++;
                        ReviewLetterShare.print2(textView2,n);
                        fence++;
                        wordsArrayList.remove(word);

                    }else {
                        judge.setImageResource(R.mipmap.wrong);
                        time=3000;
                        correctAnswer.setText(word.getWord());
                        correctAnswer.setVisibility(View.VISIBLE);
                        DatabaseUtil.SetWordvis(word,DatabaseUtil.UNKNOWN_WORD_CANT);

                    }

                    judge.setVisibility(View.VISIBLE);
                    Timer timer=new Timer();
                    TimerTask task=new TimerTask() {
                        @Override
                        public void run() {
                            Message msg = new Message();
                            msg.what=COMPLETED;
                            handler.sendMessage(msg);
                        }
                    };
                    timer.schedule(task,time);


                }else{
                    setAlert("恭喜你！今天的复习任务已经完成啦！");
                }

            }
        });

        iKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(n<num){
                    Words words=wordsArrayList.get(count-1);
                    DatabaseUtil.SetWordvis(words,DatabaseUtil.KNOWN_WORD);
                    n++;
                    wordsArrayList.remove(words);
                    ReviewLetterShare.print2(textView2,n);
                    if(wordsArrayList.size()!=0){
                        if(count<wordsArrayList.size()){
                            flush(count);
                            count++;
                        }else {
                            count=1;
                            flush(count-1);
                        }

                    }else {
                        setAlert("恭喜你！今天的复习任务已经完成啦！");
                    }


                }

            }
        });



    }
}
