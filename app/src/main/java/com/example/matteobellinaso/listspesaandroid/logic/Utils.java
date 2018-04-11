package com.example.matteobellinaso.listspesaandroid.logic;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.DateFormat;

public class Utils {

    private static final String TIMESTAMP = "timestamp";
    private static final String TIMESTAMP_VALUE = "timestamp_value";
    private static final String USERNAME_VALUE = "username_value";

    public static void writeOnSharedPreferences(Long timestamp, int id, Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(TIMESTAMP,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(TIMESTAMP_VALUE,timestamp);
        editor.putInt(USERNAME_VALUE,id);
        editor.commit();
    }

    public static Long readTimestamp(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(TIMESTAMP,Context.MODE_PRIVATE);
        return sharedPref.getLong(TIMESTAMP_VALUE,0);
    }

    public static int readId(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(TIMESTAMP,Context.MODE_PRIVATE);
        return sharedPref.getInt(USERNAME_VALUE,0);
    }

    public static boolean deleteSharedPreferences(Context context){
        return context.deleteSharedPreferences(TIMESTAMP);
    }

    public static String convertDate(String dateInMilliseconds,String dateFormat) {
        return DateFormat.format(dateFormat, Long.parseLong(dateInMilliseconds)).toString();
    }
}
