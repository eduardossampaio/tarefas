package com.apps.esampaio.tarefas.entities;

import com.apps.esampaio.tarefas.persistence.Entity;

import java.io.Serializable;

/**
 * Created by eduardo on 28/06/2016.
 */

public class Subtask implements Entity,Serializable{

    private int id;
    private int taskId;
    private String name;
    private String description;
    private boolean complete;

    private static final String createTableSql=
            " create table subtask (" +
            " id integer not null primary key autoincrement," +
            " task_id integer not null ," +
            " name varchar(100) not null," +
            " description varchar(1000)," +
            " completed int, "+
            " FOREIGN KEY(task_id) REFERENCES task(id)" +
            ");";


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

    @Override
    public String getTableName() {
        return "subtask";
    }

    @Override
    public String getCreateTable() {
        return createTableSql;
    }
}
