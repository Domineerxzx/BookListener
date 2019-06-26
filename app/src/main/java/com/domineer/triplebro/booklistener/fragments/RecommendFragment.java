package com.domineer.triplebro.booklistener.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.domineer.triplebro.booklistener.activities.ListenBookActivity;
import com.domineer.triplebro.booklistener.R;
import com.domineer.triplebro.booklistener.activities.SearchActivity;
import com.domineer.triplebro.booklistener.adapters.RecommendAdapter;
import com.domineer.triplebro.booklistener.beans.VoiceInfo;
import com.domineer.triplebro.booklistener.interfaces.OnItemClickListener;
import com.domineer.triplebro.booklistener.managers.RecommendManager;
import com.domineer.triplebro.booklistener.utils.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class RecommendFragment extends Fragment implements View.OnClickListener, OnItemClickListener {

    private View fragment_recommend;
    private RecyclerView rv_recommend;
    private RecyclerView rv_new;
    private Banner bn_banner;
    private RecommendManager recommendManager;
    private List<String> bannerImageList;
    private List<VoiceInfo> recommendInfoList;
    private RecommendAdapter recommendAdapter;
    private TextView tv_search;
    private ImageView iv_search;
    private RelativeLayout rl_search;
    private List<VoiceInfo> newInfoList;
    private RecommendAdapter newAdapter;
    private int user_id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        fragment_recommend = View.inflate(getActivity(), R.layout.fragment_recommend, null);
        initView();
        initData();
        setOnClickListener();
        return fragment_recommend;
    }

    @Override
    public void onStart() {
        super.onStart();
        initData();
    }

    private void initView() {
        rv_recommend = fragment_recommend.findViewById(R.id.rv_recommend);
        rv_new = fragment_recommend.findViewById(R.id.rv_new);
        bn_banner = fragment_recommend.findViewById(R.id.bn_banner);
        bn_banner.setImageLoader(new GlideImageLoader());
        bn_banner.isAutoPlay(true);
        bn_banner.setDelayTime(5000);
        bn_banner.setIndicatorGravity(BannerConfig.CENTER);
        rv_recommend.setLayoutManager(new GridLayoutManager(getActivity(), 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rv_new.setLayoutManager(new GridLayoutManager(getActivity(), 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        tv_search = fragment_recommend.findViewById(R.id.tv_search);
        iv_search = fragment_recommend.findViewById(R.id.iv_search);
        rl_search = fragment_recommend.findViewById(R.id.rl_search);
    }

    private void initData() {
        SharedPreferences userInfo = getActivity().getSharedPreferences("userInfo", MODE_PRIVATE);
        user_id = userInfo.getInt("user_id", -1);
        recommendManager = new RecommendManager(getActivity());
        bannerImageList = recommendManager.getBannerImageList();
        bn_banner.setImages(bannerImageList);
        bn_banner.start();
        recommendInfoList = recommendManager.getRecommendInfoList();
        recommendAdapter = new RecommendAdapter(getActivity(), recommendInfoList);
        rv_recommend.setAdapter(recommendAdapter);
        recommendAdapter.setOnItemClickListener(this);
        newInfoList = recommendManager.getNewInfoList();
        newAdapter = new RecommendAdapter(getActivity(), newInfoList);
        rv_new.setAdapter(newAdapter);
        newAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent listenBook = new Intent(getActivity(), ListenBookActivity.class);
                listenBook.putExtra("voiceInfo",newInfoList.get(position));
                getActivity().startActivity(listenBook);
                recommendManager.addHistory(newInfoList.get(position),user_id);
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
    }

    private void setOnClickListener() {
        tv_search.setOnClickListener(this);
        iv_search.setOnClickListener(this);
        rl_search.setOnClickListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent listenBook = new Intent(getActivity(), ListenBookActivity.class);
        listenBook.putExtra("voiceInfo",recommendInfoList.get(position));
        getActivity().startActivity(listenBook);
        recommendManager.addHistory(recommendInfoList.get(position),user_id);
    }

    @Override
    public void onItemLongClick(View view) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_search:
            case R.id.iv_search:
            case R.id.rl_search:
                Intent search = new Intent(getActivity(), SearchActivity.class);
                getActivity().startActivity(search);
                break;
        }
    }
}
