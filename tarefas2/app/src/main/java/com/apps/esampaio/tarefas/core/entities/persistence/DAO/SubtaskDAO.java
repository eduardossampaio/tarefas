package com.apps.esampaio.tarefas.core.entities.persistence.DAO;

import com.apps.esampaio.tarefas.core.entities.Subtask;

import java.util.Date;
import java.util.List;

/**
 * Created by eduardo on 28/06/2016.
 */

public interface SubtaskDAO {
    public void addSubtask(Subtask task);

    public void addOrUpdate(Subtask task);

    public boolean exists(Subtask subtask);

    public void updateSubtask(Subtask task);

    public void deleteSubtask(Subtask task);

    public List<Subtask> getSubTasks(int task_id);

    public List<Subtask> getSubTasksByDate(int task_id, Date date);

    public List<Subtask> getSubTasksByDate(int task_id, Date date, boolean complete);

    public List<Subtask> getSubTasksByTime(int task_id, Date date, boolean complete);

}
