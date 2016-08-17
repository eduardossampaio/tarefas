package com.apps.esampaio.tarefas.view.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.apps.esampaio.tarefas.R;
import com.apps.esampaio.tarefas.Tasks;
import com.apps.esampaio.tarefas.entities.Subtask;
import com.apps.esampaio.tarefas.entities.Task;
import com.apps.esampaio.tarefas.view.activity.ListSubtasksActivity;
import com.apps.esampaio.tarefas.view.activity.ListTasksActivity;

import java.util.List;

/**
 * Created by eduardo on 05/08/16.
 */
public class TasksTodayNotification extends Notification {
    private Task task;
    private List<Subtask> subtasks;

    public TasksTodayNotification(Context context, Task task,List<Subtask> subtasks){
        super(context);
        this.task = task;
        this.subtasks = subtasks;
        create();
    }

    public void create(){

        id = task.getId();

        notificationBuilder.setContentTitle(context.getString(R.string.app_name));
        notificationBuilder.setContentText(context.getString(R.string.notification_tasks_title));

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        inboxStyle.setBigContentTitle(context.getString(R.string.notification_tasks_list_title));

        for (Subtask subtask:subtasks) {
            inboxStyle.addLine(subtask.getName());
        }
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setContentIntent(createIntent());
        notificationBuilder.setStyle(inboxStyle);

    }

    private PendingIntent createIntent(){
        Intent resultIntent = new Intent(context, ListSubtasksActivity.class);
        resultIntent.putExtra("item",task.getId());
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(ListTasksActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        return resultPendingIntent;

    }
}