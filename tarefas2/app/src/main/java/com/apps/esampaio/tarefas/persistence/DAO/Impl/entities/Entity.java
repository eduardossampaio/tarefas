package com.apps.esampaio.tarefas.persistence.DAO.Impl.entities;

import android.content.ContentValues;

/**
 * Created by eduardo on 28/06/2016.
 */

public interface Entity {

    String getCreateTableSQL();

    String getTableName();

    ContentValues getContentValues();

    String getIdColumnName();

    int getId();

    String[] getUpdateSQLs(int dabataseVersion);
}
