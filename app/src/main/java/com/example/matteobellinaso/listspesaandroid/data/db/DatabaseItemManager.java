package com.example.matteobellinaso.listspesaandroid.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseItemManager {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context context;
    // Database constants
    private static final String DATABASE_TABLE = "items";
    public static final String KEY_LIST_ID = "_id";
    public static final String KEY_LIST_NAME = "name";
    public static final String KEY_LIST_CHECK = "checked";
    public static final String KEY_LIST_LISTID = "listId";


    public DatabaseItemManager(Context context) {
        this.context = context;
    }

    public DatabaseItemManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    private ContentValues createContentItemValues(String name, int check, int listId) {
        ContentValues values = new ContentValues();
        values.put(KEY_LIST_NAME, name);
        values.put(KEY_LIST_CHECK, check);
        values.put(KEY_LIST_LISTID, listId);
        return values;
    }

    private ContentValues contentCheck(int check) {
        ContentValues values = new ContentValues();
        values.put(KEY_LIST_CHECK, check);
        return values;
    }

    //create a contact
    public long createItem( String name, int check, int listId ) {
        ContentValues initialValues = createContentItemValues(name, check, listId) ;
        return database.insertOrThrow(DATABASE_TABLE, null, initialValues);
    }
    //delete a contact
    public boolean deleteItem(int itemId) {
        return database.delete(DATABASE_TABLE, KEY_LIST_ID + "=" + itemId, null) > 0;
    }

    public Cursor fetchFromId(int id) {
        return database.query(DATABASE_TABLE,null, KEY_LIST_LISTID +" = "+id, null, null, null,null);
    }

    public boolean updateItem(int id, int check) {
        ContentValues updateValues = contentCheck(check);
        return database.update(DATABASE_TABLE, updateValues, KEY_LIST_ID + "=" + id, null) > 0;
    }

    public Cursor checkedValues(int id){
           return database.query(DATABASE_TABLE, null,KEY_LIST_LISTID +" = "+id+" AND "+KEY_LIST_CHECK+" = "+0,null,null,null,null);
    }
}
