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
            "CREATE TABLE users (_id integer primary key autoincrement, "+KEY_EMAIL+" text, "+KEY_NAME+" text, "+KEY_PASSWORD+" text, "+KEY_IMG+" text, "+KEY_TUTORIAL+" text);";

    public static final String KEY_LIST_NAME = "name";
    public static final String KEY_LIST_IMG = "img";
    public static final String KEY_LIST_USERID = "userId";

    private static final String DB_LIST_CREATE=
            "CREATE TABLE itemlist (_id integer primary key autoincrement, "+KEY_LIST_NAME+" text, "+KEY_LIST_IMG+" text, " + KEY_LIST_USERID +" integer);";

    public static final String KEY_ITEM_NAME = "name";
    public static final String KEY_ITEM_CHECK = "checked";
    public static final String KEY_ITEM_LISTID = "listId";

    private static final String DB_ITEM_CREATE=
            "CREATE TABLE items (_id integer primary key autoincrement ,"+KEY_ITEM_NAME+" text,"+KEY_ITEM_CHECK+" integer, "+KEY_ITEM_LISTID+" integer);";

    // Costruttore
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Questo metodo viene chiamato durante la creazione del database
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        database.execSQL(DB_LIST_CREATE);
        database.execSQL(DB_ITEM_CREATE);
    }

    // Questo metodo viene chiamato durante l'upgrade del database, ad esempio quando viene incrementato il numero di versione
    @Override
    public void onUpgrade( SQLiteDatabase database, int oldVersion, int newVersion ) {
        database.execSQL("DROP TABLE IF EXISTS contact");
        database.execSQL("DROP TABLE IF EXISTS itemlist");
        database.execSQL("DROP TABLE IF EXISTS items");
        onCreate(database);
    }


}
