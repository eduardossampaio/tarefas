package com.apps.esampaio.tarefas.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.apps.esampaio.tarefas.persistence.DAO.Impl.entities.SubtaskEntity;
import com.apps.esampaio.tarefas.persistence.DAO.Impl.entities.TaskEntity;
import com.apps.esampaio.tarefas.persistence.DAO.Impl.entities.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eduardo on 28/06/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int    VERSION = 1;
    private static final String NAME   = "tasks_database";

    private static final List<Class> entitiesClasses=new ArrayList<>();
    static {
        entitiesClasses.add(TaskEntity.class);
        entitiesClasses.add(SubtaskEntity.class);
    }

    private Context context;
    public DatabaseHelper(Context context ){
        super(context,NAME,null,VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        for (Class clasz: entitiesClasses) {
            try {
                Entity entity = (Entity)clasz.newInstance();
                db.execSQL(entity.getCreateTableSQL());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
