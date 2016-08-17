package com.apps.esampaio.tarefas.persistence.DAO.Impl.entities;

import android.content.ContentValues;

/**
 * Created by eduardo on 28/06/2016.
 */

public interface Entity {

    public String getCreateTableSQL();

    public String getTableName();

    public ContentValues getContentValues();

    public String getIdColumnName();
    public int getId();

    public String getUpdateSQL(int dabataseVersion);
}
