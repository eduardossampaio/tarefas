package com.apps.esampaio.tarefas.core.entities.persistence.DAO.Impl.entities;

import android.content.ContentValues;

import com.apps.esampaio.tarefas.core.entities.Task;

/**
 * Created by eduardo on 01/07/2016.
 */

public class TaskEntity implements Entity {

    private Task object;

    private static final String createTableScript =
            "create table task\n" +
                    "(\n" +
                    "\tid integer not null primary key autoincrement,\n" +
                    "\tname varchar(100) not null\n" +
                    ");";

    public TaskEntity(Task object){
        this.object = object;
    }

    public TaskEntity(){

    }

    @Override
    public String getCreateTableSQL() {
        return createTableScript;
    }

    @Override
    public String getTableName() {
        return "task";
    }

    @Override
    public ContentValues getContentValues() {
        if(object==null)
            return null;

        ContentValues contentValues = new ContentValues();
        contentValues.put("name",object.getName());

        return contentValues;
    }

    public String getIdColumnName(){
        return "id";
    }
    public int getId(){
        return object != null ? object.getId() : -1;
    }

    @Override
    public String[] getUpdateSQLs(int dabataseVersion) {
        return null;
    }

}
