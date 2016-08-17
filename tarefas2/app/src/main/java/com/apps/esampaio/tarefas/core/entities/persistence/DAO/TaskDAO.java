package com.apps.esampaio.tarefas.core.entities.persistence.DAO;

import com.apps.esampaio.tarefas.core.entities.Task;

import java.util.Date;
import java.util.List;



public interface TaskDAO {
    void addTask(Task task);

    void updateTask(Task task);

    void deleteTask(Task task);

    List<Task> getTasks();

    List<Task> getTasksByDate(Date date);

    List<Task> getTasksByTime(Date time);

    Task getTask(int id);

    List<Task> getTasksByCompleted(boolean completed);
}
