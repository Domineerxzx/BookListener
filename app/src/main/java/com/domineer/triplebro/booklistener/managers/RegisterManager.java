package com.domineer.triplebro.booklistener.managers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.domineer.triplebro.booklistener.handlers.CountDownHandler;
import com.domineer.triplebro.booklistener.services.CountDownService;
import com.domineer.triplebro.booklistener.services.NetworkConnectionService;

public class RegisterManager implements ServiceConnection {
    private Context context;
    private String phone_number;
    private String request_code;
    private String password;
    private String nickname;

    private CountDownHandler countDownHandler;

    private int registerState;

    private static final int STATE_GET_REQUEST = 0;
    private static final int STATE_REGISTER = 1;

    public RegisterManager(Context context) {
        this.context = context;
    }

    public void getRequestCode(String phone_number){
        this.phone_number = phone_number;
        Intent intent = new Intent(context, NetworkConnectionService.class);
        context.bindService(intent,this,Context.BIND_AUTO_CREATE);
        registerState = STATE_GET_REQUEST;
    }

    public void register(String phone_number, String request_code, String password, String nickname){
        this.phone_number = phone_number;
        this.request_code = request_code;
        this.password = password;
        this.nickname = nickname;
        Intent intent = new Intent(context, NetworkConnectionService.class);
        context.bindService(intent,this,Context.BIND_AUTO_CREATE);
        registerState = STATE_REGISTER;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        switch (registerState){
            case STATE_GET_REQUEST:
                NetworkConnectionService.MyBinder getRequestCode = (NetworkConnectionService.MyBinder) service;
                getRequestCode.getRequestCode(context,phone_number,this);
                break;
            case STATE_REGISTER:
                NetworkConnectionService.MyBinder register = (NetworkConnectionService.MyBinder) service;
                register.register(context,phone_number,request_code,password,nickname,this);
                break;
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    public ServiceConnection countDown(final CountDownHandler countDownHandler) {
        this.countDownHandler = countDownHandler;
        Intent intent = new Intent(context, CountDownService.class);
        ServiceConnection conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                CountDownService.MyBinder countDown = (CountDownService.MyBinder) service;
                countDown.countDown(countDownHandler);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        context.bindService(intent, conn, Context.BIND_AUTO_CREATE);
        return conn;
    }
}
