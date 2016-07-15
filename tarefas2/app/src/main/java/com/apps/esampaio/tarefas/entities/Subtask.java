package com.apps.esampaio.tarefas.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by eduardo on 28/06/2016.
 */

public class Subtask implements Serializable{

    private int id;
    private int taskId;
    private String name;
    private String description;
    private boolean complete;

    private Date date;

    public Subtask(String name){
        this.name = name;
        this.complete = false;
    }

    public Subtask(){

    }
    public Subtask(String name,boolean completed){
        this.name = name;
        this.complete = completed;
    }
    public Subtask(String name,String description){
        this.name = name;
        this.description = description;
    }

    public Subtask(int id,String name,String description,boolean completed){
        this.id = id;
        this.name = name;
        this.complete = completed;
        this.description = description;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

}
