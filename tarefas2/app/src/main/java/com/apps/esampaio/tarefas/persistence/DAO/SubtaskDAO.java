package com.apps.esampaio.tarefas.persistence.DAO;

import com.apps.esampaio.tarefas.entities.Subtask;

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

}
