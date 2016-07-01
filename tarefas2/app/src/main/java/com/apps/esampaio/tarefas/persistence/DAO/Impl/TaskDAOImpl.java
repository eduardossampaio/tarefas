package com.apps.esampaio.tarefas.persistence.DAO.Impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.apps.esampaio.tarefas.entities.Task;
import com.apps.esampaio.tarefas.persistence.DAO.TaskDAO;
import com.apps.esampaio.tarefas.persistence.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eduardo on 28/06/2016.
 */

public class TaskDAOImpl implements TaskDAO {
    private SQLiteOpenHelper databaseHelper;

    public TaskDAOImpl(Context context){
        databaseHelper= new DatabaseHelper(context);
    }

    @Override
    public List<Task> getTasks() {
        String[] columns = {"id","name"};
        List<Task> tasks = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query("task",columns,null,null,null,null,null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            tasks.add(new Task(id,name));
        }
        return tasks;

    }

    public Task getTask(int taskId){
        String[] columns = {"id","name"};
        String[] selectionsArgs = {""+taskId};
        Task  task = null;
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query("task",columns,"id = ?",selectionsArgs,null,null,null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            task = new Task(id,name);
        }
        return task;
    }

    @Override
    public void updateTask(Task task) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",task.getName());
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.update("task",contentValues,"id = ?",new String[]{""+task.getId()});
    }

    @Override
    public void deleteTask(Task task) {
        SQLiteDatabase db = this.databaseHelper.getWritableDatabase();
        String [] deleteArgs = {""+task.getId()};
        db.delete("task","id = ?",deleteArgs);
    }

    @Override
    public void addTask(Task task) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",task.getName());
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int primaryKey = (int)db.insert(task.getTableName(),"id",contentValues);
        task.setId(primaryKey);
    }
}
