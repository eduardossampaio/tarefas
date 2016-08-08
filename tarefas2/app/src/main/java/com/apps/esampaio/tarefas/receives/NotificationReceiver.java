package com.apps.esampaio.tarefas.receives;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.apps.esampaio.tarefas.R;
import com.apps.esampaio.tarefas.Tasks;
import com.apps.esampaio.tarefas.entities.DateTime;
import com.apps.esampaio.tarefas.entities.Subtask;
import com.apps.esampaio.tarefas.entities.Task;
import com.apps.esampaio.tarefas.view.notifications.Notification;
import com.apps.esampaio.tarefas.view.notifications.TasksTodayNotification;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by eduardo on 03/08/2016.
 */

public class NotificationReceiver extends BroadcastReceiver {
    private Tasks tasks;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (tasks == null)
            tasks = new Tasks(context);

        List<Task> myTasks = getTasksToNotificate();
        for (Task task : myTasks) {
            Notification notification = new TasksTodayNotification(context, task, task.getSubtasks());
            notification.show();
        }

    }

    private List<Task> getTasksToNotificate(){
        DateTime nowDate = DateTime.getCurrentDateTime();
        if(nowDate.getHour()==0  && nowDate.getMinute() ==0){
            return tasks.getTasksByDate(nowDate.getDate(),false);
        }
        nowDate.setTime(nowDate.getHour()+2,nowDate.getMinute());
        return tasks.getTasksByTime(nowDate.getTime(),false);
    }

}
