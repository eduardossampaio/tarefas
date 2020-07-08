package com.apps.esampaio.legacy.core.entities;

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
    private DateTime dateTime;

    public Subtask(String name){
        this.name = name;
        this.complete = false;
    }

    public Subtask(){
        this.name = "";
        this.complete = false;
        this.dateTime=new DateTime();
    }
    public Subtask(String name,boolean completed){
        this.name = name;
        this.complete = completed;
        this.dateTime=new DateTime();
    }
    public Subtask(String name,String description){
        this.name = name;
        this.description = description;
        this.dateTime=new DateTime();
    }

    public Subtask(int id,String name,String description,boolean completed){
        this(name,description);
        this.id = id;
        this.complete = completed;
    }

    public Subtask(int id,String name,String description,boolean completed,Date taskDate,Date taskTime){
        this(id,name,description,completed);
        this.description = description;
        this.dateTime=new DateTime(taskDate,taskTime);
    }

    public Date getTaskDate() {
        return dateTime.getDate();
    }

    public void setTaskDate(Date taskDate) {
        this.dateTime.setDate(taskDate);
    }

    public Date getTaskTime() {
        return dateTime.getTime();
    }

    public void setTaskTime(Date taskTime) {
        this.dateTime.setTime(taskTime);
    }

    public DateTime getDateTime() {
        return dateTime;
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
