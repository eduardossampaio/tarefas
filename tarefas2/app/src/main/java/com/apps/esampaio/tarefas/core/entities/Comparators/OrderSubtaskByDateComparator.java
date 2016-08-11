package com.apps.esampaio.tarefas.core.entities.Comparators;

import com.apps.esampaio.tarefas.core.entities.Subtask;

import java.util.Comparator;

/**
 * Created by eduardo on 11/08/2016.
 */

public class OrderSubtaskByDateComparator implements Comparator<Subtask> {
    @Override
    public int compare(Subtask lhs, Subtask rhs) {
        if(lhs.getTaskDate()==null)
            return 1;
        else if(rhs.getTaskDate()==null)
            return -1;
        return lhs.getTaskDate().compareTo(rhs.getTaskDate());
    }
}
