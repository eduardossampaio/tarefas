package com.apps.esampaio.tarefas.core.entities.persistence.DAO;

import android.content.ContentValues;

import java.util.Date;
import java.util.List;

/**
 * Created by eduardo on 12/08/2016.
 */

public interface Transaction<T> {
    public Transaction<T> select();
    public Transaction<T> select(String args);
    public Transaction<T> select(String table, String[] columns);
    public Transaction<T> from(String args);
    public Transaction<T> where();
    public Transaction<T> leftJoin(String table1,String id,String table2,String id2);
    public Transaction<T> and();
    public Transaction<T> or();
    public Transaction<T> or(String arg);
    public Transaction<T> eq();
    public Transaction<T> eq(String arg1,String arg2);
    public Transaction<T> eq(String arg1,boolean arg2);
    public Transaction<T> eq(String arg1,int arg2);
    public Transaction<T> eqDate(String arg1,Date arg2);
    public Transaction<T> eqTime(String arg1,Date arg2);
    public Transaction<T> date(String args);
    public Transaction<T> time(String args);
    public Transaction<T> bool(boolean args);
    public Transaction<T> group(String args);
    public T uniqueResult();
    public List<T> execute();
}
