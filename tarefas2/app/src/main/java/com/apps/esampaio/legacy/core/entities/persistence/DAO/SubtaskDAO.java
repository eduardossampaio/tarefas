package com.apps.esampaio.legacy.core.entities.persistence.DAO;

import com.apps.esampaio.legacy.core.entities.Subtask;

import java.util.List;


public interface SubtaskDAO {
    void addSubtask(Subtask task);

    void addOrUpdate(Subtask task);

    boolean exists(Subtask subtask);

    void updateSubtask(Subtask task);

    void deleteSubtask(Subtask task);

    List<Subtask> getSubTasks(int task_id);

}
