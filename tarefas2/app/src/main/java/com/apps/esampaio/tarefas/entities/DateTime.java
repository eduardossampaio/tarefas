package com.apps.esampaio.tarefas.entities;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by eduardo on 02/08/2016.
 */

public class DateTime {
    private Date date;
    private Date time;

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    private Calendar calendar = Calendar.getInstance();
    public static DateTime getCurrentDateTime(){
        return new DateTime(new Date(System.currentTimeMillis()),new Date(System.currentTimeMillis()));
    }

    public DateTime(){
        date =null;// new Date(System.currentTimeMillis());
        time = null ;//new Date(System.currentTimeMillis());
        applyChanges();
    }

    public DateTime(Date date,Date time){
        this.date = date;
        this.time = time;
        applyChanges();
    }
    public DateTime(int year,int month,int day){
        setDate(year,month,day);

    }
    public DateTime(int year,int month,int day,int hour,int minute){
        setDate(year,month,day);
        setTime(hour,minute);
    }

    private void applyChanges(){
        year    = calendar.get(Calendar.YEAR);
        month   = calendar.get(Calendar.MONTH);
        day     = calendar.get(Calendar.DAY_OF_MONTH);
        hour    = calendar.get(Calendar.HOUR);
        minute  = calendar.get(Calendar.MINUTE);
    }

    public void setDate(int year,int month,int day){
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        date = calendar.getTime();
        this.year   = year;
        this.month  = month;
        this.day    = day;
    }
    public void setTime(int hour,int minute){
        calendar.set(Calendar.HOUR,hour);
        calendar.set(Calendar.MINUTE,minute);
        time = calendar.getTime();
        this.hour = hour;
        this.minute = minute;
    }

    public boolean dateSetted(){
        return date!=null;
    }
    public boolean timeSetted(){
        return time!=null;
    }

    public void invalidateDate(){
        this.date=null;
        applyChanges();
    }
    public void invalidateTime(){
        this.time=null;
        applyChanges();
    }


    public Date getDate() {
        return date;
    }

    public Date getTime() {
        return time;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

}
