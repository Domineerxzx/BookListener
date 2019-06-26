package com.domineer.triplebro.booklistener.sourceops;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;

import com.domineer.triplebro.booklistener.beans.CollectionInfo;
import com.domineer.triplebro.booklistener.beans.HistoryInfo;
import com.domineer.triplebro.booklistener.beans.VoiceInfo;
import com.domineer.triplebro.booklistener.beans.VoiceTypeInfo;
import com.domineer.triplebro.booklistener.enmuration.SourceType;
import com.domineer.triplebro.booklistener.interfaces.ISource;
import com.domineer.triplebro.booklistener.sourceprividers.SourceFactory;
import com.domineer.triplebro.booklistener.utils.VoiceInfoParser;
import com.domineer.triplebro.booklistener.utils.VoiceTypeInfoParser;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

public class AssetsOP implements ISource {

    private Context context;
    private final AssetManager assets;
    private String assetsFileName;
    private InputStream inputStream;
    private List<VoiceTypeInfo> voiceTypeInfoList;
    private List<VoiceInfo> voiceInfoList;

    public AssetsOP(Context context) {
        this.context = context;
        assets = context.getAssets();
    }

    public void initData() {
        List<VoiceTypeInfo> voiceTypeInfoList = getVoiceTypeInfoList();
        List<VoiceInfo> voiceInfoList = getVoiceInfoList();
        insertData(voiceTypeInfoList, voiceInfoList);
    }

    @Override
    public void insertData(List<VoiceTypeInfo> voiceTypeInfoList, List<VoiceInfo> voiceInfoList) {

        DataBaseOP dataBaseOP = (DataBaseOP) SourceFactory.sourceCreate(context, SourceType.SOURCE_FROM_DB);
        dataBaseOP.insertData(voiceTypeInfoList,voiceInfoList);

    }

    private List<VoiceInfo> getVoiceInfoList() {
        try {
            assetsFileName = "voiceInfo.xml";
            VoiceInfoParser xmlParser = new VoiceInfoParser();
            inputStream = assets.open(assetsFileName);
            voiceInfoList = xmlParser.parseXML(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return voiceInfoList;
    }

    private List<VoiceTypeInfo> getVoiceTypeInfoList() {
        try {
            assetsFileName = "voiceTypeInfo.xml";
            VoiceTypeInfoParser xmlParser = new VoiceTypeInfoParser();
            inputStream = assets.open(assetsFileName);
            voiceTypeInfoList = xmlParser.parseXML(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return voiceTypeInfoList;
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
    public void addHistory(VoiceInfo contentValues, int user_id) {

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
}
