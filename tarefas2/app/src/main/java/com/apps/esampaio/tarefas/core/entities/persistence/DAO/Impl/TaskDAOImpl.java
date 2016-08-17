package com.apps.esampaio.tarefas.core.entities.persistence.DAO.Impl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.apps.esampaio.tarefas.core.entities.Subtask;
import com.apps.esampaio.tarefas.core.entities.Task;
import com.apps.esampaio.tarefas.core.entities.persistence.DAO.Impl.entities.Entity;
import com.apps.esampaio.tarefas.core.entities.persistence.DAO.Impl.entities.TaskEntity;
import com.apps.esampaio.tarefas.core.entities.persistence.DAO.TaskDAO;
import com.apps.esampaio.tarefas.core.entities.persistence.DAO.Transaction;
import com.apps.esampaio.tarefas.core.entities.persistence.DatabaseHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TaskDAOImpl extends DAOImpl implements TaskDAO {
    private SQLiteOpenHelper databaseHelper;

    public TaskDAOImpl(Context context){
        databaseHelper= new DatabaseHelper(context);
    }

    @Override
    public List<Task> getTasks() {
       return beginTransaction()
               .select("Task",new String[]{"id","name"})
               .select("Subtask",new String[]{"id", "name", "description", "completed", "task_date", "task_time"})
               .from("Task")
               .leftJoin("Task","id","Subtask","task_id")
               .execute();

    }

    public Task getTask(int taskId){
        return beginTransaction()
                .select()
                .from("Task")
                .where()
                .eq("id",taskId)
                .uniqueResult();
    }

    @Override
    public Transaction<Task> beginTransaction() {
        return new BaseTransaction<Task>() {
            @Override
            public List<Task> execute() {
                return rawQuery(sql.toString());
            }

            @Override
            public Task uniqueResult() {
                return rawUnique(sql.toString());
            }
        };
    }

    @Override
    public List<Task> getTasksByDate(Date date) {

        return beginTransaction()
                .select("Task",new String[]{"id","name"})
                .select("Subtask",new String[]{"id", "name", "description", "completed", "task_date", "task_time"})
                .from("Task")
                .from("subtask")
                .where()
                .eq("t.id","s.task_id").and()
                .eqDate("s.task_date",date)
                .group("t.name")
                .execute();

    }
    @Override
    public List<Task> getTasksByDate(Date date,boolean completed) {
        return beginTransaction()
                .select("Task",new String[]{"id","name"})
                .select("Subtask",new String[]{"id", "name", "description", "completed", "task_date", "task_time"})
                .from("Task")
                .from("subtask")
                .where()
                .eq("t.id","s.task_id").and()
                .eqDate("s.task_date",date).and()
                .eq("s.completed",completed)
                .group("t.name")
                .execute();

    }


    @Override
    public List<Task> getTasksByTime(Date time) {
        return beginTransaction()
                .select("Task",new String[]{"id","name"})
                .select("Subtask",new String[]{"id", "name", "description", "completed", "task_date", "task_time"})
                .from("Task")
                .from("subtask")
                .where()
                .eqDate("s.task_date",time).and()
                .eqTime("s.task_time",time).and()
                .eq("t.id","s.task_id").and()
                .eq("s.completed",false)
                .group("t.name")
                .execute();

    }

    public List<Task> getTasksByCompleted(boolean completed){
        return beginTransaction()
                .select("Task",new String[]{"id","name"})
                .select("Subtask",new String[]{"id", "name", "description", "completed", "task_date", "task_time"})
                .from("Task")
                .from("subtask")
                .where()
                .eq("t.id","s.task_id")
                .and().eq("s.completed",completed)
                .group("t.name")
                .execute();

    }
    private List<Task> rawQuery(String sql){
        List<Task> tasks = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        Task task = new Task(0,null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            if(task.getId()!=id) {
                task = new Task(id,name);
                tasks.add(task);
            }
            //subtask
            int subtaskId = cursor.getInt(2);
            if(subtaskId != 0) {
                String subtaskName = cursor.getString(3);
                String description = cursor.getString(4);
                int completed = cursor.getInt(5);
                long dateMillis = cursor.getLong(6);
                Date taskDate = null;
                Date taskTime = null;
                if (dateMillis > 0) {
                    taskDate = new Date(dateMillis);
                }
                long timeillis = cursor.getLong(7);
                if (timeillis > 0) {
                    taskTime = new Date(timeillis);
                }
                task.addSubtask(new Subtask(subtaskId, subtaskName, description, completed == 1, taskDate, taskTime));
            }
        }
        cursor.close();
        return tasks;
    }
    private Task rawUnique(String sql){
        Task task = null;
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
           task = new Task(id,name);

        }
        cursor.close();
        return task;
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
