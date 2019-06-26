package com.domineer.triplebro.booklistener.managers;

import android.content.Context;

import com.domineer.triplebro.booklistener.beans.CollectionInfo;
import com.domineer.triplebro.booklistener.beans.HistoryInfo;
import com.domineer.triplebro.booklistener.beans.VoiceInfo;
import com.domineer.triplebro.booklistener.enmuration.SourceType;
import com.domineer.triplebro.booklistener.sourceops.DataBaseOP;
import com.domineer.triplebro.booklistener.sourceprividers.SourceFactory;

import java.util.List;

public class HistoryManager {

    private Context context;
    private DataBaseOP dataBaseOP;

    public HistoryManager(Context context) {
        this.context = context;
    }

    public List<VoiceInfo> getHistoryVoiceList(int user_id) {
        dataBaseOP = (DataBaseOP) SourceFactory.sourceCreate(context, SourceType.SOURCE_FROM_DB);
        List<HistoryInfo> historyInfoList = dataBaseOP.getHistoryInfoList(user_id);
        List<VoiceInfo> voiceInfoList = dataBaseOP.getHistoryVoiceList(historyInfoList);
        return voiceInfoList;
    }
}
