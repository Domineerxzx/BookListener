package com.domineer.triplebro.booklistener.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.domineer.triplebro.booklistener.R;
import com.domineer.triplebro.booklistener.adapters.CollectionAdapter;
import com.domineer.triplebro.booklistener.adapters.HistoryAdapter;
import com.domineer.triplebro.booklistener.beans.VoiceInfo;
import com.domineer.triplebro.booklistener.managers.CollectionManager;
import com.domineer.triplebro.booklistener.managers.HistoryManager;

import java.util.List;

public class HistoryActivity extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener {

    private ImageView iv_close_history;
    private HistoryManager historyManager;
    private List<VoiceInfo> historyInfoList;
    private ListView lv_history;
    private HistoryAdapter historyAdapter;
    private SharedPreferences userInfo;
    private int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initView();
        initData();
        setOnClickListener();
    }

    private void initView() {
        iv_close_history = (ImageView) findViewById(R.id.iv_close_history);
        lv_history = (ListView) findViewById(R.id.lv_history);
    }

    private void initData() {
        userInfo = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        user_id = userInfo.getInt("user_id", -1);
        if(user_id == -1){
            Toast.makeText(this, "还没登录呢，不能查看历史记录信息", Toast.LENGTH_SHORT).show();
            return;
        }
        historyManager = new HistoryManager(this);
        historyInfoList = historyManager.getHistoryVoiceList(user_id);
        historyAdapter = new HistoryAdapter(this, historyInfoList, historyManager);
        lv_history.setAdapter(historyAdapter);
        lv_history.setOnItemClickListener(this);
    }

    private void setOnClickListener() {
        iv_close_history.setOnClickListener(this);
        lv_history.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close_history:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent listen = new Intent(this, ListenBookActivity.class);
        listen.putExtra("voiceInfo", historyInfoList.get(position));
        startActivity(listen);
    }
}
