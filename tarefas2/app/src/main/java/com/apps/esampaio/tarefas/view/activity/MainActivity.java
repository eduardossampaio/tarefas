package com.apps.esampaio.tarefas.view.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.apps.esampaio.tarefas.R;
import com.apps.esampaio.tarefas.entities.DateTime;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new Thread(new Runnable() {
            @Override
            public void run() {
                scheduleNotifications();
            }
        }).start();

        Intent intent = new Intent(MainActivity.this,ListTasksActivity.class);
        startActivity(intent);
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
//        long interval = 60 * 1000; //um minuto

        Intent notificationIntent = new Intent("EXECUTE_NOTIFICATION_TASK");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,notificationIntent,0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startTime, interval, pendingIntent);
    }


}
