package com.domineer.triplebro.booklistener.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.domineer.triplebro.booklistener.R;
import com.domineer.triplebro.booklistener.views.TwoButtonDialog;

import java.io.File;

public class SettingActivity extends Activity implements View.OnClickListener {

    private ImageView iv_close_setting;
    private TextView tv_cancellation;
    private SharedPreferences userInfo;
    private SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
        setOnClickListener();
    }

    private void initView() {
        iv_close_setting = (ImageView) findViewById(R.id.iv_close_setting);
        tv_cancellation = (TextView) findViewById(R.id.tv_cancellation);
    }

    private void setOnClickListener() {
        iv_close_setting.setOnClickListener(this);
        tv_cancellation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close_setting:
                finish();
                break;
            case R.id.tv_cancellation:
                TwoButtonDialog twoButtonDialog = new TwoButtonDialog();
                twoButtonDialog.show("注销", "是否注销此用户?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
                        edit = userInfo.edit();
                        edit.clear();
                        edit.apply();
                        dialog.dismiss();
                        finish();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                },getFragmentManager());

                break;
        }
    }
}
