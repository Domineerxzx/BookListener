package com.domineer.triplebro.booklistener.sourceops;

import android.content.Context;

import com.domineer.triplebro.booklistener.beans.CollectionInfo;
import com.domineer.triplebro.booklistener.beans.HistoryInfo;
import com.domineer.triplebro.booklistener.beans.VoiceInfo;
import com.domineer.triplebro.booklistener.beans.VoiceTypeInfo;
import com.domineer.triplebro.booklistener.interfaces.ISource;
import java.io.InputStream;
import java.util.List;

public class HttpOP implements ISource {

    private Context context;
    private InputStream inputStream;

    public HttpOP(Context context) {
        this.context = context;
    }

    @Override
    public List<String> getBannerImageList() {
        return null;
    }

    @Override
    public List<VoiceInfo> getRecommendInfoList() {
        return null;
    }

    @Override
    public void addHistory(VoiceInfo contentValues,int user_id) {

    }

    @Override
    public List<VoiceInfo> getNewInfoList() {
        return null;
    }

    @Override
    public List<VoiceTypeInfo> getBookType() {
        return null;
    }

    @Override
    public List<VoiceInfo> getVoiceInfoListWithType(VoiceTypeInfo voiceTypeInfo) {
        return null;
    }

    @Override
    public boolean checkIsCollection(int id) {
        return false;
    }

    @Override
    public void deleteCollection(int id) {

    }

    @Override
    public void addCollection(int id, int user_id) {

    }

    @Override
    public List<CollectionInfo> getCollectionVoiceList(int user_id) {
        return null;
    }

    @Override
    public List<VoiceInfo> getCollectionVoiceInfoList(List<CollectionInfo> collectionInfoList) {
        return null;
    }

    @Override
    public void updateCollectionInfo(VoiceInfo voiceInfo) {

    }

    @Override
    public List<HistoryInfo> getHistoryInfoList(int user_id) {
        return null;
    }

    @Override
    public List<VoiceInfo> getHistoryVoiceList(List<HistoryInfo> historyInfoList) {
        return null;
    }

    @Override
    public List<VoiceInfo> searchVoiceInfo(String s) {
        return null;
    }

    @Override
    public void insertData(List<VoiceTypeInfo> voiceTypeInfoList, List<VoiceInfo> voiceInfoList) {

    }
}
