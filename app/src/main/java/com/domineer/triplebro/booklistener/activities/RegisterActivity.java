package com.domineer.triplebro.booklistener.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.domineer.triplebro.booklistener.R;
import com.domineer.triplebro.booklistener.activities.LoginActivity;
import com.domineer.triplebro.booklistener.handlers.CountDownHandler;
import com.domineer.triplebro.booklistener.managers.RegisterManager;

public class RegisterActivity extends Activity implements View.OnClickListener {

    private Button bt_login;
    private ImageView iv_close_create;
    private RegisterManager registerManager;
    private EditText et_phone_number;
    private EditText et_username;
    private EditText et_password;
    private EditText et_request_code;
    private Button bt_request_code;
    private Button bt_create;
    private String phone_number;
    private String password;
    private String username;
    private String request_code;
    private CheckBox cb_agree;
    private CountDownHandler countDownHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initData();
        setOnClickListener();
    }

    private void initData() {
        registerManager = new RegisterManager(this);
    }

    private void setOnClickListener() {
        bt_login.setOnClickListener(this);
        iv_close_create.setOnClickListener(this);
        bt_create.setOnClickListener(this);
        bt_request_code.setOnClickListener(this);
    }

    private void initView() {
        bt_login = findViewById(R.id.bt_login);
        iv_close_create = findViewById(R.id.iv_close_create);
        et_phone_number = findViewById(R.id.et_phone_number);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        et_request_code = findViewById(R.id.et_request_code);
        bt_request_code = findViewById(R.id.bt_request_code);
        bt_create = findViewById(R.id.bt_create);
        cb_agree = findViewById(R.id.cb_agree);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.iv_close_create:
                finish();
                break;
            case R.id.bt_request_code:
                phone_number = et_phone_number.getText().toString();
                if (phone_number.length() == 11) {
                    registerManager.getRequestCode(phone_number);
                    countDownHandler = new CountDownHandler(this, bt_request_code);
                    ServiceConnection serviceConnection = registerManager.countDown(countDownHandler);
                    countDownHandler.setServiceConnection(serviceConnection);
                } else {
                    Toast.makeText(this, "手机号有误", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_create:
                phone_number = et_phone_number.getText().toString();
                password = et_password.getText().toString();
                username = et_username.getText().toString();
                request_code = et_request_code.getText().toString();
                phone_number = et_phone_number.getText().toString();
                if (username.length() == 0) {
                    Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                } else if (phone_number.length() != 11) {
                    Toast.makeText(this, "手机号有误", Toast.LENGTH_SHORT).show();
                } else if (request_code.length() != 4) {
                    Toast.makeText(this, "验证码不能少于4位数", Toast.LENGTH_SHORT).show();
                } else if (password.length() == 0) {
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                } else if (!cb_agree.isChecked()) {
                    Toast.makeText(this, "请查看并同意条款与条件", Toast.LENGTH_SHORT).show();
                } else {
                    registerManager.register(phone_number, request_code, password, username);
                }
                break;
        }
    }
}
