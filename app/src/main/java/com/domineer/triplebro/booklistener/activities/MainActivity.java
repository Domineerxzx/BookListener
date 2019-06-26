package com.domineer.triplebro.booklistener.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.domineer.triplebro.booklistener.R;
import com.domineer.triplebro.booklistener.broadcast.NetChangeReceiver;
import com.domineer.triplebro.booklistener.fragments.BottomFragment;
import com.domineer.triplebro.booklistener.fragments.RecommendFragment;
import com.domineer.triplebro.booklistener.utils.PermissionUtil;

public class MainActivity extends Activity {

    private FrameLayout fl_top;
    private FrameLayout fl_bottom;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initView() {
        fl_top = (FrameLayout) findViewById(R.id.fl_top);
        fl_bottom = (FrameLayout) findViewById(R.id.fl_bottom);
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_top,new RecommendFragment());
        fragmentTransaction.replace(R.id.fl_bottom,new BottomFragment());
        fragmentTransaction.commit();
    }

    private void initData() {
        NetChangeReceiver receiver = new NetChangeReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(receiver, intentFilter);
        PermissionUtil.requestPower(this, this, "android.permission.WRITE_EXTERNAL_STORAGE");
    }
}
