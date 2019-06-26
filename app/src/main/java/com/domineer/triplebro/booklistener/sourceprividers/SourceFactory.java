package com.domineer.triplebro.booklistener.sourceprividers;

import android.content.Context;

import com.domineer.triplebro.booklistener.enmuration.SourceType;
import com.domineer.triplebro.booklistener.interfaces.ISource;

public class SourceFactory {
    public static ISource sourceCreate(Context context, SourceType type){
        return SourceMap.generateSource(context).get(type);
    }
}
