package com.apps.esampaio.tarefas.view.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.apps.esampaio.tarefas.R;
import com.apps.esampaio.tarefas.entities.Subtask;
import com.apps.esampaio.tarefas.view.activity.ListSubtasksActivity;
import com.apps.esampaio.tarefas.view.activity.ListTasksActivity;

import java.util.List;

/**
 * Created by eduardo on 03/08/2016.
 */

public class Notification {
    protected int id;
    protected Context context;
    protected NotificationCompat.Builder notificationBuilder;
    protected NotificationManager mNotificationManager;
    public Notification(Context context){
        this.context = context;

        notificationBuilder = new NotificationCompat.Builder(context);
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationBuilder.setSmallIcon(R.mipmap.launcher_icon);
    }

    public void show(){
        mNotificationManager.notify(id, notificationBuilder.build());
    }

}
