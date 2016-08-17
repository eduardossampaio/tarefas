package com.apps.esampaio.tarefas.core.entities.Comparators;

import com.apps.esampaio.tarefas.core.entities.Subtask;
import com.apps.esampaio.tarefas.core.entities.Task;

import java.util.Comparator;

/**
 * Created by eduardo on 11/08/2016.
 */

public class OrderSubtaskByNameComparator implements Comparator<Subtask> {
    @Override
    public int compare(Subtask lhs, Subtask rhs) {
        return lhs.getName().compareTo(rhs.getName());
    }
}
