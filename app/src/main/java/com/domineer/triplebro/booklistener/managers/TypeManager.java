package com.domineer.triplebro.booklistener.managers;

import android.content.Context;

import com.domineer.triplebro.booklistener.beans.VoiceInfo;
import com.domineer.triplebro.booklistener.beans.VoiceTypeInfo;
import com.domineer.triplebro.booklistener.enmuration.SourceType;
import com.domineer.triplebro.booklistener.sourceops.DataBaseOP;
import com.domineer.triplebro.booklistener.sourceprividers.SourceFactory;

import java.util.List;

public class TypeManager {

    private Context context;
    private DataBaseOP dataBaseOP;

    public TypeManager(Context context) {
        this.context = context;
    }

    public List<VoiceTypeInfo> getBookType() {
        dataBaseOP = (DataBaseOP) SourceFactory.sourceCreate(context, SourceType.SOURCE_FROM_DB);
        List<VoiceTypeInfo> voiceTypeInfoList = dataBaseOP.getBookType();
        return voiceTypeInfoList;
    }

    public List<VoiceInfo> getVoiceInfoListWithType(VoiceTypeInfo voiceTypeInfo) {
        dataBaseOP = (DataBaseOP) SourceFactory.sourceCreate(context, SourceType.SOURCE_FROM_DB);
        List<VoiceInfo> voiceInfoList = dataBaseOP.getVoiceInfoListWithType(voiceTypeInfo);
        return voiceInfoList;
    }
}
