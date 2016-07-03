package com.apps.esampaio.tarefas.persistence.DAO.Impl.entities;

import android.content.ContentValues;

import com.apps.esampaio.tarefas.entities.Subtask;
import com.apps.esampaio.tarefas.persistence.DAO.Impl.entities.Entity;

/**
 * Created by eduardo on 01/07/2016.
 */

public class SubtaskEntity implements Entity {

    private static final String createTableSql=
                    " create table subtask (" +
                    " id integer not null primary key autoincrement," +
                    " task_id integer not null ," +
                    " name varchar(100) not null," +
                    " description varchar(1000)," +
                    " completed int, "+
                    " FOREIGN KEY(task_id) REFERENCES task(id)" +
                    ");";

    private Subtask object;

    public SubtaskEntity(Subtask object){
        this.object = object;
    }

    public SubtaskEntity(){
    }

    @Override
    public String getCreateTableSQL() {
        return createTableSql;
    }

    @Override
    public String getTableName() {
        return "subtask";
    }

    public String getIdColumnName(){
        return "id";
    }
    public int getId(){
        return object != null ? object.getId() : -1;
    }

    @Override
    public ContentValues getContentValues() {
        if(object==null)
            return null;
        ContentValues contentValues = new ContentValues();

        contentValues.put("task_id",object.getTaskId());
        contentValues.put("name",object.getName());
        contentValues.put("description",object.getDescription());
        contentValues.put("completed",object.isComplete() ? 1 : 0);

        return contentValues;
    }

    @Override
    public String getUpdateSQL(int dabataseVersion) {
        return null;
    }
}
