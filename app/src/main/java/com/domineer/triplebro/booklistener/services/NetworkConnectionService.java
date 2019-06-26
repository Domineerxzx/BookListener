package com.domineer.triplebro.booklistener.services;

import android.app.Activity;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.domineer.triplebro.booklistener.database.MyOpenHelper;
import com.domineer.triplebro.booklistener.handlers.CountDownHandler;
import com.domineer.triplebro.booklistener.handlers.LoginHandler;
import com.domineer.triplebro.booklistener.handlers.RegisterHandler;
import com.domineer.triplebro.booklistener.properties.ProjectProperties;
import com.domineer.triplebro.booklistener.utils.HttpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

public class NetworkConnectionService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public class MyBinder extends Binder {

        public void login(Context context, String phone_number, String password, ServiceConnection serviceConnection) {
            NetworkConnectionService.this.login(context, phone_number, password, serviceConnection);
        }

        public void register(Context context, String phone_number, String request_code, String password, String nickname, ServiceConnection serviceConnection) {
            NetworkConnectionService.this.register(context, phone_number, request_code, password, nickname, serviceConnection);
        }

        public void getRequestCode(Context context, String phone_number, ServiceConnection serviceConnection) {
            NetworkConnectionService.this.getRequestCode(context, phone_number, serviceConnection);
        }


    }

    private void updateRegisterInfo(final Context context, final String phone_number, final String password, final String nickname, ServiceConnection serviceConnection, final RegisterHandler registerHandler, final long userInfo) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("userPhoneNumber", phone_number);
        builder.add("userNickName", nickname);
        builder.add("userPassword", password);
        builder.build();
        HttpUtils.sendOkHttpRequest(ProjectProperties.ADDRESS_UPDATE_REGISTER_INFO, builder, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String register_message = response.body().string();
                if (register_message.equals("{\"message\":1}")) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putInt("user_id", (int) userInfo);
                    edit.putString("nickname", nickname);
                    edit.putString("phone_number", phone_number);
                    edit.putString("password", password);
                    edit.commit();
                    registerHandler.sendEmptyMessage(ProjectProperties.REGISTER_SUCCESS);
                } else {
                    registerHandler.sendEmptyMessage(ProjectProperties.REGISTER_FAILED);
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "更新注册信息失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void getRequestCode(final Context context, final String phone_number, ServiceConnection serviceConnection) {
        final RegisterHandler registerHandler = new RegisterHandler(context, serviceConnection);
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("phoneNumber", phone_number);
        builder.build();
        HttpUtils.sendOkHttpRequest(ProjectProperties.ADDRESS_GET_REQUEST_CODE, builder, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String register_message = response.body().string();
                System.out.println("registerRequestCode------------------------------------" + register_message);
                if (register_message.contains("{\"message\":1}")) {
                    registerHandler.sendEmptyMessage(ProjectProperties.GET_REQUEST_CODE_SUCCESS);
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "获取验证码", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    registerHandler.sendEmptyMessage(ProjectProperties.GET_REQUEST_CODE_FAILED);
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "获取验证码失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void register(final Context context, final String phone_number, String request_code, final String password, final String nickname, final ServiceConnection serviceConnection) {
        final RegisterHandler registerHandler = new RegisterHandler(context, serviceConnection);
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("phoneNumber", phone_number);
        builder.add("registerNumber", request_code);
        builder.build();
        HttpUtils.sendOkHttpRequest(ProjectProperties.ADDRESS_REGISTER, builder, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                long userInfo;
                String register_message = response.body().string();
                System.out.println("register------------------------------------" + register_message);
                if (register_message.equals("{\"message\":1}")) {
                    MyOpenHelper myOpenHelper = new MyOpenHelper(context);
                    SQLiteDatabase writableDatabase = myOpenHelper.getWritableDatabase();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("phone_number", phone_number);
                    contentValues.put("password", password);
                    contentValues.put("nickname", nickname);
                    userInfo = writableDatabase.insert("userInfo", null, contentValues);
                    updateRegisterInfo(context, phone_number, password, nickname, serviceConnection, registerHandler,userInfo);
                    if (userInfo != -1) {
                        System.out.println("SUCCESS-----------------------数据库插入成功" + userInfo);
                        writableDatabase.close();
                    } else {
                        System.out.println("error-----------------------数据库插入失败" + userInfo);
                        writableDatabase.close();
                    }
                } else {
                    registerHandler.sendEmptyMessage(ProjectProperties.REGISTER_FAILED);
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "验证码错误", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void login(final Context context, final String phone_number, final String password, ServiceConnection serviceConnection) {

        final LoginHandler loginHandler = new LoginHandler(context, serviceConnection);
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("userPhone", phone_number);
        builder.add("password", password);
        builder.build();
        HttpUtils.sendOkHttpRequest(ProjectProperties.ADDRESS_LOGIN, builder, new Callback() {

            private int _id;
            private String nickname;

            @Override
            public void onFailure(Call call, IOException e) {
                boolean isCheckSuccess = false;
                MyOpenHelper myOpenHelper = new MyOpenHelper(context);
                SQLiteDatabase db = myOpenHelper.getWritableDatabase();
                Cursor userInfo = db.query("userInfo", new String[]{"_id","password", "nickname"}, "phone_number = ?", new String[]{phone_number}, null, null, null);
                if (userInfo != null && userInfo.getCount() > 0) {
                    while (userInfo.moveToNext()) {
                        String pw = userInfo.getString(1);
                        if (password.equals(pw)) {
                            isCheckSuccess = true;
                            SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.putInt("user_id",userInfo.getInt(0));
                            edit.putString("phone_number", phone_number);
                            edit.putString("nickname", userInfo.getString(2));
                            edit.putString("password", password);
                            edit.commit();
                            loginHandler.sendEmptyMessage(ProjectProperties.LOGIN_SUCCESS);
                            return;
                        }
                    }
                    if (isCheckSuccess == false) {
                        loginHandler.sendEmptyMessage(ProjectProperties.LOGIN_FAILED);
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "密码错误", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    loginHandler.sendEmptyMessage(ProjectProperties.LOGIN_FAILED);
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "未找到该用户", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String login_message = response.body().string();
                System.out.println("login" + "------------------------------------" + login_message);
                if (login_message.contains("\"message\":1")) {

                    MyOpenHelper myOpenHelper = new MyOpenHelper(context);
                    SQLiteDatabase writableDatabase = myOpenHelper.getWritableDatabase();
                    Cursor userInfo = writableDatabase.query("userInfo", new String[]{"_id","nickname"},
                            "phone_number=?", new String[]{phone_number}, null, null, null);
                    if (userInfo != null || userInfo.getCount() > 0) {
                        while (userInfo.moveToNext()) {
                            _id = userInfo.getInt(0);
                            nickname = userInfo.getString(1);
                            System.out.println(nickname);
                        }
                    }
                    writableDatabase.close();
                    SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putInt("user_id",_id);
                    edit.putString("phone_number", phone_number);
                    edit.putString("nickname", nickname);
                    edit.putString("password", password);
                    edit.commit();
                    loginHandler.sendEmptyMessage(ProjectProperties.LOGIN_SUCCESS);
                } else {
                    loginHandler.sendEmptyMessage(ProjectProperties.LOGIN_FAILED);
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "手机号或密码错误", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }
}
