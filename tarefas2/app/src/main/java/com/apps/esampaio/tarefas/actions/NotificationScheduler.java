package com.apps.esampaio.tarefas.actions;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.apps.esampaio.tarefas.Constants;

import java.util.Calendar;

/**
 * Created by eduardo on 05/08/16.
 */
public class NotificationScheduler implements Runnable {
    private Context context;
    private Intent notificationIntent = new Intent(Constants.NOTIFICATION_RECEIVER_TAG);
    public NotificationScheduler(Context context){
        this.context = context;
    }

    public void schedule(){
        Log.d("TAREFAS","Scheduling notifications");
//        new Thread(this).start();
        run();
    }

    @Override
    public void run() {
                if(alarmUp())
                    return;
        //deixar de 1 em 1 minuto porque depois vai ter que tratar as horas
        scheduleNotifications_1_minute();
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


        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,notificationIntent,0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

        alarmManager.setRepeating(AlarmManager.RTC, startTime, interval, pendingIntent);
    }

    private boolean alarmUp(){
        boolean alarmUp = (PendingIntent.getBroadcast(context, 0,
                notificationIntent,
                PendingIntent.FLAG_NO_CREATE) != null);
        return alarmUp;
    }

    private void scheduleNotifications_1_minute(){
        Log.d("TAREFAS","scheduling notifications to every minute");
        long startTime = 0;
        long interval = 60 * 1000;

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,notificationIntent,0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startTime, interval, pendingIntent);
    }
//
    private void scheduleNotifications_1_hour(){

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.add(Calendar.HOUR_OF_DAY,1);
        long today = System.currentTimeMillis();
        long tomorow = calendar.getTimeInMillis();
        long startTime = tomorow - today;
        long interval = AlarmManager.INTERVAL_HOUR;


        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,notificationIntent,0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

        alarmManager.setRepeating(AlarmManager.RTC, startTime, interval, pendingIntent);
        Toast.makeText(context,"Alarme configurado para daqui a "+startTime+"millis",Toast.LENGTH_LONG).show();
    }
}
