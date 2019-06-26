package com.domineer.triplebro.booklistener.managers;

import android.content.Context;

import com.domineer.triplebro.booklistener.beans.CollectionInfo;
import com.domineer.triplebro.booklistener.beans.VoiceInfo;
import com.domineer.triplebro.booklistener.enmuration.SourceType;
import com.domineer.triplebro.booklistener.sourceops.DataBaseOP;
import com.domineer.triplebro.booklistener.sourceprividers.SourceFactory;

import java.util.List;

/**
 * @author Domineer
 * @data 2019/6/25,21:24
 * ----------为梦想启航---------
 * --Set Sell For Your Dream--
 */
public class CollectionManager {

    private Context context;
    private DataBaseOP dataBaseOP;

    public CollectionManager(Context context) {
        this.context = context;
    }

    public List<VoiceInfo> getCollectionVoiceList(int user_id) {
        dataBaseOP = (DataBaseOP) SourceFactory.sourceCreate(context, SourceType.SOURCE_FROM_DB);
        List<CollectionInfo> collectionInfoList = dataBaseOP.getCollectionVoiceList(user_id);
        List<VoiceInfo> voiceInfoList = dataBaseOP.getCollectionVoiceInfoList(collectionInfoList);
        return voiceInfoList;
    }

    public void updateCollectionInfo(VoiceInfo voiceInfo) {
        dataBaseOP = (DataBaseOP) SourceFactory.sourceCreate(context, SourceType.SOURCE_FROM_DB);
        dataBaseOP.updateCollectionInfo(voiceInfo);
    }
}
