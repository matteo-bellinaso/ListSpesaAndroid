package com.example.matteobellinaso.listspesaandroid.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.example.matteobellinaso.listspesaandroid.R;
import com.example.matteobellinaso.listspesaandroid.data.db.DatabaseUserManager;

public class ProfileActivity extends MainActivity {

    TextView welcomUserProfile;
    TextView userProfile;
    TextView emailProfile;
    Toolbar toolbar;


    private DatabaseUserManager databaseUserManager;
    Cursor cursor ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);



        Intent profile = getIntent();
        String email = profile.getStringExtra("email");
        String password = profile.getStringExtra("password");

        Log.d("username", email);
        Log.d("password", password);



        welcomUserProfile = (TextView)findViewById(R.id.welcomeUser);
        userProfile = (TextView)findViewById(R.id.username_profile);
        emailProfile = (TextView)findViewById(R.id.email_profile);

        databaseUserManager = new DatabaseUserManager(this);
        databaseUserManager.open();


        cursor = databaseUserManager.selectUser(email, password);
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

