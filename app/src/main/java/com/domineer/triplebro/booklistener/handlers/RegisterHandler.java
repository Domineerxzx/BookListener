package com.domineer.triplebro.booklistener.handlers;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.Message;

import com.domineer.triplebro.booklistener.activities.RegisterActivity;
import com.domineer.triplebro.booklistener.properties.ProjectProperties;
public class RegisterHandler extends Handler {

    private Context context;
    private ServiceConnection serviceConnection;

    public RegisterHandler(Context context, ServiceConnection serviceConnection) {
        this.context = context;
        this.serviceConnection = serviceConnection;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case ProjectProperties.GET_REQUEST_CODE_SUCCESS:
                context.unbindService(serviceConnection);
                break;
            case ProjectProperties.GET_REQUEST_CODE_FAILED:
                context.unbindService(serviceConnection);
                break;
            case ProjectProperties.REGISTER_SUCCESS:
                context.unbindService(serviceConnection);
                ((RegisterActivity) context).finish();
                break;
            case ProjectProperties.REGISTER_FAILED:
                context.unbindService(serviceConnection);
                break;
        }
    }
}
