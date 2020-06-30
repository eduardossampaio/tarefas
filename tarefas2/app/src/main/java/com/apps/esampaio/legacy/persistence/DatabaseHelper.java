package com.apps.esampaio.legacy.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.apps.esampaio.legacy.persistence.DAO.Impl.entities.SubtaskEntity;
import com.apps.esampaio.legacy.persistence.DAO.Impl.entities.TaskEntity;
import com.apps.esampaio.legacy.persistence.DAO.Impl.entities.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eduardo on 28/06/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int    VERSION = 2;
    private static final String NAME    = "tasks_database";

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
                throw new IllegalStateException(e);
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (Class clasz: entitiesClasses) {
            try {
                Entity entity = (Entity)clasz.newInstance();
                String[] usqls = entity.getUpdateSQLs(newVersion);
                if(usqls != null && usqls.length!=0) {
                    for (String  usql :usqls) {
                        db.execSQL(usql);
                    }
                }
            }catch (Exception e){
                throw new IllegalStateException(e);
            }
        }
    }
}
