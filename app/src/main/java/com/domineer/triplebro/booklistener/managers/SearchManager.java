package com.domineer.triplebro.booklistener.managers;

import android.content.Context;

import com.domineer.triplebro.booklistener.beans.VoiceInfo;
import com.domineer.triplebro.booklistener.enmuration.SourceType;
import com.domineer.triplebro.booklistener.sourceops.DataBaseOP;
import com.domineer.triplebro.booklistener.sourceprividers.SourceFactory;

import java.util.List;

public class SearchManager {

    private Context context;
    private DataBaseOP dataBaseOP;

    public SearchManager(Context context) {
        this.context = context;
    }

    public List<VoiceInfo> searchVoiceInfo(String s) {
        dataBaseOP = (DataBaseOP) SourceFactory.sourceCreate(context, SourceType.SOURCE_FROM_DB);
        List<VoiceInfo> voiceInfoList = dataBaseOP.searchVoiceInfo(s);
        return voiceInfoList;
    }
}
