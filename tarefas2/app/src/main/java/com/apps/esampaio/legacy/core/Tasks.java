package com.apps.esampaio.legacy.core;

import android.content.Context;

import com.apps.esampaio.legacy.core.entities.comparators.Comparators;
import com.apps.esampaio.legacy.core.entities.Subtask;
import com.apps.esampaio.legacy.core.entities.Task;
import com.apps.esampaio.legacy.core.entities.persistence.DAO.Impl.SubtaskDAOImpl;
import com.apps.esampaio.legacy.core.entities.persistence.DAO.Impl.TaskDAOImpl;
import com.apps.esampaio.legacy.core.entities.persistence.DAO.SubtaskDAO;
import com.apps.esampaio.legacy.core.entities.persistence.DAO.TaskDAO;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Tasks {
    private static TaskDAO taskDAO = null;
    private static SubtaskDAO subtaskDAO = null;
    private Settings settings;
    public Tasks(Context context){
        if(taskDAO == null)
            taskDAO= new TaskDAOImpl(context);
        if(subtaskDAO == null)
            subtaskDAO = new SubtaskDAOImpl(context);
        settings = Settings.getInstance(context);
    }

    private List<Task> order(List<Task> tasks) {
        Settings.Order order = settings.order();
        Comparator<Task> taskComparator = null;
        Comparator<Subtask> subtaskComparator = null;
        if(order == Settings.Order.NO_ORDER){
            return tasks;
        }else if(order == Settings.Order.Name){
            taskComparator = Comparators.orderTaskByName;
            subtaskComparator=Comparators.orderSubtaskByName;
        }else if(order == Settings.Order.COMPLETED){
            taskComparator = Comparators.orderTaskByCompleted;
            subtaskComparator=Comparators.orderSubtaskByCompleted;
        //TODO ordenar tasks por data
        }else if(order == Settings.Order.Date){
            taskComparator = Comparators.orderTaskByName;
            subtaskComparator=Comparators.orderSubtaskByDate;
        }
        Collections.sort(tasks,taskComparator);
        for (Task task:tasks) {
            task.setComparator(subtaskComparator);
            task.sortSubtasks();
        }
        return tasks;
    }

    public List<Task> getTasks(){
        List<Task> tasks = taskDAO.getTasks();
        return order(tasks);
    }
    //TODO arrumar isso em uma sql
    public List<Task> getCompletedTasks(){
        List<Task> tasks = getTasks();
        List<Task> uncompletedTasks = getTasksByCompleted(false);
        tasks.removeAll(uncompletedTasks);
        return order(tasks);
    }

    public boolean exists(Task task){
        return taskDAO.getTask(task.getName()) != null;
    }

    private List<Task> getTasksByCompleted(boolean completed) {
        List<Task> tasks = taskDAO.getTasksByCompleted(completed);
        return order(tasks);
    }
    public List<Task> getTasksByDate(Date date){
        List<Task> tasks = taskDAO.getTasksByDate(date);
        return order(tasks);
    }
    public List<Task> getTasksByDate(Date date,boolean completed){
        List<Task> tasks = taskDAO.getTasksByDate(date,completed);
        return order(tasks);
    }

    public List<Task> getTasksByTime(Date time,boolean completed){
        List<Task> tasks = taskDAO.getTasksByTime(time);
        return order(tasks);
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
