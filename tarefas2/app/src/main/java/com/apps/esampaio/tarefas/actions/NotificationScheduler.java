package com.apps.esampaio.tarefas.actions;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.apps.esampaio.tarefas.core.Constants;

/**
 * Created by eduardo on 05/08/16.
 */
public class NotificationScheduler {
    private Context context;
    private Intent notificationIntent = new Intent(Constants.NOTIFICATION_RECEIVER_TAG);
    public NotificationScheduler(Context context){
        this.context = context;
    }

    public void schedule(){
        Log.d("TAREFAS","Scheduling notifications");
        if(alarmUp())
            return;
        scheduleNotifications();
    }


    private boolean alarmUp(){
        boolean alarmUp = (PendingIntent.getBroadcast(context, 0,
                notificationIntent,
                PendingIntent.FLAG_NO_CREATE) != null);
        return alarmUp;
    }

    private void scheduleNotifications(){
        Log.d("TAREFAS","scheduling notifications to every minute");
        long startTime = 0;
        long interval = 60 * 1000; //1 minute

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,notificationIntent,0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startTime, interval, pendingIntent);
    }
}
