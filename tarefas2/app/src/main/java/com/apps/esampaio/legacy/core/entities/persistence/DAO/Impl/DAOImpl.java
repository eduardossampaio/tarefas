package com.apps.esampaio.legacy.core.entities.persistence.DAO.Impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.apps.esampaio.legacy.core.entities.persistence.DAO.Impl.entities.Entity;


public abstract class DAOImpl {

    public int addEntity(SQLiteDatabase database,Entity entity){
        ContentValues contentValues = entity.getContentValues();
        String idTable = entity.getTableName();
        return  (int) database.insert(entity.getTableName(),idTable,contentValues);

    }

    public boolean exists(SQLiteDatabase database,Entity entity){
        boolean exists ;
        String where = entity.getIdColumnName()+" = ?";
        String [] args = new String[]{""+entity.getId()};

        Cursor cursor = database.query(entity.getTableName(),null,where,args,null,null,null);

        exists = cursor.moveToNext();
        cursor.close();
        return exists;
    }


    protected void updateById(SQLiteDatabase database,Entity entity){
        String [] args = new String[]{""+entity.getId()};
        String where = entity.getIdColumnName()+" = ?";
        update(database,entity,where,args);
    }


    private void update(SQLiteDatabase database,Entity entity,String where,String[] args){
        ContentValues contentValues =entity.getContentValues();
        database.update(entity.getTableName(),contentValues,where,args);
    }

    protected void  deleteById(SQLiteDatabase database,Entity entity){
        String [] args = new String[]{""+entity.getId()};
        String where = entity.getIdColumnName()+" = ?";
        delete(database,entity,where,args);
    }

    private void delete(SQLiteDatabase database,Entity entity,String where,String[] args){
        database.delete(entity.getTableName(),where,args);
    }
}
