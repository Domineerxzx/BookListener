package com.domineer.triplebro.booklistener.sourceops;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.domineer.triplebro.booklistener.beans.CollectionInfo;
import com.domineer.triplebro.booklistener.beans.HistoryInfo;
import com.domineer.triplebro.booklistener.beans.VoiceInfo;
import com.domineer.triplebro.booklistener.beans.VoiceTypeInfo;
import com.domineer.triplebro.booklistener.database.MyOpenHelper;
import com.domineer.triplebro.booklistener.interfaces.ISource;

import java.util.ArrayList;
import java.util.List;

public class DataBaseOP implements ISource {

    private Context context;
    private final MyOpenHelper myOpenHelper;
    private final SQLiteDatabase db;

    public DataBaseOP(Context context) {
        this.context = context;
        myOpenHelper = new MyOpenHelper(context);
        db = myOpenHelper.getWritableDatabase();
    }

    @Override
    public List<String> getBannerImageList() {

        List<String> bannerImageList = new ArrayList<>();
        Cursor adPictureInfoCursor = db.query("adPictureInfo", null, null, null, null, null, null);
        if (adPictureInfoCursor != null && adPictureInfoCursor.getCount() > 0) {
            while (adPictureInfoCursor.moveToNext()) {
                bannerImageList.add(adPictureInfoCursor.getString(1));
            }
        } else {
            ContentValues contentValues = new ContentValues();
            bannerImageList.add("https://thethreestooges.oss-cn-shenzhen.aliyuncs.com/BookListener/banner1.jpg");
            contentValues.put("ad_picture", "https://thethreestooges.oss-cn-shenzhen.aliyuncs.com/BookListener/banner1.jpg");
            db.insert("adPictureInfo", null, contentValues);
            bannerImageList.add("https://thethreestooges.oss-cn-shenzhen.aliyuncs.com/BookListener/banner2.jpg");
            contentValues.put("ad_picture", "https://thethreestooges.oss-cn-shenzhen.aliyuncs.com/BookListener/banner2.jpg");
            db.insert("adPictureInfo", null, contentValues);
            bannerImageList.add("https://thethreestooges.oss-cn-shenzhen.aliyuncs.com/BookListener/banner3.jpg");
            contentValues.put("ad_picture", "https://thethreestooges.oss-cn-shenzhen.aliyuncs.com/BookListener/banner3.jpg");
            db.insert("adPictureInfo", null, contentValues);
            bannerImageList.add("https://thethreestooges.oss-cn-shenzhen.aliyuncs.com/BookListener/banner4.jpg");
            contentValues.put("ad_picture", "https://thethreestooges.oss-cn-shenzhen.aliyuncs.com/BookListener/banner4.jpg");
            db.insert("adPictureInfo", null, contentValues);
        }
        if (adPictureInfoCursor != null) {
            adPictureInfoCursor.close();
        }
        return bannerImageList;
    }

    @Override
    public List<VoiceInfo> getRecommendInfoList() {
        List<VoiceInfo> recommendInfoList = new ArrayList<>();
        Cursor recommendCursor = db.query("voiceInfo", null, null, null, null, null, "time desc");
        if (recommendCursor != null && recommendCursor.getCount() > 0) {
            while (recommendCursor.moveToNext()) {
                VoiceInfo voiceInfo = new VoiceInfo(recommendCursor.getInt(0), recommendCursor.getInt(1),
                        recommendCursor.getString(2), recommendCursor.getString(3),
                        recommendCursor.getString(4), recommendCursor.getString(5),
                        recommendCursor.getInt(6));
                recommendInfoList.add(voiceInfo);
                if (recommendInfoList.size() == 9) {
                    break;
                }
            }
        }
        if (recommendCursor != null) {
            recommendCursor.close();
        }

        return recommendInfoList;
    }

    @Override
    public void addHistory(VoiceInfo voiceInfo, int user_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("voice_id", voiceInfo.get_id());
        contentValues.put("user_id", user_id);
        db.insert("historyInfo", null, contentValues);
        contentValues = new ContentValues();
        contentValues.put("time", voiceInfo.time + 1);
        db.update("voiceInfo", contentValues, "_id = ?", new String[]{String.valueOf(voiceInfo.get_id())});
    }

    @Override
    public List<VoiceInfo> getNewInfoList() {
        List<VoiceInfo> newInfoList = new ArrayList<>();
        Cursor newCursor = db.query("voiceInfo", null, null, null, null, null, "time asc");
        if (newCursor != null && newCursor.getCount() > 0) {
            while (newCursor.moveToNext()) {
                VoiceInfo voiceInfo = new VoiceInfo(newCursor.getInt(0), newCursor.getInt(1),
                        newCursor.getString(2), newCursor.getString(3),
                        newCursor.getString(4), newCursor.getString(5),
                        newCursor.getInt(6));
                newInfoList.add(voiceInfo);
                if (newInfoList.size() == 9) {
                    break;
                }
            }
        } else {

        }
        if (newCursor != null) {
            newCursor.close();
        }

        return newInfoList;
    }

    @Override
    public List<VoiceTypeInfo> getBookType() {
        List<VoiceTypeInfo> voiceTypeInfoList = new ArrayList<>();
        Cursor voiceTypeInfoCursor = db.query("voiceTypeInfo", null, null, null, null, null, null);
        if (voiceTypeInfoCursor != null && voiceTypeInfoCursor.getCount() > 0) {
            while (voiceTypeInfoCursor.moveToNext()) {
                VoiceTypeInfo voiceTypeInfo = new VoiceTypeInfo(voiceTypeInfoCursor.getInt(0), voiceTypeInfoCursor.getString(1));
                voiceTypeInfoList.add(voiceTypeInfo);
            }
        }
        if (voiceTypeInfoCursor != null) {
            voiceTypeInfoCursor.close();
        }
        return voiceTypeInfoList;
    }

    @Override
    public List<VoiceInfo> getVoiceInfoListWithType(VoiceTypeInfo voiceTypeInfo) {
        List<VoiceInfo> voiceInfoList = new ArrayList<>();
        Cursor voiceInfoCursor = db.query("voiceInfo", null, "voice_type_id = ?", new String[]{String.valueOf(voiceTypeInfo.get_id())}, null, null, null);
        if (voiceInfoCursor != null && voiceInfoCursor.getCount() > 0) {
            while (voiceInfoCursor.moveToNext()) {
                VoiceInfo voiceInfo = new VoiceInfo(voiceInfoCursor.getInt(0), voiceInfoCursor.getInt(1),
                        voiceInfoCursor.getString(2), voiceInfoCursor.getString(3),
                        voiceInfoCursor.getString(4), voiceInfoCursor.getString(5),
                        voiceInfoCursor.getInt(6));
                voiceInfoList.add(voiceInfo);
            }
        } else {

        }
        if (voiceInfoCursor != null) {
            voiceInfoCursor.close();
        }
        return voiceInfoList;
    }

    @Override
    public boolean checkIsCollection(int id) {
        Cursor collectionInfoCursor = db.query("voiceCollectionInfo", null, "voice_id = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (collectionInfoCursor != null && collectionInfoCursor.getCount() > 0) {
            collectionInfoCursor.close();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void deleteCollection(int id) {
        db.delete("voiceCollectionInfo", "voice_id = ?", new String[]{String.valueOf(id)});
    }

    @Override
    public void addCollection(int id, int user_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("voice_id", id);
        contentValues.put("user_id", user_id);
        db.insert("voiceCollectionInfo", null, contentValues);
    }

    @Override
    public List<CollectionInfo> getCollectionVoiceList(int user_id) {
        List<CollectionInfo> collectionInfoList = new ArrayList<>();
        Cursor voiceCollectionInfoCursor = db.query("voiceCollectionInfo", null, "user_id = ?", new String[]{String.valueOf(user_id)}, null, null, null);
        if (voiceCollectionInfoCursor != null && voiceCollectionInfoCursor.getCount() > 0) {
            while (voiceCollectionInfoCursor.moveToNext()) {
                CollectionInfo collectionInfo = new CollectionInfo(voiceCollectionInfoCursor.getInt(0), voiceCollectionInfoCursor.getInt(1), voiceCollectionInfoCursor.getInt(2));
                collectionInfoList.add(collectionInfo);
            }
        } else {

        }
        if (voiceCollectionInfoCursor != null) {
            voiceCollectionInfoCursor.close();
        }
        return collectionInfoList;
    }

    @Override
    public List<VoiceInfo> getCollectionVoiceInfoList(List<CollectionInfo> collectionInfoList) {
        List<VoiceInfo> voiceInfoList = new ArrayList<>();
        for (CollectionInfo collectionInfo : collectionInfoList) {
            Cursor voiceInfoCursor = db.query("voiceInfo", null, "_id = ?", new String[]{String.valueOf(collectionInfo.getVoice_id())}, null, null, null);
            if (voiceInfoCursor != null && voiceInfoCursor.getCount() > 0) {
                while (voiceInfoCursor.moveToNext()) {
                    VoiceInfo voiceInfo = new VoiceInfo(voiceInfoCursor.getInt(0), voiceInfoCursor.getInt(1),
                            voiceInfoCursor.getString(2), voiceInfoCursor.getString(3),
                            voiceInfoCursor.getString(4), voiceInfoCursor.getString(5),
                            voiceInfoCursor.getInt(6));
                    voiceInfoList.add(voiceInfo);
                }
            }
            if (voiceInfoCursor != null) {
                voiceInfoCursor.close();
            }
        }
        return voiceInfoList;
    }

    @Override
    public void updateCollectionInfo(VoiceInfo voiceInfo) {
        db.delete("voiceCollectionInfo", "voice_id = ?", new String[]{String.valueOf(voiceInfo.get_id())});
    }

    @Override
    public List<HistoryInfo> getHistoryInfoList(int user_id) {
        List<HistoryInfo> historyInfoList = new ArrayList<>();
        Cursor historyInfoCursor = db.query("historyInfo", null, "user_id = ?", new String[]{String.valueOf(user_id)}, null, null, null);
        if (historyInfoCursor != null && historyInfoCursor.getCount() > 0) {
            while (historyInfoCursor.moveToNext()) {
                HistoryInfo historyInfo = new HistoryInfo(historyInfoCursor.getInt(0), historyInfoCursor.getInt(1), historyInfoCursor.getInt(2));
                historyInfoList.add(historyInfo);
            }
        } else {

        }
        if (historyInfoCursor != null) {
            historyInfoCursor.close();
        }
        return historyInfoList;
    }

    @Override
    public List<VoiceInfo> getHistoryVoiceList(List<HistoryInfo> historyInfoList) {
        List<VoiceInfo> voiceInfoList = new ArrayList<>();
        for (HistoryInfo historyInfo : historyInfoList) {
            Cursor voiceInfoCursor = db.query("voiceInfo", null, "_id = ?", new String[]{String.valueOf(historyInfo.getVoice_id())}, null, null, null);
            if (voiceInfoCursor != null && voiceInfoCursor.getCount() > 0) {
                while (voiceInfoCursor.moveToNext()) {
                    VoiceInfo voiceInfo = new VoiceInfo(voiceInfoCursor.getInt(0), voiceInfoCursor.getInt(1),
                            voiceInfoCursor.getString(2), voiceInfoCursor.getString(3),
                            voiceInfoCursor.getString(4), voiceInfoCursor.getString(5),
                            voiceInfoCursor.getInt(6));
                    voiceInfoList.add(voiceInfo);
                }
            }
            if (voiceInfoCursor != null) {
                voiceInfoCursor.close();
            }
        }
        return voiceInfoList;
    }

    @Override
    public List<VoiceInfo> searchVoiceInfo(String s) {
        List<VoiceInfo> voiceInfoList = new ArrayList<>();
        Cursor voiceInfoCursor = db.query("voiceInfo", null, "voice_name like ?", new String[]{"%" + s + "%"}, null, null, null);
        if (voiceInfoCursor != null && voiceInfoCursor.getCount() > 0) {
            while (voiceInfoCursor.moveToNext()) {
                VoiceInfo voiceInfo = new VoiceInfo(voiceInfoCursor.getInt(0), voiceInfoCursor.getInt(1),
                        voiceInfoCursor.getString(2), voiceInfoCursor.getString(3),
                        voiceInfoCursor.getString(4), voiceInfoCursor.getString(5),
                        voiceInfoCursor.getInt(6));
                voiceInfoList.add(voiceInfo);
            }
        }
        if (voiceInfoCursor != null) {
            voiceInfoCursor.close();
        }
        return voiceInfoList;
    }

    @Override
    public void insertData(List<VoiceTypeInfo> voiceTypeInfoList, List<VoiceInfo> voiceInfoList) {
        for (VoiceTypeInfo voiceTypeInfo : voiceTypeInfoList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("_id", voiceTypeInfo.get_id());
            contentValues.put("voice_type_name", voiceTypeInfo.getType_name());
            db.insert("voiceTypeInfo",null,contentValues);
        }
        for (VoiceInfo voiceInfo : voiceInfoList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("_id", voiceInfo.get_id());
            contentValues.put("voice_type_id", voiceInfo.getTypeId());
            contentValues.put("voice_name", voiceInfo.getVoiceName());
            contentValues.put("voice_path", voiceInfo.getVoicePath());
            contentValues.put("voice_image", voiceInfo.getVoiceImage());
            contentValues.put("author", voiceInfo.getAuthor());
            contentValues.put("time", voiceInfo.getTime());
            db.insert("voiceInfo",null,contentValues);
        }
    }
}
