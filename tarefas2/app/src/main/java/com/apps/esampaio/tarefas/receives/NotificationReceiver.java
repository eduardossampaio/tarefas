package com.apps.esampaio.tarefas.receives;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.apps.esampaio.tarefas.R;
import com.apps.esampaio.tarefas.Tasks;
import com.apps.esampaio.tarefas.entities.DateTime;
import com.apps.esampaio.tarefas.entities.Subtask;
import com.apps.esampaio.tarefas.entities.Task;
import com.apps.esampaio.tarefas.view.Notifications;

import java.util.List;

/**
 * Created by eduardo on 03/08/2016.
 */

public class NotificationReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Tasks tasks = new Tasks(context);
        String tasksToday = "";
        List<Task> myTasks = tasks.getTasks();
        for (Task task : myTasks) {
            for(Subtask subtask : task.getSubtasks() ){
                if(isToNotificate(subtask)){
                    //Toast.makeText(context,subtask.getName(),Toast.LENGTH_SHORT).show();
                    tasksToday+=subtask.getName();
                    tasksToday+="\n";
                    Notifications.showSimpleNotification(context, subtask.getId(), context.getString(R.string.app_name),subtask.getName());
                }
            }
        }
        if(!tasksToday.isEmpty()) {
           // Notifications.showSimpleNotification(context, 12345, "\"Tarefa agendada para hoje\"", tasksToday);
        }
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
