package com.apps.esampaio.tarefas.core.entities.comparators;

import com.apps.esampaio.tarefas.core.entities.Subtask;

import java.io.Serializable;
import java.util.Comparator;



public class OrderSubtaskByCompletedComparator implements Comparator<Subtask>,Serializable{
    @Override
    public int compare(Subtask lhs, Subtask rhs) {
        if ( lhs.isComplete() == rhs.isComplete()){
            return  lhs.getName().compareTo(rhs.getName());
        }else if(lhs.isComplete() && ! rhs.isComplete()){
            return 1;
        }else{
            return -1;
        }
    }
}
