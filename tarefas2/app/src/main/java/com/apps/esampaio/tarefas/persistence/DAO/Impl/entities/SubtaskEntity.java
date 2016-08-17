package com.apps.esampaio.tarefas.persistence.DAO.Impl.entities;

import android.content.ContentValues;

import com.apps.esampaio.tarefas.entities.Subtask;
import com.apps.esampaio.tarefas.persistence.DAO.Impl.entities.Entity;

/**
 * Created by eduardo on 01/07/2016.
 */

public class SubtaskEntity implements Entity {

    private static final String createTableSql =
            " create table subtask (" +
                    " id integer not null primary key autoincrement," +
                    " task_id integer not null ," +
                    " name varchar(100) not null," +
                    " task_date date," +
                    " task_time date," +
                    " description varchar(1000)," +
                    " completed int, " +
                    " FOREIGN KEY(task_id) REFERENCES task(id)" +
                    ");";

    private static final String tableName = "subtask";
    private Subtask object;

    public SubtaskEntity(Subtask object) {
        this.object = object;
    }

    public SubtaskEntity() {
    }

    @Override
    public String getCreateTableSQL() {
        return createTableSql;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    public String getIdColumnName() {
        return "id";
    }

    public int getId() {
        return object != null ? object.getId() : -1;
    }

    @Override
    public ContentValues getContentValues() {
        if (object == null)
            return null;
        ContentValues contentValues = new ContentValues();

        contentValues.put("task_id", object.getTaskId());
        contentValues.put("name", object.getName());
        contentValues.put("description", object.getDescription());
        contentValues.put("completed", object.isComplete() ? 1 : 0);
        contentValues.put("task_date", object.getTaskDate() != null ? object.getTaskDate().getTime() : 0);
        contentValues.put("task_time", object.getTaskTime() != null ? object.getTaskTime().getTime() : 0);

        return contentValues;
    }

    @Override
    public String[] getUpdateSQLs(int databaseVersion) {
        if (databaseVersion == 2) {
            return new String[]{
                    "ALTER TABLE " + tableName + " ADD task_date date",
                    "ALTER TABLE " + tableName + " ADD task_time date"
            };
        }
        return null;
    }
}
