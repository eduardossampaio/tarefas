package com.apps.esampaio.tarefas.receives;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.apps.esampaio.tarefas.actions.NotificationScheduler;

/**
 * Created by eduardo on 05/08/16.
 */
public class StartupReceive extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("TAREFAS","StartupReceive");
        new NotificationScheduler(context).schedule();
    }
}