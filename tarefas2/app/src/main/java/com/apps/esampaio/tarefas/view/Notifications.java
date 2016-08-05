package com.apps.esampaio.tarefas.view;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.apps.esampaio.tarefas.R;

/**
 * Created by eduardo on 03/08/2016.
 */

public class Notifications {

    public static void showSimpleNotification(Context context,int id,String title,String message){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.launcher_icon);
        builder.setContentTitle(title);
        builder.setContentText(message);
//        builder.setColor(R.color)

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(id, builder.build());
    }
}
