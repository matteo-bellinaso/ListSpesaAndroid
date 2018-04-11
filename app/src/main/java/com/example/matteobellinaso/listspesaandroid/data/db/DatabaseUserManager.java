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
    private static final String DATABASE_LIST = "itemlist";
    public static final String KEY_USERID = "_id";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_NAME = "name";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_IMG = "img";
    public static final String KEY_TUTORIAL = "tutorial";
    public static final String KEY_LIST_USERID= "userId";



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

    private ContentValues createContentUserValues(String email,String name, String password, String img, int tutorial) {
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, email);
        values.put(KEY_NAME, name);
        values.put(KEY_PASSWORD, password);
        values.put(KEY_IMG, img);
        values.put(KEY_TUTORIAL, tutorial);
        return values;
    }

    private ContentValues tutorialValue(int id){
        ContentValues value = new ContentValues();
        value.put(KEY_TUTORIAL,id);
        return value;
    }

    private ContentValues createContentListValues(String name, String img, int userId) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_IMG, img);
        values.put(KEY_LIST_USERID, userId);
        return values;
    }


    //create a contact
    public long createUser(String email, String name, String password, String img, int tutorial) {
        ContentValues initialValues = createContentUserValues(email,name, password, img, tutorial) ;
        return database.insertOrThrow(DATABASE_TABLE, null, initialValues);
    }

    //create a contact
    public long createList(String name, String img, int userId) {
        ContentValues initialValues = createContentListValues(name, img, userId) ;
        return database.insertOrThrow(DATABASE_LIST, null, initialValues);
    }

    //update a contact
    public boolean updateUser( long userID, String email, String name, String password, String img, int tutorial) {
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
        String[] columns = new String[]{KEY_TUTORIAL, KEY_USERID};
        return database.query(DATABASE_TABLE, columns,"email = '"+email+"' AND password = '"+password+"'",null,null,null,null);
    }

    public boolean updateTutorial(int id,int userId){
        ContentValues tutoVal = tutorialValue(id);
        return database.update(DATABASE_TABLE, tutoVal, KEY_USERID + "=" + userId, null) > 0;
    }
}
