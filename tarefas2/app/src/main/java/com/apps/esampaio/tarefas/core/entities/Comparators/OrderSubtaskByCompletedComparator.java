package com.apps.esampaio.tarefas.core.entities.Comparators;

import com.apps.esampaio.tarefas.core.entities.Subtask;

import java.util.Comparator;



public class OrderSubtaskByCompletedComparator implements Comparator<Subtask>{
    @Override
    public int compare(Subtask lhs, Subtask rhs) {
        if ( lhs.isComplete() == rhs.isComplete()){
            return  0;
        }else if(lhs.isComplete() && ! rhs.isComplete()){
            return 1;
        }else{
            return -1;
        }
    }
}
