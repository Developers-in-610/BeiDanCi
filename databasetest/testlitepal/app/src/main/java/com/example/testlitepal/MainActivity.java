package com.example.testlitepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import org.litepal.LitePal;
import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        copyDbFile(MainActivity.this, "words.db");
        LitePal.initialize(this);
        List<Words> words=LitePal.findAll(Words.class);
        for(Words words1 :words){
            Log.d("MainActivity","id is"+words1.getId());
            Log.d("MainActivity","word is "+words1.getWord());
            Log.d("MainActivity","chineses is"+words1.getChineses());
        }


    }

    //用于项目开始时将words.db拷贝进入 /data/data/packagename/database
    public void copyDbFile(Context context, String db_name) {
        SharedPreferences shaf = getSharedPreferences("database", MODE_PRIVATE);
        InputStream in = null;
        FileOutputStream out = null;
        String DATABASECOPIED="databasecopied";
        //String path = "/data/data/" + context.getPackageName() + "/databases/";
        File filePath = context.getDatabasePath(db_name);
        //判读是否已经读入，防止多次拷贝
        if (!shaf.getBoolean(DATABASECOPIED, false)) {
            try {
                in = context.getAssets().open(db_name); // 从assets目录下复制
                out = new FileOutputStream(filePath);
                int length = -1;
                byte[] buf = new byte[1024];
                while ((length = in.read(buf)) != -1) {
                    out.write(buf, 0, length);
                }
                out.flush();
                SharedPreferences.Editor editor = getSharedPreferences("database", MODE_PRIVATE)
                        .edit();
                editor.putBoolean(DATABASECOPIED, true);
                editor.apply();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (in != null) in.close();
                    if (out != null) out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            //}
        }
    }
}
class Words extends LitePalSupport {

    @Column(unique = true, defaultValue = "unknown")
    private Integer id;
    private String word;
    private String chineses;

    public Integer getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setChineses(String chineses) {
        this.chineses = chineses;
    }

    public String getChineses() {
        return chineses;
    }
// generated getters and setters.


}