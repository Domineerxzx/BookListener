package com.domineer.triplebro.booklistener.interfaces;

import com.domineer.triplebro.booklistener.beans.CollectionInfo;
import com.domineer.triplebro.booklistener.beans.HistoryInfo;
import com.domineer.triplebro.booklistener.beans.VoiceInfo;
import com.domineer.triplebro.booklistener.beans.VoiceTypeInfo;

import java.util.List;

public interface ISource {


    List<String> getBannerImageList();

    List<VoiceInfo> getRecommendInfoList();

    void addHistory(VoiceInfo contentValues,int user_id);

    List<VoiceInfo> getNewInfoList();

    List<VoiceTypeInfo> getBookType();

    List<VoiceInfo> getVoiceInfoListWithType(VoiceTypeInfo voiceTypeInfo);

    boolean checkIsCollection(int id);

    void deleteCollection(int id);

    void addCollection(int id, int user_id);

    List<CollectionInfo> getCollectionVoiceList(int user_id);

    List<VoiceInfo> getCollectionVoiceInfoList(List<CollectionInfo> collectionInfoList);

    void updateCollectionInfo(VoiceInfo voiceInfo);

    List<HistoryInfo> getHistoryInfoList(int user_id);

    List<VoiceInfo> getHistoryVoiceList(List<HistoryInfo> historyInfoList);

    List<VoiceInfo> searchVoiceInfo(String s);

    void insertData(List<VoiceTypeInfo> voiceTypeInfoList, List<VoiceInfo> voiceInfoList);
}
