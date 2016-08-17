package com.apps.esampaio.tarefas.core.entities.Comparators;

import com.apps.esampaio.tarefas.core.entities.Task;

import java.io.Serializable;
import java.util.Comparator;

public class OrderTaskByNameComparator implements Comparator<Task>,Serializable {
    @Override
    public int compare(Task lhs, Task rhs) {
        return lhs.getName().compareTo(rhs.getName());
    }

}
