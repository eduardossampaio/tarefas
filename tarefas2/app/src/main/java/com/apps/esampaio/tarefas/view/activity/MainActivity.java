package com.apps.esampaio.tarefas.view.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.apps.esampaio.tarefas.R;
import com.apps.esampaio.tarefas.Tasks;
import com.apps.esampaio.tarefas.actions.NotificationScheduler;
import com.apps.esampaio.tarefas.entities.DateTime;
import com.apps.esampaio.tarefas.entities.Task;
import com.apps.esampaio.tarefas.view.notifications.TasksTodayNotification;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Tasks tasks = new Tasks(this);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        calendar.set(Calendar.YEAR,2016);
        calendar.set(Calendar.MONTH,Calendar.AUGUST);
        calendar.set(Calendar.DAY_OF_MONTH,7);
        calendar.set(Calendar.HOUR_OF_DAY,9);
        calendar.set(Calendar.MINUTE,10);
        calendar.set(Calendar.SECOND,0);
        Date date = calendar.getTime();
        String dateS= DateFormat.getDateInstance().format(date);
        List<Task> notifyTasks = tasks.getTasksByTime(date,false);
        for(Task task:notifyTasks){
            TasksTodayNotification tasksTodayNotification = new TasksTodayNotification(this, task, task.getSubtasks());
            tasksTodayNotification.show();
        }

        new NotificationScheduler(this).schedule();

        Intent intent = new Intent(MainActivity.this,ListTasksActivity.class);
        startActivity(intent);
    }




}
