package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReviewGame extends AppCompatActivity {

    private TextView count0;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    ArrayList<Words> wordsArrayList;

    AlertDialog.Builder dialog;

    SQLiteDatabase db;
    private static final int COMPLETED = 0;

    private int index;//当前word位置
    private ArrayList<Words> al;
    private static int fence;

    int time;
    int count;//分数
    int num; //剩余word的数目判断
    int anum;//当前页面存在的word数目
    int now;

    private volatile boolean exit=false;
    private TextView timeLimit;

    int b1;
    int b2;
    Button buttonA;
    Button buttonB;
    ArrayList result;//分数历史记录


    public  static int total;//数据库word总数
    public static int total1;
    private int record;//最高历史记录
    private SharedPreferences S; //记录
    private SharedPreferences.Editor aEditor; //记录

    int color0= Color.rgb(174 ,238, 238);//背景色：粉色#FFC0CB/青色#AEEEEE
    int color= Color.rgb(150, 205, 205);//背景色：深粉/深青#96CDCD
    int textColor0=Color.rgb(0,0,0);//文本颜色：黑色
    int textColor=Color.rgb(255,48,48);//文本颜色：红色

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
                actionStart(ReviewGame.this,fence);
            }
        });
        dialog.show();
    }
    private void init(){
        anum=4;
        b1=8;
        b2=8;

        //wordsArrayList=DatabaseUtil.GetWord(DatabaseUtil.NEW_WORD,40);
        wordsArrayList=DatabaseUtil.GetALLWord();
        button1=(Button)findViewById(R.id.button11);
        button2=(Button)findViewById(R.id.button12);
        button3=(Button)findViewById(R.id.button21);
        button4=(Button)findViewById(R.id.button22);
        button5=(Button)findViewById(R.id.button31);
        button6=(Button)findViewById(R.id.button32);
        button7=(Button)findViewById(R.id.button41);
        button8=(Button)findViewById(R.id.button42);
        count0=(TextView)findViewById(R.id.score);

        count0.setText("得分"+count);

        //buttonText(index);
        buttonText(fence);

    }

    public void nextWord()
    {
        init();

    }


    private ArrayList random3(){
        result=new ArrayList();
        for(int i=0;i<8;i++){
            result.add(i,i);
        }
        Collections.shuffle(result);

        return result;
    }

    //设置按钮文本
    private void buttonText(int pos) {

        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        button4.setVisibility(View.VISIBLE);
        button5.setVisibility(View.VISIBLE);
        button6.setVisibility(View.VISIBLE);
        button7.setVisibility(View.VISIBLE);
        button8.setVisibility(View.VISIBLE);

        Words word1 = wordsArrayList.get(pos);
        Words word2 = wordsArrayList.get(pos+1);
        Words word3 = wordsArrayList.get(pos+2);
        Words word4 = wordsArrayList.get(pos+3);
        //fence = fence + 4;

        ArrayList b=random3();

        int a;
        //int c;
        for (a = 0; a < 4; a++) {
            //前四个对应中文，后四个对应英文
            Words word=wordsArrayList.get(pos+a);

             //c=(int)b.get(a);
            switch ((int)b.get(a)) {
                case 0:
                    button1.setText(word.getChineses());
                    break;
                case 1:
                    button2.setText(word.getChineses());
                    break;
                case 2:
                    button3.setText(word.getChineses());
                    break;
                case 3:
                    button4.setText(word.getChineses());
                    break;
                case 4:
                    button5.setText(word.getChineses());
                    break;
                case 5:
                    button6.setText(word.getChineses());
                    break;
                case 6:
                    button7.setText(word.getChineses());
                    break;
                case 7:
                    button8.setText(word.getChineses());
                    break;
            }
        }
        for (a = 4; a < 8; a++) {
            //前四个对应中文，后四个对应英文
            Words word=wordsArrayList.get(pos+a-4);

            switch ((int)b.get(a)) {
                case 0:
                    button1.setText(word.getWord());
                    break;
                case 4:
                    button5.setText(word.getWord());
                    break;
                case 1:
                    button2.setText(word.getWord());
                    break;
                case 5:
                    button6.setText(word.getWord());
                    break;
                case 2:
                    button3.setText(word.getWord());
                    break;
                case 6:
                    button7.setText(word.getWord());
                    break;
                case 3:
                    button4.setText(word.getWord());
                    break;
                case 7:
                    button8.setText(word.getWord());
                    break;

            }
        }

    }

    //清除按钮变化，恢复原状
    private void clearButton(){
        buttonA.setTextColor(textColor0);
        buttonA.setBackgroundColor(color0);
        buttonB.setTextColor(textColor0);
        buttonB.setBackgroundColor(color0);
        b1=8;
        b2=8;
    }

    //记录点击的按钮信息,点击引起背景和文本颜色变化
    private void judgeButton(Button button,int s){
        if(b1==8){
            b1=s;
            button.setBackgroundColor(color);
            button.setTextColor(textColor);

            buttonA=button;

        }
        else if(b2==8){
            b2=s;
            button.setBackgroundColor(color);
            button.setTextColor(textColor);
            buttonB=button;
        }
        else {
            clearButton();
        }
    }

    //查找按钮对应的位置??
    private int listButton(int a){
        int i;
        for(i=0;i<8;i++){
            if((int)result.get(i)==a)
                return i;
        }
        return i;
    }


    //判断答案是否正确
    private void judge(){
        int x=listButton(b1);
        int y=listButton(b2);
        if(x==8||y==8){
           // showDialog("wrong");
            return;
        }
        if(x==y+4||y==x+4){
            buttonA.setVisibility(View.INVISIBLE);
            buttonB.setVisibility(View.INVISIBLE);
            count++;
            count0.setText("得分"+count);
            anum--;
            num++;

            //再修改,修改
            if(count==total){
                saveRecord();
                exit=true;
                showDialog("挑战结束，得分："+count);
                return;
            }
            if(anum==0){
                //index=index+4;
                fence=fence+4;
                clearButton();
                nextWord();
            }


            //judge2();
        }
        else {
            clearButton();
        }
    }

    private void judge2(){
        //再修改,修改
        if(count==total){
            saveRecord();
            exit=true;
            showDialog("挑战结束，得分："+count);
            return;
        }

        if (anum == 0) {
            //index=index+4;
            fence = fence + 4;
            clearButton();
            nextWord();
        }

    }

   //计时器


    //
    private class Counter implements Runnable {
        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                timeLimit.setText(msg.what + "s");
                if (msg.what == 0) {
                    // 倒计时结束
                    //if(count<=wordnum)
                    //{
                    saveRecord();
                    showDialog("挑战结束，得分" + count + "   " + "历史最高得分" + record);
                    // }
                    //else showDialog("挑战结束，得分"+wordnum+"   历史最高得分"+record);
                }
            }
        };

        @Override
        public void run() {

            for (int i = 60; i >= 0; i--) {
                if (!exit) {
                    handler.sendEmptyMessage(i);
                    try {

                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();

                    }
                }
            }
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        exit=true;
    }



    //游戏结束语
    private void showDialog(String s)
    {
        AlertDialog.Builder rule=new AlertDialog.Builder(this);
        String string=s;
        rule.setMessage(string);

        rule.setNegativeButton("退出挑战",click);
        rule.create().show();
    }
    private DialogInterface.OnClickListener click=new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            finish();
        }
    };

    //如果分数比历史分数大，则记录并通知
    public void saveRecord()
    {
        record=S.getInt("old_record",0);
        if(record<count)
        {aEditor.putInt("old_record",count);
            record=count;

            Toast.makeText(ReviewGame.this,"新纪录！",Toast.LENGTH_SHORT).show();
        }
        aEditor.commit();

    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_game);

        S=getSharedPreferences("TheRecord2",Context.MODE_PRIVATE);
        aEditor=S.edit();

        //fence,index
       // fence=getIntent().getIntExtra("Fence",0);

        //fence=0
        num=0;
        count=0;
        //index=fence;

        al = DatabaseUtil.GetALLWord();
        total1 = al.size();
        int a=total1%4;
        total=total1-a;

        //total=DatabaseUtil.countNum(fence);
        //total1=total%4;
        //total=total-total1;
        //total=12;

        /*
        if(DatabaseUtil.countNum(fence)<=0){
            setAlert("当前没有新词可复习，赶紧去背新词吧！");
        }else  if(DatabaseUtil.countNum(fence)<4){
            num=DatabaseUtil.countNum(fence);
        }else {
            num=4;
        }

         */

        timeLimit = (TextView) findViewById(R.id.time);

        Counter counter = new Counter();
        Thread counter1 = new Thread(counter, "计时");
        counter1.start();
/*
        if(DatabaseUtil.countNum(fence)<4){
            setAlert("当前没有新词可以复习，感紧去背新词吧！");
        }
        else{
            anum=4;
        }*/
        if (total < 4)
        {showDialog("单词数量不足以进行挑战");
            return;
        }
        else
            Collections.shuffle(al);
        init();


   /*      buttonText(fence);

        Limit = (TextView) findViewById(R.id.time);


        ReviewGame.Counter counter = new ReviewGame.Counter();
        Thread counter1 = new Thread(counter, "计时");
        counter1.start();
*/
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count<=total) {
                    judgeButton(button1, 0);

                    if (b2 != 8) {
                        judge();

                    }

                }

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count<=total) {
                    judgeButton(button2, 1);
                    if (b2 != 8) {
                        judge();

                    }

                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count<=total) {
                    judgeButton(button3, 2);
                    if (b2 != 8) {
                        judge();

                    }

                }

            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count<=total) {
                    judgeButton(button4, 3);
                    if (b2 != 8) {
                        judge();

                    }

                }

            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count<total) {
                    judgeButton(button5, 4);
                    if (b2 != 8) {
                        judge();

                    }

                }
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count<total) {
                    judgeButton(button6, 5);
                    if (b2 != 8) {
                        judge();

                    }

                }

            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count<=total) {
                    judgeButton(button7, 6);
                    if (b2 != 8) {
                        judge();

                    }

                }

            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count<=total) {
                    judgeButton(button8, 7);
                    if (b2 != 8) {
                        judge();

                    }

                }

            }
        });

    }
}
