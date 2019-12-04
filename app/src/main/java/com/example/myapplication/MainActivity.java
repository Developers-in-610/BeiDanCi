package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    public static int fence;
    public static int daywords=10;

//    public static int left_word_total=2089;
    public static void actionStart(Context context,int f){
        Intent intent=new Intent(context,ReviewLetterMain.class);
        intent.putExtra("Fence",f);
        context.startActivity(intent);
        Log.v("hh","main_action"+fence);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //传递数据
        Intent intent=getIntent();
        fence=intent.getIntExtra("newFence",0);
        Button review=(Button)findViewById(R.id.button);
        Button memorize=(Button) findViewById(R.id.memwords);
        Button BChangePlan=(Button) findViewById(R.id.books);
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               MainActivity.actionStart(MainActivity.this,fence);
            }
        });
        memorize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(MainActivity.this,MemorizeWords.class));

            }
        });
        BChangePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Selectbook.class));
            }
        });




    }
    public void initdayview(){
        int daynum=(int)Math.ceil(DatabaseUtil.getnumgroupbyvis(0)/MainActivity.daywords);
        TextView tvbooks=(TextView) findViewById(R.id.tvbook);
        TextView tvdaynums=(TextView) findViewById(R.id.day);
        TextView tvdaywords=(TextView) findViewById(R.id.words);
        tvdaynums.setText(""+daynum);
        tvdaywords.setText(""+MainActivity.daywords);
        if(DBManager.DB_NAME==Selectbook.cetbook[0])
            tvbooks.setText("四级词汇");
        else
            tvbooks.setText("六级词汇");
    }

    @Override
    protected void onStart() {
        super.onStart();
        DBManager dbHelper=new DBManager(this);
        dbHelper.openDatabase();
        initdayview();
        if (!NetworkUtil.isNetworkAvailable(this)) {
            NetworkUtil.showSetNetworkUI(this);
        } else {
            Toast.makeText(this, "网络可用...", Toast.LENGTH_SHORT).show();
        }

    }


}
