package com.apps.esampaio.tarefas.core.entities.persistence.DAO;

import java.util.Date;
import java.util.List;

public interface Transaction<T> {
    Transaction<T> select();
    Transaction<T> select(String args);
    Transaction<T> select(String table, String[] columns);
    Transaction<T> from(String args);
    Transaction<T> where();
    Transaction<T> leftJoin(String table1, String id, String table2, String id2);
    Transaction<T> and();
    Transaction<T> or();
    Transaction<T> or(String arg);
    Transaction<T> eq();
    Transaction<T> eq(String arg1, String arg2);
    Transaction<T> eq(String arg1, boolean arg2);
    Transaction<T> eq(String arg1, int arg2);
    Transaction<T> eqDate(String arg1, Date arg2);
    Transaction<T> eqTime(String arg1, Date arg2);
    Transaction<T> date(String args);
    Transaction<T> time(String args);
    Transaction<T> bool(boolean args);
    Transaction<T> group(String args);
    T uniqueResult();
    List<T> execute();
}
