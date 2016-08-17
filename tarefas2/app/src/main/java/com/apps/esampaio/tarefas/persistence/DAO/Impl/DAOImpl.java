package com.apps.esampaio.tarefas.persistence.DAO.Impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.apps.esampaio.tarefas.persistence.DAO.Impl.entities.Entity;

/**
 * Created by eduardo on 02/07/2016.
 */

public abstract class DAOImpl {

    public int addEntity(SQLiteDatabase database,Entity entity){
        ContentValues contentValues = entity.getContentValues();
        String idTable = entity.getTableName();
        int id = entity.getId();
        int primaryKey =(int) database.insert(entity.getTableName(),idTable,contentValues);
        return primaryKey;
    }

    public boolean exists(SQLiteDatabase database,Entity entity){
        boolean exists = false;
        String where = entity.getIdColumnName()+" = ?";
        String [] args = new String[]{""+entity.getId()};

        Cursor cursor = database.query(entity.getTableName(),null,where,args,null,null,null);

        exists = cursor.moveToNext();
        cursor.close();
        return exists;
    }

    public void updateById(SQLiteDatabase database,Entity entity){
        String [] args = new String[]{""+entity.getId()};
        String where = entity.getIdColumnName()+" = ?";
        update(database,entity,where,args);
    }


    public void update(SQLiteDatabase database,Entity entity,String where,String[] args){
        ContentValues contentValues =entity.getContentValues();
        database.update(entity.getTableName(),contentValues,where,args);
    }

    public void  deleteById(SQLiteDatabase database,Entity entity){
        String [] args = new String[]{""+entity.getId()};
        String where = entity.getIdColumnName()+" = ?";
        delete(database,entity,where,args);
    }

    public void delete(SQLiteDatabase database,Entity entity,String where,String[] args){
        database.delete(entity.getTableName(),where,args);
    }
}
