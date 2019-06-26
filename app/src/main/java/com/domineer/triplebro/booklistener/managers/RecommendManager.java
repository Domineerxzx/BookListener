package com.domineer.triplebro.booklistener.managers;

import android.content.ContentValues;
import android.content.Context;

import com.domineer.triplebro.booklistener.beans.VoiceInfo;
import com.domineer.triplebro.booklistener.enmuration.SourceType;
import com.domineer.triplebro.booklistener.sourceops.DataBaseOP;
import com.domineer.triplebro.booklistener.sourceprividers.SourceFactory;

import java.util.List;

public class RecommendManager {

    private Context context;
    private DataBaseOP dataBaseOP;

    public RecommendManager(Context context) {
        this.context = context;
    }


    public List<String> getBannerImageList() {

        dataBaseOP = (DataBaseOP) SourceFactory.sourceCreate(context, SourceType.SOURCE_FROM_DB);
        List<String> bannerImageList = dataBaseOP.getBannerImageList();
        return bannerImageList;
    }

    public List<VoiceInfo> getRecommendInfoList() {

        dataBaseOP = (DataBaseOP) SourceFactory.sourceCreate(context, SourceType.SOURCE_FROM_DB);
        List<VoiceInfo> recommendInfoList = dataBaseOP.getRecommendInfoList();
        return recommendInfoList;
    }

    public void addHistory(VoiceInfo voiceInfo,int user_id) {
        dataBaseOP = (DataBaseOP) SourceFactory.sourceCreate(context, SourceType.SOURCE_FROM_DB);
        dataBaseOP.addHistory(voiceInfo,user_id);
    }

    public List<VoiceInfo> getNewInfoList() {
        dataBaseOP = (DataBaseOP) SourceFactory.sourceCreate(context, SourceType.SOURCE_FROM_DB);
        List<VoiceInfo> newInfoList = dataBaseOP.getNewInfoList();
        return newInfoList;
    }
}
