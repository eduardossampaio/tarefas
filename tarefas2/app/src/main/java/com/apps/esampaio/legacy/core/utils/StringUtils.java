package com.apps.esampaio.legacy.core.utils;

/**
 * Created by eduardo on 11/08/2016.
 */

public class StringUtils {
    public static String capitalize(String s,boolean isToCapitalize){
        if(!isToCapitalize)
            return s;
        return Character.toUpperCase(s.charAt(0))+s.substring(1);
    }

    public static String append(boolean space,String ... strings){
        StringBuilder builder = new StringBuilder();
        for (String s: strings) {
            if(s.startsWith(" ") || s.endsWith(" ")) {
                builder.append(s.trim());
            }else {
                builder.append(s);
            }
            if(space)
                builder.append(" ");
        }
        return builder.toString();
    }
}
