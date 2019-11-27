package com.example.myapplication;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class wordvoice extends Service {

    private MediaPlayer mp;
    private String query;

    @Override
    public void onCreate() {

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (query != null && !query.equals(intent.getStringExtra("query")) && mp != null) {
            mp.start();
        } else {
            String query = intent.getStringExtra("query");
            // System.out.println("http://dict.youdao.com/dictvoice?audio=" + query);
            Uri location = Uri.parse("http://dict.youdao.com/dictvoice?audio=" + query);

//            mp = MediaPlayer.create(this, location);
            mp = new MediaPlayer();
            Map<String,String> headers = new HashMap<String,String>();
            headers.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/18.17763");
            try {
                mp.setDataSource(this,location,headers);
            } catch (IOException e){
                e.printStackTrace();
            }
            try {
                mp.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // System.out.println("音乐开始播放");
            mp.start();

            // 音乐播放完毕的事件处理
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    // 不循环播放
                    try {
                        // mp.start();
                        System.out.println("stopped");
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                }
            });

            // 播放音乐时发生错误的事件处理
            mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    // 释放资源
                    try {
                        mp.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            });
        }

        return super.onStartCommand(intent, flags, startId);
    }



    @Override
    public void onDestroy() {
        // 服务停止时停止播放音乐并释放资源
        mp.stop();
        mp.release();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}



