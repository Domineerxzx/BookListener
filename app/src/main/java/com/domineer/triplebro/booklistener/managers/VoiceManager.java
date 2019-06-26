package com.domineer.triplebro.booklistener.managers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.widget.SeekBar;
import android.widget.VideoView;

import com.domineer.triplebro.booklistener.activities.ListenBookActivity;
import com.domineer.triplebro.booklistener.enmuration.SourceType;
import com.domineer.triplebro.booklistener.services.CountDownService;
import com.domineer.triplebro.booklistener.sourceops.DataBaseOP;
import com.domineer.triplebro.booklistener.sourceprividers.SourceFactory;

public class VoiceManager implements ServiceConnection {

    private Context context;
    private DataBaseOP dataBaseOP;
    private VideoView videoView;
    private SeekBar seekBar;

    public VoiceManager(Context context) {
        this.context = context;
    }

    public boolean checkIsCollection(int id) {
        dataBaseOP = (DataBaseOP) SourceFactory.sourceCreate(context, SourceType.SOURCE_FROM_DB);
        boolean isCollection = dataBaseOP.checkIsCollection(id);
        return isCollection;
    }

    public void deleteCollection(int id) {
        dataBaseOP = (DataBaseOP) SourceFactory.sourceCreate(context, SourceType.SOURCE_FROM_DB);
        dataBaseOP.deleteCollection(id);
    }

    public void addCollection(int id, int user_id) {
        dataBaseOP = (DataBaseOP) SourceFactory.sourceCreate(context, SourceType.SOURCE_FROM_DB);
        dataBaseOP.addCollection(id,user_id);
    }

    public void setProgressListener(VideoView videoView, SeekBar seekBar) {
        this.videoView = videoView;
        this.seekBar = seekBar;
        Intent intent = new Intent(context, CountDownService.class);
        context.bindService(intent,this,Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        CountDownService.MyBinder myBinder = (CountDownService.MyBinder) service;
        myBinder.setProgressListener(videoView,seekBar);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
