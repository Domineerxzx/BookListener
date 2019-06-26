package com.domineer.triplebro.booklistener.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.domineer.triplebro.booklistener.R;
import com.domineer.triplebro.booklistener.activities.ListenBookActivity;
import com.domineer.triplebro.booklistener.activities.SearchActivity;
import com.domineer.triplebro.booklistener.adapters.RecommendAdapter;
import com.domineer.triplebro.booklistener.adapters.TypeNameAdapter;
import com.domineer.triplebro.booklistener.beans.VoiceInfo;
import com.domineer.triplebro.booklistener.beans.VoiceTypeInfo;
import com.domineer.triplebro.booklistener.interfaces.OnItemClickListener;
import com.domineer.triplebro.booklistener.managers.TypeManager;

import java.util.List;

public class TypeFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener,OnItemClickListener {

    private View fragment_type;
    private TextView tv_search;
    private ImageView iv_search;
    private RelativeLayout rl_search;
    private TypeManager typeManager;
    private RecyclerView rv_type_content;
    private ListView lv_type_name;
    private List<VoiceTypeInfo> voiceTypeInfoList;
    private TypeNameAdapter typeNameAdapter;
    private List<VoiceInfo> voiceInfoList;
    private RecommendAdapter typeContentAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        fragment_type = inflater.inflate(R.layout.fragment_type, null);
        initView();
        initData();
        setOnClickListener();
        return fragment_type;
    }

    private void initView() {
        tv_search = fragment_type.findViewById(R.id.tv_search);
        iv_search = fragment_type.findViewById(R.id.iv_search);
        rl_search = fragment_type.findViewById(R.id.rl_search);
        lv_type_name = fragment_type.findViewById(R.id.lv_type_name);
        rv_type_content = fragment_type.findViewById(R.id.rv_type_content);
        rv_type_content.setLayoutManager(new GridLayoutManager(getActivity(),2));
    }

    private void initData() {
        typeManager = new TypeManager(getActivity());
        voiceTypeInfoList = typeManager.getBookType();
        typeNameAdapter = new TypeNameAdapter(getActivity(), voiceTypeInfoList);
        lv_type_name.setAdapter(typeNameAdapter);
        VoiceTypeInfo voiceTypeInfo = voiceTypeInfoList.get(0);
        voiceInfoList = typeManager.getVoiceInfoListWithType(voiceTypeInfo);
        typeContentAdapter = new RecommendAdapter(getActivity(), voiceInfoList);
        rv_type_content.setAdapter(typeContentAdapter);
        typeContentAdapter.setOnItemClickListener(this);
    }

    private void setOnClickListener() {
        tv_search.setOnClickListener(this);
        iv_search.setOnClickListener(this);
        rl_search.setOnClickListener(this);
        lv_type_name.setOnItemClickListener(this);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        VoiceTypeInfo voiceTypeInfo = voiceTypeInfoList.get(position);
        voiceInfoList = typeManager.getVoiceInfoListWithType(voiceTypeInfo);
        typeContentAdapter.setData(voiceInfoList);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent listenBook = new Intent(getActivity(), ListenBookActivity.class);
        listenBook.putExtra("voiceInfo",voiceInfoList.get(position));
        getActivity().startActivity(listenBook);
    }

    @Override
    public void onItemLongClick(View view) {

    }
}
