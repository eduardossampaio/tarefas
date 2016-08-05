package com.apps.esampaio.tarefas.actions;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.apps.esampaio.tarefas.Constants;

import java.util.Calendar;

/**
 * Created by eduardo on 05/08/16.
 */
public class NotificationScheduler implements Runnable {
    private Context context;

    public NotificationScheduler(Context context){
        this.context = context;
    }

    public void schedule(){
        Log.d("TAREFAS","Scheduling notifications");
        new Thread(this).start();
    }

    @Override
    public void run() {
        scheduleNotifications_1_hour();
    }

    private void scheduleNotifications(){

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DAY_OF_YEAR,1);
        long today = System.currentTimeMillis();
        long tomorow = calendar.getTimeInMillis();
        long startTime = tomorow - today;
        long interval = AlarmManager.INTERVAL_DAY;

        Intent notificationIntent = new Intent(Constants.NOTIFICATION_RECEIVER_TAG);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,notificationIntent,0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startTime, interval, pendingIntent);
    }

    private void scheduleNotifications_1_minute(){
        Log.d("TAREFAS","scheduling notifications to every minute");
        long startTime = 0;
        long interval = 1000;
        Intent notificationIntent = new Intent(Constants.NOTIFICATION_RECEIVER_TAG);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,notificationIntent,0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startTime, interval, pendingIntent);
    }

    private void scheduleNotifications_1_hour(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.HOUR,1);
        long today = System.currentTimeMillis();
        long tomorow = calendar.getTimeInMillis();
        long startTime = tomorow - today;
        long interval = AlarmManager.INTERVAL_HOUR;

        Intent notificationIntent = new Intent(Constants.NOTIFICATION_RECEIVER_TAG);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,notificationIntent,0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startTime, interval, pendingIntent);
    }
}
