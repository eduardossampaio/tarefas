package com.apps.esampaio.tarefas.core.entities.persistence.DAO;

import com.apps.esampaio.tarefas.core.entities.Task;

import java.util.Date;
import java.util.List;



public interface TaskDAO {
    void addTask(Task task);

    void updateTask(Task task);

    void deleteTask(Task task);

    List<Task> getTasks();

    Task getTask(int id);

    Task getTask(String name);

    List<Task> getTasksByDate(Date date);

    List<Task> getTasksByDate(Date date,boolean completed);

    List<Task> getTasksByTime(Date time);

    List<Task> getTasksByCompleted(boolean completed);

    Transaction<Task> beginTransaction();
}
