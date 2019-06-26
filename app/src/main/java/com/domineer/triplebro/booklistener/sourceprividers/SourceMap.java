package com.domineer.triplebro.booklistener.sourceprividers;

import android.content.Context;

import com.domineer.triplebro.booklistener.enmuration.SourceType;
import com.domineer.triplebro.booklistener.interfaces.ISource;
import com.domineer.triplebro.booklistener.sourceops.AssetsOP;
import com.domineer.triplebro.booklistener.sourceops.DataBaseOP;
import com.domineer.triplebro.booklistener.sourceops.HttpOP;

import java.util.HashMap;
import java.util.Map;

public class SourceMap {

    public static Map<SourceType,ISource> generateSource(Context context){
        Map<SourceType, ISource> sourceMap = new HashMap<>();
        sourceMap.put(SourceType.SOURCE_FROM_SERVER,new HttpOP(context));
        sourceMap.put(SourceType.SOURCE_FROM_ASSETS,new AssetsOP(context));
        sourceMap.put(SourceType.SOURCE_FROM_DB,new DataBaseOP(context));
        return sourceMap;
    }
}
