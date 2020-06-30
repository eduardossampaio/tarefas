package com.apps.esampaio.legacy.core.entities.comparators;

import com.apps.esampaio.legacy.core.entities.Task;

import java.io.Serializable;
import java.util.Comparator;


public class OrderTaskByCompletedComparator implements Comparator<Task>,Serializable {
    @Override
    public int compare(Task lhs, Task rhs) {
        return rhs.getCompleteSubtasksNumber() -lhs.getCompleteSubtasksNumber();
    }
    
}
