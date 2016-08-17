package com.apps.esampaio.tarefas.persistence.DAO.Impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.apps.esampaio.tarefas.entities.Subtask;
import com.apps.esampaio.tarefas.persistence.DAO.SubtaskDAO;
import com.apps.esampaio.tarefas.persistence.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eduardo on 28/06/2016.
 */

public class SubtaskDAOImpl implements SubtaskDAO {
    private DatabaseHelper databaseHelper;
    public SubtaskDAOImpl(Context context){
        databaseHelper = new DatabaseHelper(context);
    }
    @Override
    public void addSubtask(Subtask subtask) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("task_id",subtask.getTaskId());
        contentValues.put("name",subtask.getName());
        contentValues.put("description",subtask.getDescription());
        contentValues.put("completed",subtask.isComplete() ? 1 : 0);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int primaryKey = (int)db.insert("subtask","id",contentValues);

        subtask.setId(primaryKey);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",subtask.getName());
        contentValues.put("description",subtask.getDescription());
        contentValues.put("completed",subtask.isComplete() ? 1 : 0);

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.update("subtask",contentValues,"id = ?",new String[]{""+subtask.getId()});
    }

    @Override
    public void deleteSubtask(Subtask subtask) {
        SQLiteDatabase db = this.databaseHelper.getWritableDatabase();
        String [] deleteArgs = {""+subtask.getId()};
        db.delete("subtask","id = ?",deleteArgs);
    }

    public void addOrUpdate(Subtask subtask){
        if ( exists(subtask)){
            updateSubtask(subtask);
        }else{
            addSubtask(subtask);
        }
    }

    public boolean exists(Subtask subtask){
        boolean exists = false;
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query("subtask",null,"id = ?",new String[]{""+subtask.getId()},null,null,null);

        exists = cursor.moveToNext();

        return exists;
    }

    @Override
    public List<Subtask> getSubTasks(int task_id) {
        String[] columns = {"id","name","description","completed"};
        String [] args = {""+task_id};
        List<Subtask> tasks = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query("subtask",columns,"task_id = ?",args,null,null,null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String description = cursor.getString(2);
            int completed = cursor.getInt(3);
            tasks.add( new Subtask(id,name,description,completed == 1) );
        }
        return tasks;
    }
}
