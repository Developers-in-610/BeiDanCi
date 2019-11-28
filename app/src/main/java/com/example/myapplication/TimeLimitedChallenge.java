package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TimeLimitedChallenge extends AppCompatActivity {
    private TextView timeLimit;
    private ArrayList<Words> al;
    private List<String> chineses;
    private int index;
    private Button choice1;
    private Button choice2;
    private int score;
    private TextView tvenglish;
    private Words nowword;
    private int knownum;
    public static int totalnum;
    private Button choice3;
    private Button choice4;
    private int fence;


    private class Counter implements Runnable
    {
        Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            timeLimit.setText(msg.what + "s");
            if (msg.what == 0) {
                // 倒计时结束
                showDialog("挑战结束，得分：");
            }
        }
    };
        @Override
        public void run() {
            for (int i = 20; i >= 0; i--) {
                handler.sendEmptyMessage(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void showDialog(String s)
    {
        AlertDialog.Builder rule=new AlertDialog.Builder(this);
        String string=s;
        rule.setMessage(string);
        rule.setPositiveButton("再次挑战",click1);
        rule.setNegativeButton("退出挑战",click2);
        rule.create().show();
    }
    private DialogInterface.OnClickListener click1=new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            startActivity(new Intent(TimeLimitedChallenge.this,TimeLimitedChallenge.class));
        }
    };
    private DialogInterface.OnClickListener click2=new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            startActivity(new Intent(TimeLimitedChallenge.this,ReviewLetterMain.class));
        }
    };
    public int rand()
    {
        return (int)(0+Math.random()*(al.size()-0+1));

    }
    public void init() {
        knownum = 0;
        totalnum = 0;
        al = DatabaseUtil.GetALLWord();
        totalnum = al.size();
        if (totalnum < 4)
            showDialog("单词数量不足以进行挑战");
        else{
            Collections.shuffle(al);
            index = 0;
            choice1 = (Button) findViewById(R.id.answer1);
            choice2 = (Button) findViewById(R.id.answer2);
            choice3 = (Button) findViewById(R.id.answer3);
            choice4 = (Button) findViewById(R.id.answer4);
            tvenglish = (TextView) findViewById(R.id.eng);
            nowword = al.get(index);
            tvenglish.setText(nowword.getWord());
            chineses = new LinkedList<String>() {{
                add(nowword.getChineses());
                add(al.get(index + 1).getChineses());
                add(al.get(index + 2).getChineses());
                add(al.get(index + 3).getChineses());
            }};

            Collections.shuffle(chineses);
            choice1.setText(chineses.get(0));
            choice2.setText(chineses.get(1));
            choice3.setText(chineses.get(2));
            choice4.setText(chineses.get(3));
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_challenge1);
        init();
        timeLimit = (TextView) findViewById(R.id.time);

        Counter counter = new Counter();
        Thread counter1 = new Thread(counter, "计时");
        counter1.start();




                }

            }





