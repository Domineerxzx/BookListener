package com.domineer.triplebro.booklistener.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.domineer.triplebro.booklistener.R;
import com.domineer.triplebro.booklistener.broadcast.NetChangeReceiver;
import com.domineer.triplebro.booklistener.enmuration.SourceType;
import com.domineer.triplebro.booklistener.sourceops.AssetsOP;
import com.domineer.triplebro.booklistener.sourceprividers.SourceFactory;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
        initData();
    }

    private void initView() {

    }

    private void initData() {
        AssetsOP assetsOP = (AssetsOP) SourceFactory.sourceCreate(SplashActivity.this, SourceType.SOURCE_FROM_ASSETS);
        assetsOP.initData();
        new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2500);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent main = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(main);
                            finish();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
