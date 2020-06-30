package com.apps.esampaio.legacy.core.entities.comparators;

import com.apps.esampaio.legacy.core.entities.Task;

import java.io.Serializable;
import java.util.Comparator;

public class OrderTaskByNameComparator implements Comparator<Task>,Serializable {
    @Override
    public int compare(Task lhs, Task rhs) {
        return lhs.getName().compareTo(rhs.getName());
    }

}
