package com.domineer.triplebro.booklistener.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.domineer.triplebro.booklistener.R;
import com.domineer.triplebro.booklistener.activities.CollectionActivity;
import com.domineer.triplebro.booklistener.beans.VoiceInfo;
import com.domineer.triplebro.booklistener.managers.CollectionManager;

import java.util.List;

public class CollectionAdapter extends BaseAdapter {

    private Context context;
    private List<VoiceInfo> collectionInfoList;
    private CollectionManager collectionManager;
    public CollectionAdapter(Context context, List<VoiceInfo> collectionInfoList, CollectionManager collectionManager) {
        this.context = context;
        this.collectionInfoList = collectionInfoList;
        this.collectionManager = collectionManager;
    }

    public List<VoiceInfo> getCollectionInfoList() {
        return collectionInfoList;
    }

    public void setCollectionInfoList(List<VoiceInfo> collectionInfoList) {
        this.collectionInfoList = collectionInfoList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return collectionInfoList.size();
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
            convertView = View.inflate(context, R.layout.item_voice, null);
            viewHolder.tv_time = convertView.findViewById(R.id.tv_time);
            viewHolder.tv_voice_name = convertView.findViewById(R.id.tv_voice_name);
            viewHolder.tv_author = convertView.findViewById(R.id.tv_author);
            viewHolder.iv_voice_image = convertView.findViewById(R.id.iv_voice_image);
            viewHolder.iv_collection = convertView.findViewById(R.id.iv_collection);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.tv_time.setText(String.valueOf(collectionInfoList.get(position).getTime()));
        viewHolder.tv_voice_name.setText(collectionInfoList.get(position).getVoiceName());
        viewHolder.tv_author.setText(collectionInfoList.get(position).getAuthor());
        Glide.with(context).load(collectionInfoList.get(position).getVoiceImage()).into(viewHolder.iv_voice_image);
        viewHolder.iv_collection.setBackgroundResource(R.mipmap.collection_click);
        viewHolder.iv_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.iv_collection.setBackgroundResource(R.mipmap.collection_click);
                collectionManager.updateCollectionInfo(collectionInfoList.get(position));
                collectionInfoList.remove(position);
                setCollectionInfoList(collectionInfoList);
            }
        });
        return convertView;
    }

    public class ViewHolder{

        private TextView tv_time;
        private TextView tv_voice_name;
        private TextView tv_author;
        private ImageView iv_voice_image;
        private ImageView iv_collection;

    }
}
