package com.apps.esampaio.tarefas.core.entities.persistence.DAO.Impl;

import com.apps.esampaio.tarefas.core.entities.persistence.DAO.Transaction;

import java.util.Date;


public abstract class BaseTransaction<T> implements Transaction<T> {
    protected StringBuilder sql = new StringBuilder();

    @Override
    public Transaction<T> where() {
        if (sql.indexOf("WHERE") == -1)
            sql.append(" WHERE ");
        return this;
    }
    @Override
    public Transaction<T> select() {
        return select("*");
    }
    @Override
    public Transaction<T> select(String args) {
        sql.append("SELECT ");
        sql.append(args);
        return this;
    }
    public Transaction<T> select(String table, String[] columns){
        if(sql.indexOf("SELECT")==-1)
            sql.append(" SELECT ");
        for (String key : columns){
            sql.append(table.charAt(0));
            sql.append(".");
            sql.append(key);
            sql.append(",");
        }
        return this;
    }

    @Override
    public Transaction<T> from(String args) {
        if (sql.indexOf("FROM") == -1) {
            if(sql.charAt(sql.length()-1)==',')
                sql.deleteCharAt(sql.length()-1);
            sql.append(" FROM ");
        }
        else
            sql.append(",");
        sql.append(args);
        sql.append(" ");
        sql.append(args.charAt(0));
        return this;
    }
    public Transaction<T> leftJoin(String table1,String id1,String table2,String id2){
        sql.append(" LEFT OUTER JOIN ");
        sql.append(table2).append( " as ").append(table2.charAt(0));
        sql.append(" ON ");
        sql.append(table1.charAt(0)).append(".").append(id1);
        sql.append("=");
        sql.append(table2.charAt(0)).append(".").append(id2);
        return this;
    }

    @Override
    public Transaction<T> and() {
        sql.append(" AND ");
        return this;
    }

    @Override
    public Transaction<T> bool(boolean args) {
        sql.append(" ");
        sql.append(args ? "1" : "0");
        return this;
    }

    @Override
    public Transaction<T> or() {
        sql.append(" OR ");
        return this;
    }

    @Override
    public Transaction<T> or(String arg) {
        sql.append(" OR ").append(arg);
        return this;
    }

    @Override
    public Transaction<T> eq() {
        sql.append(" = ");
        return this;
    }

    @Override
    public Transaction<T> eq(String arg1, String arg2) {
        sql.append(arg1).append(" = ").append(arg2);
        return this;
    }

    @Override
    public Transaction<T> eqDate(String arg1, Date arg2) {
        this.date(arg1);
        sql.append(" = ");
        this.date(arg2.getTime()+"");
        return this;
    }
    @Override
    public Transaction<T> eqTime(String arg1, Date arg2) {
        this.time(arg1);
        sql.append(" = ");
        this.time(arg2.getTime()+"");
        return this;
    }

    @Override
    public Transaction<T> eq(String arg1, boolean arg2) {
        sql.append(arg1).append(" = ");
        sql.append(arg2 ? "1" : "0");
        return this;
    }
    public Transaction<T> eq(String arg1, int arg2) {
        sql.append(arg1).append(" = ");
        sql.append(arg2);
        return this;
    }

    @Override
    public Transaction<T> date(String args) {
        sql.append("date(").append(args).append("/1000, 'unixepoch','localtime')");
        return this;
    }
    @Override
    public Transaction<T> time(String args) {
        sql.append("time(").append(args).append("/1000, 'unixepoch','localtime')");
        return this;
    }

    @Override
    public Transaction<T> group(String args) {
        sql.append(" GROUP BY ");
        sql.append(args);
        return this;
    }

}
