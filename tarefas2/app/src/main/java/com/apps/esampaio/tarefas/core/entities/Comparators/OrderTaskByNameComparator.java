package com.apps.esampaio.tarefas.core.entities.Comparators;

import com.apps.esampaio.tarefas.core.entities.Task;

import java.util.Comparator;

/**
 * Created by eduardo on 11/08/2016.
 */

public class OrderTaskByNameComparator implements Comparator<Task> {
    @Override
    public int compare(Task lhs, Task rhs) {
        return lhs.getName().compareTo(rhs.getName());
    }

}
