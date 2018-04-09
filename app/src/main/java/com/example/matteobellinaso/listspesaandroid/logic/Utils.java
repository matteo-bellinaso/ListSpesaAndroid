package com.example.matteobellinaso.listspesaandroid.logic;

import android.content.Context;
import android.content.SharedPreferences;

public class Utils {

    private static final String TIMESTAMP = "timestamp";
    private static final String TIMESTAMP_VALUE = "timestamp_value";

    public static void writeOnSharedPreferences(Long timestamp, Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(TIMESTAMP,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(TIMESTAMP_VALUE,timestamp);
        editor.commit();
    }

    public static Long readOnSharedPreferences(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(TIMESTAMP,Context.MODE_PRIVATE);
        return sharedPref.getLong(TIMESTAMP_VALUE,0);
    }
}
