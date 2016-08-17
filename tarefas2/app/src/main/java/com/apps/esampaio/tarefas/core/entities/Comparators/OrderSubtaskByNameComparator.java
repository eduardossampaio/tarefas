package com.apps.esampaio.tarefas.core.entities.comparators;

import com.apps.esampaio.tarefas.core.entities.Subtask;

import java.io.Serializable;
import java.util.Comparator;



public class OrderSubtaskByNameComparator implements Comparator<Subtask>,Serializable {
    @Override
    public int compare(Subtask lhs, Subtask rhs) {
        return lhs.getName().compareTo(rhs.getName());
    }
}
