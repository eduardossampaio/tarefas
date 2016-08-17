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

public class NotificationReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        if ( !isInTime() )
            return;
        Tasks tasks = new Tasks(context);
        List<Task> myTasks = tasks.getTasks();
        for (Task task : myTasks) {
            List<Subtask> tasksToday =new ArrayList<>();
            for(Subtask subtask : task.getSubtasks() ){
                if(isToNotificate(subtask)){
                    tasksToday.add(subtask);

                }
            }
            if(!tasksToday.isEmpty()) {
                Notification notification = new TasksTodayNotification(context,task,tasksToday);
                notification.show();
            }
        }

    }
    private boolean isInTime(){
        Calendar calendar=Calendar.getInstance();
        return calendar.get(Calendar.MINUTE) == 0 && calendar.get(Calendar.HOUR_OF_DAY)==0;
    }

    private boolean isToNotificate(Subtask subtask){
        if(subtask==null)
            return false;
        if(subtask.isComplete())
            return false;
        if(subtask.getTaskDate()==null)
            return false;
        //TODO arrumar isso
        DateTime nowDate = DateTime.getCurrentDateTime();
        DateTime subtaskDate = new DateTime(subtask.getTaskDate(),null);
        if ( nowDate.getDay() == subtaskDate.getDay() && nowDate.getMonth()== subtaskDate.getMonth() && nowDate.getYear() == subtaskDate.getYear()){
            return true;
        }
        return false;
    }
}
