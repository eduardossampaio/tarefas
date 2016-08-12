package com.apps.esampaio.tarefas.core.entities.persistence.DAO;

import com.apps.esampaio.tarefas.core.entities.Subtask;

import java.util.List;


public interface SubtaskDAO {
    void addSubtask(Subtask task);

    void addOrUpdate(Subtask task);

    boolean exists(Subtask subtask);

    void updateSubtask(Subtask task);

    void deleteSubtask(Subtask task);

    List<Subtask> getSubTasks(int task_id);

}
