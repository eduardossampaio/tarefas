package com.apps.esampaio.legacy.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;



public class Settings {

    public enum Order{
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

    private Context context;
    public static Settings getInstance(Context context){
        return  new Settings(context);
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
    public boolean manualBackup(){
        return getBoolPref("preference_enable_manual_backup",false);
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
