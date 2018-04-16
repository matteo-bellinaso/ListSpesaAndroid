package com.example.matteobellinaso.listspesaandroid.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by matteobellinaso on 11/04/18.
 */

public class DatabaseListManager {


    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context context;
    // Database constants
    private static final String DATABASE_TABLE = "itemlist";
    public static final String KEY_LIST_ID = "_id";
    public static final String KEY_LIST_NAME = "name";
    public static final String KEY_LIST_IMG = "img";
    public static final String KEY_LIST_USERID = "userId";


    public DatabaseListManager(Context context) {
        this.context = context;
    }

    public DatabaseListManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }
    public void close() {
        dbHelper.close();
    }

    private ContentValues createContentListValues(String name, String img, int userId) {
        ContentValues values = new ContentValues();
        values.put(KEY_LIST_NAME, name);
        values.put(KEY_LIST_IMG, img);
        values.put(KEY_LIST_USERID, userId);
        return values;
    }

    //create a contact
    public long createList( String name, String img, int userId ) {
        ContentValues initialValues = createContentListValues(name, img, userId) ;
        return database.insertOrThrow(DATABASE_TABLE, null, initialValues);
    }
    //update a contact
    public boolean updateList( long listId, String name, String img, int userId) {
        ContentValues updateValues = createContentListValues(name, img,userId);
        return database.update(DATABASE_TABLE, updateValues, KEY_LIST_ID + "=" + listId, null) > 0;
    }
    //delete a contact
    public boolean deleteList(long listId) {
        return database.delete(DATABASE_TABLE, KEY_LIST_ID + "=" + listId, null) > 0;
    }
    //fetch all contacts
    public Cursor fetchAllList() {
        return database.query(DATABASE_TABLE,null, null, null, null, null,null);
    }

}
