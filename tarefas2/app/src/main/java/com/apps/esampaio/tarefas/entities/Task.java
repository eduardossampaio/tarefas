package com.apps.esampaio.tarefas.entities;

import com.apps.esampaio.tarefas.persistence.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eduardo on 28/06/2016.
 */

public class Task implements Entity ,Serializable{
    private int id;
    private String name;
    private List<Subtask> subtasks;

    private static final String createTableScript =
            "create table task\n" +
               "(\n" +
                "\tid integer not null primary key autoincrement,\n" +
                "\tname varchar(100) not null\n" +
                ");";


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
        if ( o instanceof Task == false)
            return false;
        return ((Task) o).id == this.id;
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


    @Override
    public String getCreateTable() {
        return createTableScript;
    }

    @Override
    public String getTableName() {
        return "task";
    }

    public void updateTask(Subtask newSubtask) {
        int pos=-1;
        for(int i=0;i<subtasks.size();i++){
            if ( subtasks.get(i).getId() == newSubtask.getId()){
                pos = i;
            }
        }
        if(pos >= 0){
            subtasks.remove(pos);
            subtasks.add(pos,newSubtask);
        }
    }
}
