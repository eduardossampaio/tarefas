package com.apps.esampaio.legacy;

import android.content.Context;

import com.apps.esampaio.legacy.entities.Subtask;
import com.apps.esampaio.legacy.entities.Task;
import com.apps.esampaio.legacy.persistence.DAO.Impl.SubtaskDAOImpl;
import com.apps.esampaio.legacy.persistence.DAO.Impl.TaskDAOImpl;
import com.apps.esampaio.legacy.persistence.DAO.SubtaskDAO;
import com.apps.esampaio.legacy.persistence.DAO.TaskDAO;

import java.util.List;

/**
 * Created by eduardo on 28/06/2016.
 */

public class Tasks {
    private static TaskDAO taskDAO = null;
    private static SubtaskDAO subtaskDAO = null;

    public Tasks(Context context){
        if(taskDAO == null)
            taskDAO= new TaskDAOImpl(context);
        if(subtaskDAO == null)
            subtaskDAO = new SubtaskDAOImpl(context);

    }

    public List<Task> getTasks(){
        List<Task> tasks = taskDAO.getTasks();
        for (Task task: tasks) {
            task.setSubtasks(subtaskDAO.getSubTasks(task.getId()));
        }
        return tasks;
    }

    public void addTask(Task task){
        taskDAO.addTask(task);
        for (Subtask subtask: task.getSubtasks()) {
            subtaskDAO.addSubtask(subtask);
        }
    }



    public void updateTask(Task item) {
        taskDAO.updateTask(item);
        for (Subtask subtask: item.getSubtasks()) {
            subtaskDAO.addOrUpdate(subtask);
        }
    }

    public void delete(Task item) {
        taskDAO.deleteTask(item);
        for (Subtask subtask: item.getSubtasks()) {
            deleteSubtask(subtask);
        }
    }

    public void deleteSubtask(Subtask subtask){
        subtaskDAO.deleteSubtask(subtask);
    }

    public Task getTask(int id) {
        Task task = taskDAO.getTask(id);
        task.setSubtasks(subtaskDAO.getSubTasks(task.getId()));
        return task;
    }
}
