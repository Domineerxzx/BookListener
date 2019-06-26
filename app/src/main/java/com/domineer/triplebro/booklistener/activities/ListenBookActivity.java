package com.domineer.triplebro.booklistener.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.domineer.triplebro.booklistener.R;
import com.domineer.triplebro.booklistener.adapters.RecommendAdapter;
import com.domineer.triplebro.booklistener.beans.VoiceInfo;
import com.domineer.triplebro.booklistener.interfaces.OnItemClickListener;
import com.domineer.triplebro.booklistener.managers.RecommendManager;
import com.domineer.triplebro.booklistener.managers.VoiceManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ListenBookActivity extends Activity implements View.OnClickListener, OnItemClickListener, SeekBar.OnSeekBarChangeListener {

    private ImageView iv_close_listen;
    private ImageView iv_collection;
    private RecyclerView rv_recommend;
    private TextView tv_time;
    private TextView tv_end_time;
    private TextView tv_author;
    private TextView tv_voice_name;
    private TextView tv_start_time;
    private ImageView iv_replay;
    private ImageView iv_play_or_pause;
    private ImageView iv_stop;
    private SeekBar sb_voice;
    private VideoView vv_voice;
    private VoiceManager voiceManager;
    private int user_id;
    private VoiceInfo voiceInfo;
    private boolean isCollection;
    private RecommendManager recommendManager;
    private List<VoiceInfo> recommendInfoList;
    private RecommendAdapter recommendAdapter;
    private TextView tv_position;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            if (vv_voice.isPlaying()) {
                int current = vv_voice.getCurrentPosition();
                sb_voice.setProgress(current);
                tv_position.setText(time(vv_voice.getCurrentPosition()));
            }
            handler.postDelayed(runnable, 500);
        }
    };
    private ImageView iv_voice_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen_book);

        initView();
        initData();
        setOnClickListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    private void initView() {
        iv_close_listen = (ImageView) findViewById(R.id.iv_close_listen);
        iv_stop = (ImageView) findViewById(R.id.iv_stop);
        iv_play_or_pause = (ImageView) findViewById(R.id.iv_play_or_pause);
        iv_replay = (ImageView) findViewById(R.id.iv_replay);
        tv_start_time = (TextView) findViewById(R.id.tv_start_time);
        tv_voice_name = (TextView) findViewById(R.id.tv_voice_name);
        tv_author = (TextView) findViewById(R.id.tv_author);
        tv_end_time = (TextView) findViewById(R.id.tv_end_time);
        tv_time = (TextView) findViewById(R.id.tv_time);
        rv_recommend = (RecyclerView) findViewById(R.id.rv_recommend);
        iv_collection = (ImageView) findViewById(R.id.iv_collection);
        sb_voice = (SeekBar) findViewById(R.id.sb_voice);
        vv_voice = (VideoView) findViewById(R.id.vv_voice);
        tv_position = (TextView) findViewById(R.id.tv_position);
        iv_voice_image = (ImageView) findViewById(R.id.iv_voice_image);
    }

    private void initData() {
        Intent intent = getIntent();
        voiceInfo = (VoiceInfo)intent.getSerializableExtra("voiceInfo");
        voiceManager = new VoiceManager(this);
        SharedPreferences userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
        user_id = userInfo.getInt("user_id", -1);
        tv_voice_name.setText(voiceInfo.getVoiceName());
        tv_author.setText(voiceInfo.getAuthor());
        tv_time.setText(String.valueOf(voiceInfo.getTime()));
        Glide.with(this).load(voiceInfo.getVoiceImage()).into(iv_voice_image);
        isCollection = voiceManager.checkIsCollection(voiceInfo.get_id());
        if(isCollection){
            iv_collection.setBackgroundResource(R.mipmap.collection_click);
        }else{
            iv_collection.setBackgroundResource(R.mipmap.collection);
        }
        vv_voice.setVideoURI(Uri.parse(voiceInfo.getVoicePath()));
        recommendManager = new RecommendManager(this);
        recommendInfoList = recommendManager.getRecommendInfoList();
        rv_recommend.setLayoutManager(new GridLayoutManager(this,3){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        recommendAdapter = new RecommendAdapter(this, recommendInfoList);
        rv_recommend.setAdapter(recommendAdapter);
        recommendAdapter.setOnItemClickListener(this);
        sb_voice.setMax(100);
        sb_voice.setOnSeekBarChangeListener(this);
        vv_voice.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                tv_end_time.setText(time(vv_voice.getDuration()));
            }
        });

        // 在播放完毕被回调
        vv_voice.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(ListenBookActivity.this, "播放完成", Toast.LENGTH_SHORT).show();
                vv_voice.pause();
                vv_voice.resume();
                iv_play_or_pause.setBackgroundResource(R.mipmap.play);
                vv_voice.seekTo(0);
            }
        });
    }

    private void setOnClickListener() {
        iv_close_listen.setOnClickListener(this);
        iv_stop.setOnClickListener(this);
        iv_play_or_pause.setOnClickListener(this);
        iv_replay.setOnClickListener(this);
        iv_collection.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close_listen:
                finish();
                break;
            case R.id.iv_stop:
                iv_play_or_pause.setBackgroundResource(R.mipmap.play);
                vv_voice.pause();
                vv_voice.resume();
                break;
            case R.id.iv_play_or_pause:
                vv_voice.setKeepScreenOn(true);
                if(vv_voice.isPlaying()){
                    vv_voice.pause();
                    iv_play_or_pause.setBackgroundResource(R.mipmap.play);
                }else{
                    vv_voice.start();
                    iv_play_or_pause.setBackgroundResource(R.mipmap.pause);
                    handler.postDelayed(runnable, 0);
                    vv_voice.start();
                    sb_voice.setMax(vv_voice.getDuration());
                }
                break;
            case R.id.iv_replay:
                iv_play_or_pause.setBackgroundResource(R.mipmap.play);
                vv_voice.resume();
                vv_voice.start();
                iv_play_or_pause.setBackgroundResource(R.mipmap.pause);
                break;
            case R.id.iv_collection:
                if(user_id == -1){
                    Toast.makeText(this, "还没登录呢，快去登录吧", Toast.LENGTH_SHORT).show();
                    Intent login = new Intent(this, LoginActivity.class);
                    startActivity(login);
                    return;
                }
                if(isCollection){
                    iv_collection.setBackgroundResource(R.mipmap.collection);
                    isCollection = false;
                    voiceManager.deleteCollection(voiceInfo.get_id());
                }else{
                    iv_collection.setBackgroundResource(R.mipmap.collection_click);
                    isCollection = true;
                    voiceManager.addCollection(voiceInfo.get_id(),user_id);
                }
                break;

        }
    }

    @Override
    public void onItemClick(View view, int position) {
        vv_voice.pause();
        Intent listen = new Intent(this, ListenBookActivity.class);
        listen.putExtra("voiceInfo",recommendInfoList.get(position));
        startActivity(listen);
        finish();
    }

    @Override
    public void onItemLongClick(View view) {

    }

    protected String time(long millionSeconds) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millionSeconds);
        return simpleDateFormat.format(c.getTime());
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // 取得当前进度条的刻度
        int progress = seekBar.getProgress();
        if (vv_voice.isPlaying()) {
            // 设置当前播放的位置
            vv_voice.seekTo(progress);
        }
    }
}
