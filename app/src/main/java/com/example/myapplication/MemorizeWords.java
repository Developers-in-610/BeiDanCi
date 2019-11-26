package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MemorizeWords extends AppCompatActivity {
    private ArrayList<Words> al;
    private int index;
    private Button bknow;
    private Button bunknow;
    private TextView tvhintchinese,tvnewword,tvokword;
    private TextView tvenglish;
    private Words nowword;
    private int knownum,newwordnum;
    public static int totalnum;
    private Button throwit;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorize_words);
        init();

        bknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextword(1);

            }
        });
        bunknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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





    }
    //初始化
    public void init(){
        totalnum=5;knownum=0;newwordnum=0;
        al=DatabaseUtil.GetWord(0,totalnum);
        index=0;
        bknow=(Button) findViewById(R.id.knowbutton);
        bunknow=(Button) findViewById(R.id.unknowbutton);
        throwit=(Button) findViewById(R.id.throwit);
        tvhintchinese=(TextView) findViewById(R.id.hintchinese);
        tvenglish=(TextView) findViewById(R.id.english);
        tvokword=(TextView) findViewById(R.id.okword);
        tvnewword=(TextView) findViewById(R.id.newword);
        nowword=al.get(index);
        tvenglish.setText(nowword.getWord());
        bknow.setText("我认识");
        bunknow.setText("提示一下吧");
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
                //////处理。。。。
            }
        }
        index=(index+1)%totalnum;
        while (al.get(index).getVisted()==1||al.get(index).getVisted()==3) {
            index = (index + 1) % totalnum;
        }
        nowword=al.get(index);
        tvhintchinese.setText("");
        tvenglish.setText(nowword.getWord());
        bknow.setText("我认识");
        bunknow.setText("提示一下吧");

    }
}
