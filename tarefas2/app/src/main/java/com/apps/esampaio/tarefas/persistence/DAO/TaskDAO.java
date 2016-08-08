package com.apps.esampaio.tarefas.persistence.DAO;

import com.apps.esampaio.tarefas.entities.Task;

import java.util.Date;
import java.util.List;

/**
 * Created by eduardo on 28/06/2016.
 */

public interface TaskDAO {
    public void addTask(Task task);

    public void updateTask(Task task);

    public void deleteTask(Task task);

    public List<Task> getTasks();

    public List<Task> getTasksByDate(Date date);

    public List<Task> getTasksByTime(Date time);

    public Task getTask(int id);

}
