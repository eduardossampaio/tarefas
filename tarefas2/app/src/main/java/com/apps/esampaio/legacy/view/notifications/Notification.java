package com.apps.esampaio.legacy.view.notifications;

import android.app.NotificationManager;
import android.content.Context;
import androidx.core.app.NotificationCompat;

import com.apps.esampaio.R;
import com.apps.esampaio.legacy.core.Settings;

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
        Settings settings = Settings.getInstance(context);
        notificationBuilder = new NotificationCompat.Builder(context);
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationBuilder.setSmallIcon(R.mipmap.launcher_icon);
        if(settings.vibrate())
            notificationBuilder.setVibrate(new long[]{1000,400,1000});
    }

    public void show(){
        mNotificationManager.notify(id, notificationBuilder.build());
    }

}
