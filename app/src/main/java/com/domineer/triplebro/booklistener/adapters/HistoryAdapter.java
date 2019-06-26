package com.domineer.triplebro.booklistener.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.domineer.triplebro.booklistener.R;
import com.domineer.triplebro.booklistener.beans.VoiceInfo;
import com.domineer.triplebro.booklistener.managers.CollectionManager;
import com.domineer.triplebro.booklistener.managers.HistoryManager;

import java.util.List;

public class HistoryAdapter extends BaseAdapter {

    private Context context;
    private List<VoiceInfo> historyInfoList;
    private HistoryManager historyManager;
    public HistoryAdapter(Context context, List<VoiceInfo> historyInfoList, HistoryManager historyManager) {
        this.context = context;
        this.historyInfoList = historyInfoList;
        this.historyManager = historyManager;
    }

    public List<VoiceInfo> getHistoryInfoList() {
        return historyInfoList;
    }

    public void setHistoryInfoList(List<VoiceInfo> historyInfoList) {
        this.historyInfoList = historyInfoList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return historyInfoList.size();
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
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_history, null);
            viewHolder.tv_time = convertView.findViewById(R.id.tv_time);
            viewHolder.tv_voice_name = convertView.findViewById(R.id.tv_voice_name);
            viewHolder.tv_author = convertView.findViewById(R.id.tv_author);
            viewHolder.iv_voice_image = convertView.findViewById(R.id.iv_voice_image);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.tv_time.setText(String.valueOf(historyInfoList.get(position).getTime()));
        viewHolder.tv_voice_name.setText(historyInfoList.get(position).getVoiceName());
        viewHolder.tv_author.setText(historyInfoList.get(position).getAuthor());
        Glide.with(context).load(historyInfoList.get(position).getVoiceImage()).into(viewHolder.iv_voice_image);
        return convertView;
    }

    public class ViewHolder{

        private TextView tv_time;
        private TextView tv_voice_name;
        private TextView tv_author;
        private ImageView iv_voice_image;

    }
}
