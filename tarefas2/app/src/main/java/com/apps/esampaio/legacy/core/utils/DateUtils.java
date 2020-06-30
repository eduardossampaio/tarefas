package com.apps.esampaio.legacy.core.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by eduardo on 15/08/2016.
 */

public class DateUtils {
    public static String formatDate(long millis,String pattern){
        try {
            Date date = new Date(millis);
            DateFormat dateFormat = new SimpleDateFormat(pattern);
            return dateFormat.format(date);
        }catch (Exception e){
            return "";
        }
    }

    public static Date toDate(String pattern,String dateFormated){
        try {

            DateFormat dateFormat = new SimpleDateFormat(pattern);
            return dateFormat.parse(dateFormated);
        }catch (Exception e){
            return null;
        }
    }

}
