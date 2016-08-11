package com.apps.esampaio.tarefas.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.IntegerRes;

/**
 * Created by eduardo on 11/08/2016.
 */

public class Settings {

    public static enum Order{
        NO_ORDER,
        Name,
        Date,
        COMPLETED;
        static Order get(int i){
            for (Order order:values()) {
                if(order.ordinal() == i)
                    return order;
            }
            return null;
        }
    }
    private static Settings instance;
    private Context context;

    public static Settings getInstance(Context context){
        if(instance == null){
            instance = new Settings(context);
        }
        return instance;
    }

    public Settings(Context context){
        this.context = context;
    }
    public boolean capitalizeFirst(){
        return getBoolPref("preference_capitalize_first",false);
    }

    public boolean vibrate(){
        return getBoolPref("preference_vibrate",true);
    }
    public boolean notifyAllTasks(){
        return getBoolPref("preference_notify_all",true);
    }
    public Order order(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        int order = Integer.parseInt( preferences.getString("preference_order_type","0") );
        return Order.get(order);
    }


    private boolean getBoolPref(String key,boolean defaultValue){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(key,defaultValue);
    }

    public int notifyBefore(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return Integer.parseInt(preferences.getString("preference_notify_before","0"));

    }

}
