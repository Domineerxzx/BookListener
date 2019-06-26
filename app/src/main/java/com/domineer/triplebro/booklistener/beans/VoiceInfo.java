package com.domineer.triplebro.booklistener.beans;

import java.io.Serializable;

public class VoiceInfo implements Serializable {

    public int _id;
    public int typeId;
    public String voiceName;
    public String voicePath;
    public String voiceImage;
    public String author;
    public int time;

    public VoiceInfo() {

    }

    public VoiceInfo(int _id, int typeId, String voiceName, String voicePath, String voiceImage, String author, int time) {
        this._id = _id;
        this.typeId = typeId;
        this.voiceName = voiceName;
        this.voicePath = voicePath;
        this.voiceImage = voiceImage;
        this.author = author;
        this.time = time;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getVoiceName() {
        return voiceName;
    }

    public void setVoiceName(String voiceName) {
        this.voiceName = voiceName;
    }

    public String getVoicePath() {
        return voicePath;
    }

    public void setVoicePath(String voicePath) {
        this.voicePath = voicePath;
    }

    public String getVoiceImage() {
        return voiceImage;
    }

    public void setVoiceImage(String voiceImage) {
        this.voiceImage = voiceImage;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "VoiceInfo{" +
                "_id=" + _id +
                ", typeId=" + typeId +
                ", voiceName='" + voiceName + '\'' +
                ", voicePath='" + voicePath + '\'' +
                ", voiceImage='" + voiceImage + '\'' +
                ", author='" + author + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
