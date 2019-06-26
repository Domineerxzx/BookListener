package com.domineer.triplebro.booklistener.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.domineer.triplebro.booklistener.R;
import com.domineer.triplebro.booklistener.beans.VoiceInfo;
import com.domineer.triplebro.booklistener.interfaces.OnItemClickListener;
import java.util.List;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder> {

    private Context context;
    private List<VoiceInfo> data;
    private OnItemClickListener onItemClickListener;

    public RecommendAdapter(Context context, List<VoiceInfo> data) {
        this.context = context;
        this.data = data;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public List<VoiceInfo> getData() {
        return data;
    }

    public void setData(List<VoiceInfo> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_recommend, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(inflate, onItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        ViewHolder holder = viewHolder;
        holder.tv_voice_name.setText(data.get(i).getVoiceName());
        holder.tv_author.setText(data.get(i).getAuthor());
        holder.tv_time.setText(String.valueOf(data.get(i).getTime()));
        Glide.with(context).load(data.get(i).getVoiceImage()).into(holder.iv_voice_image);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private OnItemClickListener onItemClickListener;
        public TextView tv_voice_name;
        public TextView tv_author;
        public TextView tv_time;
        public ImageView iv_voice_image;


        public ViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
            initView(itemView);
        }

        private void initView(View itemView) {
            tv_voice_name = itemView.findViewById(R.id.tv_voice_name);
            tv_author = itemView.findViewById(R.id.tv_author);
            tv_time = itemView.findViewById(R.id.tv_time);
            iv_voice_image = itemView.findViewById(R.id.iv_voice_image);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getPosition());
        }
    }
}
