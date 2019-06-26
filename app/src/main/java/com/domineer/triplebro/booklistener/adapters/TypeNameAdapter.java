package com.domineer.triplebro.booklistener.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.domineer.triplebro.booklistener.R;
import com.domineer.triplebro.booklistener.beans.VoiceTypeInfo;

import java.util.List;

public class TypeNameAdapter extends BaseAdapter {

    private Context context;
    private List<VoiceTypeInfo> voiceTypeInfoList;

    public TypeNameAdapter(Context context, List<VoiceTypeInfo> voiceTypeInfoList) {
        this.context = context;
        this.voiceTypeInfoList = voiceTypeInfoList;
    }

    public void setVoiceTypeInfoList(List<VoiceTypeInfo> voiceTypeInfoList) {
        this.voiceTypeInfoList = voiceTypeInfoList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return voiceTypeInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_type_name, null);
            viewHolder.tv_type_name = convertView.findViewById(R.id.tv_type_name);
            viewHolder.v_tag = convertView.findViewById(R.id.v_tag);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_type_name.setText(voiceTypeInfoList.get(position).getType_name());
        return convertView;
    }

    private class ViewHolder {
        private TextView tv_type_name;
        private View v_tag;
    }
}
