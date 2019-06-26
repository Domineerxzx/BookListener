package com.domineer.triplebro.booklistener.broadcast;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.domineer.triplebro.booklistener.views.TwoButtonDialog;

public class NetChangeReceiver extends BroadcastReceiver {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(final Context context, Intent intent) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                //有网处理
            } else {
                //无网显示个提示什么的
                TwoButtonDialog twoButtonDialog = new TwoButtonDialog();
                twoButtonDialog.show("断网警告", "断网了，请检查网络", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((Activity)context).finish();
                        dialog.dismiss();
                    }
                },((Activity)context).getFragmentManager());
            }
        } catch (Exception e) {
            //ignore
        }
    }
}
