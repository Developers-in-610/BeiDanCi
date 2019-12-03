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
        DBManager dbHelper=new DBManager(this);
        dbHelper.openDatabase();
        initdayview();

        //传递数据
        Intent intent=getIntent();
        fence=intent.getIntExtra("newFence",0);

        //Log.v("hh","main_create"+fence);

        //将数据库写入手机


        Button review=(Button)findViewById(R.id.button);
        Button memorize=(Button) findViewById(R.id.memwords);
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




    }
    public void initdayview(){
        int daynum=(int)Math.ceil(DatabaseUtil.getnumgroupbyvis(0)/MainActivity.daywords);
        TextView tvdaynums=(TextView) findViewById(R.id.day);
        TextView tvdaywords=(TextView) findViewById(R.id.words);
        tvdaynums.setText(""+daynum);
        tvdaywords.setText(""+MainActivity.daywords);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initdayview();
        if (!NetworkUtil.isNetworkAvailable(this)) {
            NetworkUtil.showSetNetworkUI(this);
        } else {
            Toast.makeText(this, "网络可用...", Toast.LENGTH_SHORT).show();
        }

    }


}
