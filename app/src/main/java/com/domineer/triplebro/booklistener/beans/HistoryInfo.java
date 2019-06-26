package com.domineer.triplebro.booklistener.beans;

import java.io.Serializable;

public class HistoryInfo implements Serializable {

    public int _id;
    public int voice_id;
    public int user_id;

    public HistoryInfo() {

    }

    public HistoryInfo(int _id, int voice_id, int user_id) {
        this._id = _id;
        this.voice_id = voice_id;
        this.user_id = user_id;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getVoice_id() {
        return voice_id;
    }

    public void setVoice_id(int voice_id) {
        this.voice_id = voice_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "HistoryInfo{" +
                "_id=" + _id +
                ", voice_id=" + voice_id +
                ", user_id=" + user_id +
                '}';
    }
}
