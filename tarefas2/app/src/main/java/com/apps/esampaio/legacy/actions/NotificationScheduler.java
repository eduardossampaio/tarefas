package com.apps.esampaio.legacy.actions;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.apps.esampaio.legacy.core.Constants;


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
        return(PendingIntent.getBroadcast(context, 0,
                notificationIntent,
                PendingIntent.FLAG_NO_CREATE | PendingIntent.FLAG_IMMUTABLE) != null);
    }

    private void scheduleNotifications(){
        Log.d("TAREFAS","scheduling notifications to every minute");
        long startTime = 0;
        long interval = 60 * 1000; //1 minute

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startTime, interval, pendingIntent);
    }
}
