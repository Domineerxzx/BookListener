package com.domineer.triplebro.booklistener.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.VideoView;

import com.domineer.triplebro.booklistener.handlers.CountDownHandler;
import com.domineer.triplebro.booklistener.properties.ProjectProperties;

public class CountDownService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public class MyBinder extends Binder {

        public void countDown(CountDownHandler countDownHandler) {
            CountDownService.this.countDown(countDownHandler);
        }

        public void setProgressListener(VideoView videoView, SeekBar seekBar) {
            CountDownService.this.setProgressListener(videoView,seekBar);
        }
    }

    private void setProgressListener(final VideoView videoView, final SeekBar seekBar) {
        new Thread() {
            @Override
            public void run() {
                try {
                    while (videoView.isPlaying()) {
                        // 如果正在播放，没0.5.毫秒更新一次进度条
                        int current = videoView.getCurrentPosition();
                        seekBar.setProgress(current);
                        sleep(500);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void countDown(final CountDownHandler countDownHandler) {
        new Thread(){
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 60; i++) {
                        Thread.sleep(1000);
                        Message message = new Message();
                        message.obj = i+1;
                        message.what= ProjectProperties.GET_REQUEST_CODE_FAILED;
                        countDownHandler.sendMessage(message);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
