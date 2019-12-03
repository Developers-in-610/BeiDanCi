package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
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

public class TimeLimitedChallenge extends AppCompatActivity implements View.OnClickListener{
    private TextView timeLimit;
    private ArrayList<Words> al;
    private List<String> chineses;
    private int index;
    Button choice1;
    Button choice2;
    private int score;
    private String answer;
    private TextView tvenglish;
    private Words nowword;
    private int wordnum;
    private TextView score0;
    public static int totalnum;
    Button choice3;
    Button choice4;
    private volatile boolean exit=false;
    private int record;
    private SharedPreferences sp;

    private SharedPreferences.Editor editor;



    private class Counter implements Runnable
    {
        Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            timeLimit.setText(msg.what + "s");
            if (msg.what == 0) {
                // 倒计时结束
                if(score<=wordnum)
                {saveRecord();
                    showDialog("挑战结束，得分"+score+"   " +
                        "历史最高得分"+record);}
                else showDialog("挑战结束，得分"+wordnum+"   历史最高得分"+record);
            }
        }
    };
        @Override
        public void run() {

            for (int i = 20; i >= 0; i--) {
if(!exit)
{  handler.sendEmptyMessage(i);
                try {

                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }}
        }
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        exit=true;
    }
    private void showDialog(String s)
    {
        AlertDialog.Builder rule=new AlertDialog.Builder(this);
        String string=s;
        rule.setMessage(string);
        //rule.setPositiveButton("再次挑战",click1);
        rule.setNegativeButton("退出挑战",click2);
        rule.create().show();
    }

    private DialogInterface.OnClickListener click2=new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            //startActivity(new Intent(TimeLimitedChallenge.this,ReviewLetterMain.class));
            finish();
        }
    };

    public void init(int i) {
        index=i;
            choice1 = (Button) findViewById(R.id.answer1);
            choice2 = (Button) findViewById(R.id.answer2);
            choice3 = (Button) findViewById(R.id.answer3);
            choice4 = (Button) findViewById(R.id.answer4);
            score0=(TextView)findViewById(R.id.score);
            tvenglish = (TextView) findViewById(R.id.eng);
            nowword = al.get(index%totalnum);
            tvenglish.setText(nowword.getWord());
            chineses = new LinkedList<String>() {{
                add(nowword.getChineses());
                add(al.get((index + 1)%totalnum).getChineses());
                add(al.get((index + 2)%totalnum).getChineses());
                add(al.get((index + 3)%totalnum).getChineses());
            }};

            Collections.shuffle(chineses);
            choice1.setText(chineses.get(0));
            choice2.setText(chineses.get(1));
            choice3.setText(chineses.get(2));
            choice4.setText(chineses.get(3));
            score0.setText("得分:"+score);

    }
public void nextWord()
{
init(++index);
}
public void saveRecord()
{
    record=sp.getInt("old_record",0);
    if(record<score)
    {editor.putInt("old_record",score);
    record=score;


    Toast.makeText(TimeLimitedChallenge.this,"新纪录！",Toast.LENGTH_SHORT).show();
    }

    editor.commit();

}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_challenge1);
        sp=getSharedPreferences("TheRecord",Context.MODE_PRIVATE);
        editor=sp.edit();
        wordnum = 0;
        score=0;
        al = DatabaseUtil.GetALLWord();
        totalnum = al.size();
        if (totalnum < 4)
        {showDialog("单词数量不足以进行挑战");
        return;
        }
        else
            Collections.shuffle(al);
        init(0);
        timeLimit = (TextView) findViewById(R.id.time);

        Counter counter = new Counter();
        Thread counter1 = new Thread(counter, "计时");
        counter1.start();
        choice1.setOnClickListener(this);
        choice2.setOnClickListener(this);
        choice3.setOnClickListener(this);
        choice4.setOnClickListener(this);


                }
                @Override
    public void onClick(View view)
                {answer=nowword.getChineses();

                    if(wordnum==totalnum){
exit=true;
                        saveRecord();
                        showDialog("挑战结束，得分"+score+" " + ""+
                                "历史最高得分"+record);
                        return;
                    }
                    switch (view.getId()){
                        case R.id.answer1:if(answer==choice1.getText().toString())score++;break;
                        case R.id.answer2:if(answer==choice2.getText().toString())score++;break;
                        case R.id.answer3:if(answer==choice3.getText().toString())score++;break;
                        case R.id.answer4:if(answer==choice4.getText().toString())score++;break;
                        default:break;
                    }
                    wordnum++;
               nextWord();
                }

            }





