package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.TextView;

public class Main2Activity extends Activity
{
    private ImageView welcomeImg = null;
    private boolean isFirstRun = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        welcomeImg = (ImageView) this.findViewById(R.id.welcome_img);
        AlphaAnimation anima = new AlphaAnimation(0.1f, 1.0f);
        anima.setDuration(5000);// 设置动画显示时间
        welcomeImg.startAnimation(anima);
        anima.setAnimationListener(new AnimationImpl());

    }
    private class AnimationImpl implements AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {
            welcomeImg.setBackgroundResource(R.drawable.welcome_page);
        }
        @Override
        public void onAnimationEnd(Animation animation) {

            skip();
        }
        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
    private void skip() {
        initday();
        if(isFirstRun == false)
            startActivity(new Intent(this, MainActivity.class));
        finish();
    }
    private void initday(){

        SharedPreferences sharedPreferences=getSharedPreferences(Selectbook.sharename, Context.MODE_PRIVATE);

        if((sharedPreferences.getString(Selectbook.sharedbnamekey," ")==" ")
                ||sharedPreferences.getInt(Selectbook.sharedaywordkey,-1)==-1){
//            startActivity(new Intent();
            isFirstRun = true;
            Selectbook.needRunMain = true;
            Intent it = new Intent(this,Selectbook.class);
            startActivity(it);
            return;
        }
        else{
//            MainActivity.left_word_total=sharedPreferences.getInt("left_word_total",-1);
            MainActivity.daywords=sharedPreferences.getInt(Selectbook.sharedaywordkey,-1);
            DBManager.DB_NAME=sharedPreferences.getString(Selectbook.sharedbnamekey," ");


        }

    }
}


