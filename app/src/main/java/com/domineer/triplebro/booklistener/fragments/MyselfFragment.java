package com.domineer.triplebro.booklistener.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.domineer.triplebro.booklistener.R;
import com.domineer.triplebro.booklistener.activities.CollectionActivity;
import com.domineer.triplebro.booklistener.activities.HistoryActivity;
import com.domineer.triplebro.booklistener.activities.LoginActivity;
import com.domineer.triplebro.booklistener.activities.SettingActivity;

public class MyselfFragment extends Fragment implements View.OnClickListener {

    private View fragment_myself;
    private ImageView iv_user_head;
    private ImageView iv_collection;
    private ImageView iv_setting;
    private RelativeLayout rl_collection;
    private RelativeLayout rl_setting;
    private LinearLayout ll_user_info;
    private TextView tv_nickname;
    private TextView tv_username;
    private TextView tv_collection;
    private TextView tv_setting;
    private ImageView iv_history;
    private RelativeLayout rl_history;
    private TextView tv_history;
    private SharedPreferences userInfo;
    private String username;
    private String nickname;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        fragment_myself = inflater.inflate(R.layout.fragment_myself, null);
        initView();
        initData();
        setOnClickListener();
        return fragment_myself;
    }

    @Override
    public void onStart() {
        super.onStart();
        initData();
    }

    private void initView() {
        iv_collection = (ImageView) fragment_myself.findViewById(R.id.iv_collection);
        iv_history = (ImageView) fragment_myself.findViewById(R.id.iv_history);
        iv_setting = (ImageView) fragment_myself.findViewById(R.id.iv_setting);
        rl_collection = (RelativeLayout) fragment_myself.findViewById(R.id.rl_collection);
        rl_history = (RelativeLayout) fragment_myself.findViewById(R.id.rl_history);
        rl_setting = (RelativeLayout) fragment_myself.findViewById(R.id.rl_setting);
        ll_user_info = (LinearLayout) fragment_myself.findViewById(R.id.ll_user_info);
        tv_nickname = (TextView) fragment_myself.findViewById(R.id.tv_nickname);
        tv_username = (TextView) fragment_myself.findViewById(R.id.tv_username);
        tv_collection = (TextView) fragment_myself.findViewById(R.id.tv_collection);
        tv_history = (TextView) fragment_myself.findViewById(R.id.tv_history);
        tv_setting = (TextView) fragment_myself.findViewById(R.id.tv_setting);
    }

    private void initData() {
        userInfo = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        username = userInfo.getString("phone_number", "");
        nickname = userInfo.getString("nickname", "");
        if (TextUtils.isEmpty(username) && TextUtils.isEmpty(nickname)) {
            tv_username.setText(R.string.usernameDefault);
            tv_nickname.setText(R.string.nicknameDefault);
        } else {
            tv_username.setText("ID：" + username);
            tv_nickname.setText(nickname);
        }
    }

    private void setOnClickListener() {
        iv_collection.setOnClickListener(this);
        iv_history.setOnClickListener(this);
        iv_setting.setOnClickListener(this);
        rl_collection.setOnClickListener(this);
        rl_history.setOnClickListener(this);
        rl_setting.setOnClickListener(this);
        ll_user_info.setOnClickListener(this);
        tv_nickname.setOnClickListener(this);
        tv_username.setOnClickListener(this);
        tv_collection.setOnClickListener(this);
        tv_history.setOnClickListener(this);
        tv_setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_collection:
            case R.id.tv_collection:
            case R.id.rl_collection:
                if(userInfo.getInt("user_id",-1) == -1){
                    Toast.makeText(getActivity(), "还没登录呢", Toast.LENGTH_SHORT).show();
                }else{
                    Intent collection = new Intent(getActivity(), CollectionActivity.class);
                    getActivity().startActivity(collection);
                }
                break;
            case R.id.iv_history:
            case R.id.tv_history:
            case R.id.rl_history:
                if(userInfo.getInt("user_id",-1) == -1){
                    Toast.makeText(getActivity(), "还没登录呢", Toast.LENGTH_SHORT).show();
                }else{
                    Intent history = new Intent(getActivity(), HistoryActivity.class);
                    getActivity().startActivity(history);
                }
                break;
            case R.id.iv_setting:
            case R.id.tv_setting:
            case R.id.rl_setting:
                Intent setting = new Intent(getActivity(), SettingActivity.class);
                getActivity().startActivity(setting);
                break;
            case R.id.tv_username:
            case R.id.tv_nickname:
            case R.id.ll_user_info:
                String username = tv_username.getText().toString().trim();
                String nickname = tv_nickname.getText().toString().trim();
                if (username.equals("暂无登录信息") || nickname.equals("点击  登录/注册")) {
                    Intent login = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivity(login);
                }
                break;
        }
    }
}
