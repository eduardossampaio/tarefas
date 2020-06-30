package com.apps.esampaio.legacy.core.entities;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateTime implements Serializable{
    private Date date;
    private Date time;

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    private Calendar calendar = Calendar.getInstance();

    public static DateTime getCurrentDateTime() {
        return new DateTime(new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
    }

    public DateTime() {
        date = null;// new Date(System.currentTimeMillis());
        time = null;//new Date(System.currentTimeMillis());
        applyChanges();
    }

    public DateTime(Date date, Date time) {
        this.date = date;
        this.time = time;
        applyChanges();
    }

    public DateTime(int year, int month, int day) {
        setDate(year, month, day);

    }

    public DateTime(int year, int month, int day, int hour, int minute) {
        setDate(year, month, day);
        setTime(hour, minute);
    }

    private void applyChanges() {
        if (date != null) {
            calendar.setTime(date);
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }
        if (time != null) {
            calendar.setTime(time);
            calendar.set(Calendar.SECOND,0);
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);

        }
    }

    public void setDate(int year, int month, int day) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        date = calendar.getTime();
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public void setDate(Date date) {
        this.date = date;
        applyChanges();
    }

    public void setTime(int hour, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND,0);
        time = calendar.getTime();
        this.hour = hour;
        this.minute = minute;
    }

    public void setTime(Date time) {
        this.time = time;
        applyChanges();
    }

    public boolean dateSetted() {
        return date != null;
    }

    public boolean timeSetted() {
        return time != null;
    }

    public void invalidateDate() {
        this.date = null;
        applyChanges();
    }

    public void invalidateTime() {
        this.time = null;
        applyChanges();
    }

    public String formatDate() {
        if (date == null)
            return null;
        return DateFormat.getDateInstance(DateFormat.MEDIUM).format(date);
    }

    public String formatTime() {
        if (time == null)
            return null;
        return DateFormat.getTimeInstance(DateFormat.SHORT).format(time);
    }

    public boolean dateEquals(DateTime dateTime) {
        return this.year == dateTime.year && this.month == dateTime.month && this.day == dateTime.day;
    }

    public boolean timeEquals(DateTime dateTime) {
        return this.hour == dateTime.hour && this.minute== dateTime.minute;
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
