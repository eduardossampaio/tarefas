package com.apps.esampaio.legacy.receives;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.apps.esampaio.legacy.actions.NotificationScheduler;


public class StartupReceive extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("TAREFAS","StartupReceive");
        new NotificationScheduler(context).schedule();
    }
}
