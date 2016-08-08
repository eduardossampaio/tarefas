package com.apps.esampaio.tarefas.persistence.DAO.Impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.apps.esampaio.tarefas.entities.Subtask;
import com.apps.esampaio.tarefas.entities.Task;
import com.apps.esampaio.tarefas.persistence.DAO.Impl.entities.Entity;
import com.apps.esampaio.tarefas.persistence.DAO.Impl.entities.TaskEntity;
import com.apps.esampaio.tarefas.persistence.DAO.TaskDAO;
import com.apps.esampaio.tarefas.persistence.DatabaseHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by eduardo on 28/06/2016.
 */

public class TaskDAOImpl extends DAOImpl implements TaskDAO {
    private static final String GROUP_BY = " group by t.name";
    private SQLiteOpenHelper databaseHelper;
    private final String SELECT_TASK_SUBTASK = "SELECT * from task t , subtask s ";

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
    public List<Task> getTasksByDate(Date date) {
        String sql =SELECT_TASK_SUBTASK+" where t.id = s.task_id and "+dateSql("s.task_date")+" = "+dateSql("?")+GROUP_BY;
        String []args={""+date.getTime()};
        sql = sql.replaceAll("\\?",args[0]);
        return rawQuery(sql);
    }

    @Override
    public List<Task> getTasksByTime(Date time) {
        String sql =SELECT_TASK_SUBTASK+" where "+dateSql("s.task_date") +" = "+dateSql(time.getTime())+"and "+timeSql("s.task_time")+" = "+timeSql(time.getTime())+ " and t.id = s.task_id and s.completed= 0"+GROUP_BY;
        String []args={""+time.getTime()};
        sql = sql.replaceAll("\\?",args[0]);
        return rawQuery(sql);
    }
    private List<Task> rawQuery(String sql){
        List<Task> tasks = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            Task task = new Task(id,name);
            tasks.add(task);
        }
        return tasks;
    }

    @Override
    public void updateTask(Task task) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        updateById(db,new TaskEntity(task));
    }

    @Override
    public void deleteTask(Task task) {
        SQLiteDatabase db = this.databaseHelper.getWritableDatabase();
        deleteById(db,new TaskEntity(task));
    }

    @Override
    public void addTask(Task task) {
        Entity entity = new TaskEntity(task);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int id = addEntity(db,entity);
        task.setId(id);
    }
}
