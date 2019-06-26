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
import com.domineer.triplebro.booklistener.beans.VoiceInfo;
import com.domineer.triplebro.booklistener.managers.CollectionManager;

import java.util.List;

public class CollectionActivity extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener {

    private ImageView iv_close_collection;
    private CollectionManager collectionManager;
    private List<VoiceInfo> collectionInfoList;
    private ListView lv_collection;
    private CollectionAdapter collectionAdapter;
    private SharedPreferences userInfo;
    private int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        initView();
        initData();
        setOnClickListener();
    }

    private void initView() {
        iv_close_collection = (ImageView) findViewById(R.id.iv_close_collection);
        lv_collection = (ListView) findViewById(R.id.lv_collection);
    }

    private void initData() {
        userInfo = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        user_id = userInfo.getInt("user_id", -1);
        if(user_id == -1){
            Toast.makeText(this, "还没登录呢，不能查看收藏信息", Toast.LENGTH_SHORT).show();
            return;
        }
        collectionManager = new CollectionManager(this);
        collectionInfoList = collectionManager.getCollectionVoiceList(user_id);
        collectionAdapter = new CollectionAdapter(this,collectionInfoList,collectionManager);
        lv_collection.setAdapter(collectionAdapter);
        lv_collection.setOnItemClickListener(this);
    }

    private void setOnClickListener() {
        iv_close_collection.setOnClickListener(this);
        lv_collection.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close_collection:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent listen = new Intent(this, ListenBookActivity.class);
        listen.putExtra("voiceInfo",collectionInfoList.get(position));
        startActivity(listen);
    }
}
