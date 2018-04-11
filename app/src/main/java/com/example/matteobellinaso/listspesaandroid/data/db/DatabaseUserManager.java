package com.example.matteobellinaso.listspesaandroid.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseUserManager {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context context;
    // Database constants
    private static final String DATABASE_TABLE = "users";
    public static final String KEY_USERID = "_id";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_NAME = "name";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_IMG = "img";
    public static final String KEY_TUTORIAL = "tutorial";


    public DatabaseUserManager(Context context) {
        this.context = context;
    }

    public DatabaseUserManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }
    public void close() {
        dbHelper.close();
    }

    private ContentValues createContentUserValues(String email,String name, String password, String img, boolean tutorial) {
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, email);
        values.put(KEY_NAME, name);
        values.put(KEY_PASSWORD, password);
        values.put(KEY_IMG, img);
        values.put(KEY_TUTORIAL, tutorial);
        return values;
    }

    //create a contact
    public long createUser(String email, String name, String password, String img, boolean tutorial) {
        ContentValues initialValues = createContentUserValues(email,name, password, img, tutorial) ;
        return database.insertOrThrow(DATABASE_TABLE, null, initialValues);
    }
    //update a contact
    public boolean updateUser( long userID, String email, String name, String password, String img, boolean tutorial) {
        ContentValues updateValues = createContentUserValues(email,name, password, img,tutorial);
        return database.update(DATABASE_TABLE, updateValues, KEY_USERID + "=" + userID, null) > 0;
    }
    //delete a contact
    public boolean deleteUser(long contactID) {
        return database.delete(DATABASE_TABLE, KEY_USERID + "=" + contactID, null) > 0;
    }
    //fetch all contacts
    public Cursor fetchAllUsers() {
        return database.query(DATABASE_TABLE,null, null, null, null, null,null);
    }

    public Cursor selectUser(String email, String password){
        String[] columns = new String[]{KEY_EMAIL,KEY_PASSWORD};
        return database.query(DATABASE_TABLE, columns,"email = '"+email+"' AND password = '"+password+"'",null,null,null,null);
    }
}
