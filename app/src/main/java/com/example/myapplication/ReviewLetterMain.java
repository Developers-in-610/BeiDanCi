package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ReviewLetterMain extends AppCompatActivity {

    public static int fence;
    public static void actionStart(Context context,Class c,int f){
        //接收数据
        Intent intent = new Intent(context,c);
        intent.putExtra("Fence",f);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_letter_main);
        fence=getIntent().getIntExtra("Fence",0);
        ImageButton button=(ImageButton)findViewById(R.id.fullSpell);
        ImageButton button1=(ImageButton)findViewById(R.id.chooseLetter);
        ImageButton button2=(ImageButton)findViewById(R.id.challenge);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionStart(ReviewLetterMain.this,ReviewLetter1.class,fence);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionStart(ReviewLetterMain.this,ReviewLetter2.class,fence);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog();


            }
        });
    }
    private void showDialog()
    {
        AlertDialog.Builder rule=new AlertDialog.Builder(this);
        rule.setMessage("挑战规则：限时200秒，选择每个单词正确的中文释义，答对计1分，答错不计分。");
        rule.setPositiveButton("开始挑战",click1);
        rule.setNegativeButton("不挑战",click2);
        rule.create().show();
    }
    private DialogInterface.OnClickListener click1=new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            startActivity(new Intent(ReviewLetterMain.this,TimeLimitedChallenge.class));
        }
    };
    private DialogInterface.OnClickListener click2=new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
           dialogInterface.cancel();
        }
    };
}

