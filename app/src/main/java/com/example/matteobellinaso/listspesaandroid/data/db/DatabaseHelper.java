package com.example.matteobellinaso.listspesaandroid.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;
    public static final String KEY_EMAIL = "email";
    public static final String KEY_NAME = "name";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_IMG = "img";
    public static final String KEY_TUTORIAL = "tutorial";

    // Lo statement SQL di creazione del database
    private static final String DATABASE_CREATE =
            "CREATE TABLE users (_id integer primary key autoincrement,"+KEY_EMAIL+" varchar(255), "+KEY_NAME+" varchar(255), "+KEY_PASSWORD+" varchar(255), "+KEY_IMG+" varchar(255), "+KEY_TUTORIAL+" boolean);";

    // Costruttore
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Questo metodo viene chiamato durante la creazione del database
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    // Questo metodo viene chiamato durante l'upgrade del database, ad esempio quando viene incrementato il numero di versione
    @Override
    public void onUpgrade( SQLiteDatabase database, int oldVersion, int newVersion ) {
        database.execSQL("DROP TABLE IF EXISTS contact");
        onCreate(database);
    }


}
