package com.domineer.triplebro.booklistener.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.domineer.triplebro.booklistener.R;
import com.domineer.triplebro.booklistener.adapters.RecommendAdapter;
import com.domineer.triplebro.booklistener.beans.VoiceInfo;
import com.domineer.triplebro.booklistener.interfaces.OnItemClickListener;
import com.domineer.triplebro.booklistener.managers.SearchManager;

import java.util.List;

public class SearchActivity extends Activity implements OnItemClickListener, TextWatcher, View.OnClickListener {

    private ImageView iv_close_search;
    private EditText et_search;
    private RelativeLayout rl_search;
    private RecyclerView rv_search_result;
    private SearchManager searchManager;
    private List<VoiceInfo> voiceInfoList;
    private RecommendAdapter recommendAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();
        initData();
        setOnClickListener();
    }

    private void initView() {
        iv_close_search = (ImageView) findViewById(R.id.iv_close_search);
        et_search = (EditText) findViewById(R.id.et_search);
        rv_search_result = (RecyclerView) findViewById(R.id.rv_search_result);
        rv_search_result.setLayoutManager(new GridLayoutManager(this,3));

    }

    private void initData() {
        searchManager = new SearchManager(this);
        et_search.addTextChangedListener(this);
    }

    private void setOnClickListener() {
        iv_close_search.setOnClickListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent listen = new Intent(this, ListenBookActivity.class);
        listen.putExtra("voiceInfo",voiceInfoList.get(position));
        startActivity(listen);
    }

    @Override
    public void onItemLongClick(View view) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(s.toString().length()>0){
            voiceInfoList = searchManager.searchVoiceInfo(s.toString());
            recommendAdapter = new RecommendAdapter(this, voiceInfoList);
            rv_search_result.setAdapter(recommendAdapter);
            recommendAdapter.setOnItemClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close_search:
                finish();
        }
    }
}
