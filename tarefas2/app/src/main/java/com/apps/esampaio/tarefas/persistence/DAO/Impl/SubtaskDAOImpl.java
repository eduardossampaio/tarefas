package com.apps.esampaio.tarefas.persistence.DAO.Impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.apps.esampaio.tarefas.entities.Subtask;
import com.apps.esampaio.tarefas.persistence.DAO.Impl.entities.SubtaskEntity;
import com.apps.esampaio.tarefas.persistence.DAO.Impl.entities.TaskEntity;
import com.apps.esampaio.tarefas.persistence.DAO.SubtaskDAO;
import com.apps.esampaio.tarefas.persistence.DatabaseHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by eduardo on 28/06/2016.
 */

public class SubtaskDAOImpl extends DAOImpl implements SubtaskDAO {

    private DatabaseHelper databaseHelper;
    public SubtaskDAOImpl(Context context){
        databaseHelper = new DatabaseHelper(context);
    }

    @Override
    public void addSubtask(Subtask subtask) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int primaryKey = addEntity(db,new SubtaskEntity(subtask));
        subtask.setId(primaryKey);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        updateById(db,new SubtaskEntity(subtask));
    }

    @Override
    public void deleteSubtask(Subtask subtask) {
        SQLiteDatabase db = this.databaseHelper.getWritableDatabase();
        deleteById(db,new SubtaskEntity(subtask));
    }

    public void addOrUpdate(Subtask subtask){
        if ( exists(subtask)){
            updateSubtask(subtask);
        }else{
            addSubtask(subtask);
        }
    }

    public boolean exists(Subtask subtask){
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        return exists(db,new SubtaskEntity(subtask));
    }

    @Override
    public List<Subtask> getSubTasks(int task_id) {
        String[] columns = {"id","name","description","completed","task_date","task_time"};
//        String[] columns = {"id","name","description","completed"};
        String [] args = {""+task_id};
        List<Subtask> tasks = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query("subtask",columns,"task_id = ?",args,null,null,null);
        while(cursor.moveToNext()){
            Date taskDate = null;
            Date taskTime = null;
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String description = cursor.getString(2);
            int completed = cursor.getInt(3);
            long dateMillis = cursor.getLong(4);
//            //todo ver um jeito melhor de fazer isso
            if ( dateMillis > 0){
                taskDate= new Date(dateMillis);
            }

            long timeillis = cursor.getLong(5);
//            //todo ver um jeito melhor de fazer isso
            if ( timeillis > 0){
                taskTime= new Date(timeillis);
            }
            tasks.add( new Subtask(id,name,description,completed == 1,taskDate,taskTime) );
        }
        return tasks;
    }
}
