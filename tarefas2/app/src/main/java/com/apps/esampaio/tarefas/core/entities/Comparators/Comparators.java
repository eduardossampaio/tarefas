package com.apps.esampaio.tarefas.core.entities.comparators;

import com.apps.esampaio.tarefas.core.entities.Subtask;
import com.apps.esampaio.tarefas.core.entities.Task;

import java.util.Comparator;

/**
 * Created by eduardo on 11/08/2016.
 */

public class Comparators{
    //task comparators
    public static final Comparator<Task> orderTaskByName  = new OrderTaskByNameComparator();
    public static final Comparator<Task> orderTaskByCompleted = new OrderTaskByCompletedComparator();

    //subtask comparators
    public static final Comparator<Subtask> orderSubtaskByName = new OrderSubtaskByNameComparator();
    public static final Comparator<Subtask> orderSubtaskByDate = new OrderSubtaskByDateComparator();
    public static final Comparator<Subtask> orderSubtaskByCompleted = new OrderSubtaskByCompletedComparator();


}
