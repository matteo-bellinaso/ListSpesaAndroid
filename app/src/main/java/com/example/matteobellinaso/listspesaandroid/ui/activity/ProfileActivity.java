package com.example.matteobellinaso.listspesaandroid.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.matteobellinaso.listspesaandroid.R;
import com.example.matteobellinaso.listspesaandroid.data.db.DatabaseUserManager;

public class ProfileActivity extends Activity {

    private TextView welcomUserProfile;
    private TextView userProfile;
    private TextView emailProfile;

    private DatabaseUserManager databaseUserManager;
    private Cursor cursor ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent profile = getIntent();
        int userId = profile.getIntExtra("userId",0);


        welcomUserProfile = (TextView)findViewById(R.id.welcomeUser);
        userProfile = (TextView)findViewById(R.id.username_profile);
        emailProfile = (TextView)findViewById(R.id.email_profile);

        databaseUserManager = new DatabaseUserManager(this);
        databaseUserManager.open();


        cursor = databaseUserManager.selectUserById(userId);
        if (cursor != null && cursor.getCount()>0) {
            cursor.moveToFirst();

            String user = cursor.getString(cursor.getColumnIndex("name"));
            String emailprofile = cursor.getString(cursor.getColumnIndex("email"));

            welcomUserProfile.setText("Benvenuto " + user);
            userProfile.setText(user);
            emailProfile.setText(emailprofile);
            cursor.close();
            databaseUserManager.close();

        } else {
            Log.d("crash", "-");
        }
    }
}

