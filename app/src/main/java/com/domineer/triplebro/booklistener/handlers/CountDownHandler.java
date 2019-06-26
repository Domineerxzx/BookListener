package com.domineer.triplebro.booklistener.handlers;

import android.content.Context;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;

import com.domineer.triplebro.booklistener.R;
import com.domineer.triplebro.booklistener.managers.RegisterManager;
import com.domineer.triplebro.booklistener.properties.ProjectProperties;

public class CountDownHandler extends Handler {

    private Context context;
    private Button bt_get_code;
    private ServiceConnection serviceConnection;

    public CountDownHandler(Context context, Button bt_get_code) {
        this.context = context;
        this.bt_get_code = bt_get_code;
    }

    public ServiceConnection getServiceConnection() {
        return serviceConnection;
    }

    public void setServiceConnection(ServiceConnection serviceConnection) {
        this.serviceConnection = serviceConnection;
    }

    @Override
    public void handleMessage(Message msg) {
        if(msg.what == ProjectProperties.GET_REQUEST_CODE_FAILED && (int)msg.obj != 60){
            bt_get_code.setBackgroundResource(R.drawable.shape_alpha_card);
            bt_get_code.setText("已获取验证码（"+(60-(int)msg.obj)+"s)");
        }
        if (msg.what == ProjectProperties.GET_REQUEST_CODE_FAILED && (int)msg.obj == 60){
            bt_get_code.setBackgroundResource(R.drawable.shape_button);
            bt_get_code.setText("重新获取验证码");
            if(serviceConnection != null){
                context.unbindService(serviceConnection);
            }
        }
    }
}
