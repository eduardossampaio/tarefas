package com.apps.esampaio.legacy.view.notifications;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import com.apps.esampaio.legacy.R;
import com.apps.esampaio.legacy.core.entities.Subtask;
import com.apps.esampaio.legacy.core.entities.Task;
import com.apps.esampaio.legacy.view.activity.ListSubtasksActivity;
import com.apps.esampaio.legacy.view.activity.ListTasksActivity;

import java.util.List;


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

        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setContentIntent(createIntent());
        if(subtasks.isEmpty()){
            return;
        }else if(subtasks.size()==1){
            notificationBuilder.setContentText(subtasks.get(0).getName());
        } else {
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            inboxStyle.setBigContentTitle(context.getString(R.string.notification_tasks_list_title));

            for (Subtask subtask : subtasks) {
                inboxStyle.addLine(subtask.getName());
            }

            notificationBuilder.setStyle(inboxStyle);
        }

    }

    private PendingIntent createIntent(){
        Intent resultIntent = new Intent(context, ListSubtasksActivity.class);
        resultIntent.putExtra("item",task);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(ListTasksActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
