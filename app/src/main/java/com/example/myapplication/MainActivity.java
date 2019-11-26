package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //将数据库写入手机
        DBManager dbHelper=new DBManager(this);
        dbHelper.openDatabase();
        Button review=(Button)findViewById(R.id.button);
        Button memorize=(Button) findViewById(R.id.memwords);
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ReviewLetterMain.class));
            }
        });
        memorize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MemorizeWords.class));
            }
        });



    }
}
