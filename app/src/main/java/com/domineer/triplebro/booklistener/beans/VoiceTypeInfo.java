package com.domineer.triplebro.booklistener.beans;

public class VoiceTypeInfo {

    public int _id;
    public String type_name;

    public VoiceTypeInfo() {

    }

    public VoiceTypeInfo(int _id, String type_name) {
        this._id = _id;
        this.type_name = type_name;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    @Override
    public String toString() {
        return "VoiceTypeInfo{" +
                "_id=" + _id +
                ", type_name='" + type_name + '\'' +
                '}';
    }
}
