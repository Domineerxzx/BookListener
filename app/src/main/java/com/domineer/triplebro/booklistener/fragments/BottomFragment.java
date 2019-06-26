package com.domineer.triplebro.booklistener.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.domineer.triplebro.booklistener.R;

public class BottomFragment extends Fragment implements View.OnClickListener {

    private View fragment_bottom;
    private LinearLayout ll_recommend;
    private LinearLayout ll_type;
    private LinearLayout ll_myself;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private Button bt_recommend;
    private Button bt_type;
    private Button bt_myself;
    private TextView tv_recommend;
    private TextView tv_type;
    private TextView tv_myself;

    private Button lastFunctionButton;
    private TextView lastFunctionTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        fragment_bottom = inflater.inflate(R.layout.fragment_bottom, null);
        initView();
        setOnClick();
        fragmentManager = getActivity().getFragmentManager();
        return fragment_bottom;
    }

    private void initView() {
        ll_recommend = fragment_bottom.findViewById(R.id.ll_recommend);
        ll_type = fragment_bottom.findViewById(R.id.ll_type);
        ll_myself = fragment_bottom.findViewById(R.id.ll_myself);
        bt_recommend = fragment_bottom.findViewById(R.id.bt_recommend);
        bt_type = fragment_bottom.findViewById(R.id.bt_type);
        bt_myself = fragment_bottom.findViewById(R.id.bt_myself);
        tv_recommend = fragment_bottom.findViewById(R.id.tv_recommend);
        tv_type = fragment_bottom.findViewById(R.id.tv_type);
        tv_myself = fragment_bottom.findViewById(R.id.tv_myself);

        if (lastFunctionTextView == null) {
            lastFunctionTextView = tv_recommend;
        }
        if (lastFunctionButton == null) {
            lastFunctionButton = bt_recommend;
        }

    }

    public void setOnClick() {

        ll_recommend.setOnClickListener(this);
        ll_type.setOnClickListener(this);
        ll_myself.setOnClickListener(this);
        bt_recommend.setOnClickListener(this);
        bt_type.setOnClickListener(this);
        bt_myself.setOnClickListener(this);
        tv_recommend.setOnClickListener(this);
        tv_type.setOnClickListener(this);
        tv_myself.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_recommend:
            case R.id.bt_recommend:
            case R.id.tv_recommend:
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fl_top, new RecommendFragment());
                transaction.commit();
                changeImageForButton(lastFunctionButton, bt_recommend);
                lastFunctionTextView.setTextColor(Color.GRAY);
                tv_recommend.setTextColor(0xFFC7452E);
                lastFunctionTextView = tv_recommend;
                break;
            case R.id.ll_type:
            case R.id.bt_type:
            case R.id.tv_type:
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fl_top, new TypeFragment());
                transaction.commit();
                changeImageForButton(lastFunctionButton, bt_type);
                lastFunctionTextView.setTextColor(Color.GRAY);
                tv_type.setTextColor(0xFFC7452E);
                lastFunctionTextView = tv_type;
                break;
            case R.id.ll_myself:
            case R.id.bt_myself:
            case R.id.tv_myself:
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fl_top, new MyselfFragment());
                transaction.commit();
                changeImageForButton(lastFunctionButton, bt_myself);
                lastFunctionTextView.setTextColor(Color.GRAY);
                tv_myself.setTextColor(0xFFC7452E);
                lastFunctionTextView = tv_myself;
                break;
        }
    }

    private void changeImageForButton(Button lastFunctionButton, Button onClickButton) {
        switch (lastFunctionButton.getId()) {
            case R.id.bt_recommend:
                lastFunctionButton.setBackgroundResource(R.mipmap.recommend_unclick);
                break;
            case R.id.bt_type:
                lastFunctionButton.setBackgroundResource(R.mipmap.type_unclick);
                break;
            case R.id.bt_myself:
                lastFunctionButton.setBackgroundResource(R.mipmap.myself_unclick);
                break;
        }
        switch (onClickButton.getId()) {
            case R.id.bt_recommend:
                onClickButton.setBackgroundResource(R.mipmap.recommend_click);
                break;
            case R.id.bt_type:
                onClickButton.setBackgroundResource(R.mipmap.type_click);
                break;
            case R.id.bt_myself:
                onClickButton.setBackgroundResource(R.mipmap.myself_click);
                break;
        }
        this.lastFunctionButton = onClickButton;
    }

}
