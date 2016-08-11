package com.apps.esampaio.tarefas.core.entities.Comparators;

import com.apps.esampaio.tarefas.core.entities.Task;

import java.util.Comparator;

/**
 * Created by eduardo on 11/08/2016.
 */

public class OrderTaskByCompletedComparator implements Comparator<Task> {
    @Override
    public int compare(Task lhs, Task rhs) {
        return rhs.getCompleteSubtasksNumber() -lhs.getCompleteSubtasksNumber();
    }
    
}
