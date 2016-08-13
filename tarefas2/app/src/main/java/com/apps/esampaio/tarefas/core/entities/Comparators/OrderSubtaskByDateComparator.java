package com.apps.esampaio.tarefas.core.entities.Comparators;

import com.apps.esampaio.tarefas.core.entities.Subtask;

import java.io.Serializable;
import java.util.Comparator;


public class OrderSubtaskByDateComparator implements Comparator<Subtask>,Serializable {
    @Override
    public int compare(Subtask lhs, Subtask rhs) {
        if(lhs.getTaskDate()==null)
            return 1;
        else if(rhs.getTaskDate()==null)
            return -1;
        return lhs.getTaskDate().compareTo(rhs.getTaskDate());
    }
}
