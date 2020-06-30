package com.apps.esampaio.legacy.receives;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.apps.esampaio.legacy.core.Settings;
import com.apps.esampaio.legacy.core.Tasks;
import com.apps.esampaio.legacy.core.entities.DateTime;
import com.apps.esampaio.legacy.core.entities.Task;
import com.apps.esampaio.legacy.view.notifications.Notification;
import com.apps.esampaio.legacy.view.notifications.TasksTodayNotification;

import java.util.Calendar;
import java.util.List;

/**
 * Created by eduardo on 03/08/2016.
 */

public class NotificationReceiver extends BroadcastReceiver {
    private Tasks tasks;
    private Settings settings;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (tasks == null)
            tasks = new Tasks(context);
        settings = Settings.getInstance(context);
        List<Task> myTasks = getTasksToNotificate();
        for (Task task : myTasks) {
            Notification notification = new TasksTodayNotification(context, task, task.getSubtasks());
            notification.show();
        }

    }

    private List<Task> getTasksToNotificate(){

        DateTime nowDate = DateTime.getCurrentDateTime();
        if(nowDate.getHour()==0  && nowDate.getMinute() ==0 && settings.notifyAllTasks()){
            return tasks.getTasksByDate(nowDate.getDate(),false);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate.getTime());
        int minutes = settings.notifyBefore();
        calendar.add(Calendar.MINUTE,minutes);
        return tasks.getTasksByTime(calendar.getTime(),false);
    }

}
