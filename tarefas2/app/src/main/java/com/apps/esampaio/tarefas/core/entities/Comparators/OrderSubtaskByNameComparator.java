package com.apps.esampaio.tarefas.core.entities.Comparators;

import com.apps.esampaio.tarefas.core.entities.Subtask;

import java.util.Comparator;



public class OrderSubtaskByNameComparator implements Comparator<Subtask> {
    @Override
    public int compare(Subtask lhs, Subtask rhs) {
        return lhs.getName().compareTo(rhs.getName());
    }
}
