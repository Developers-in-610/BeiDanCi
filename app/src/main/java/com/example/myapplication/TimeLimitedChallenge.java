package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class TimeLimitedChallenge extends AppCompatActivity {
    private TextView timeLimit;



    private class Counter implements Runnable
    {
        Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            timeLimit.setText(msg.what + "s");
            if (msg.what == 0) {
                // 倒计时结束
                showDialog();
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
    private void showDialog()
    {
        AlertDialog.Builder rule=new AlertDialog.Builder(this);
        String string="本次得分";
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_challenge1);
        timeLimit = (TextView) findViewById(R.id.time);
Counter counter=new Counter();
Thread counter1=new Thread(counter,"计时");
counter1.start();



    }}





