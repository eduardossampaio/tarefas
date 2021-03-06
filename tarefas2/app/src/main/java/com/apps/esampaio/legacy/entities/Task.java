package com.apps.esampaio.legacy.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eduardo on 28/06/2016.
 */

public class Task implements Serializable{
    private int id;
    private String name;
    private List<Subtask> subtasks;




    public Task(int id,String name){
        this.id = id;
        this.name = name;
        subtasks = new ArrayList<>();
    }

    public Task(String name){
        this.id = -1;
        this.name = name;
        subtasks = new ArrayList<>();
    }
    public Task() {

    }

    public void addSubtask(Subtask subtask){
        this.subtasks.add(subtask);
        subtask.setTaskId(this.id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSubtasksNumber(){
        return this.subtasks.size();
    }

    public int getCompleteSubtasksNumber(){
        int completed = 0;
        for (Subtask subtask: subtasks) {
            if(subtask.isComplete())
                completed++;
        }
        return completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        for (Subtask subtask:subtasks) {
            subtask.setTaskId(getId());
        }
    }


    @Override
    public boolean equals(Object o) {
        return o instanceof Task != false && ((Task) o).id == this.id;
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<Subtask> subtasks) {
        this.subtasks = subtasks;
        for (Subtask subtask:subtasks) {
            subtask.setTaskId(getId());
        }
    }

    public void updateSubtask(Subtask newSubtask) {
        int pos=-1;
        for(int i=0;i<subtasks.size();i++){
            if ( subtasks.get(i).getId() == newSubtask.getId()){
                pos = i;
                break;
            }
        }
        if(pos >= 0){
            subtasks.remove(pos);
            subtasks.add(pos,newSubtask);
        }
    }
}
