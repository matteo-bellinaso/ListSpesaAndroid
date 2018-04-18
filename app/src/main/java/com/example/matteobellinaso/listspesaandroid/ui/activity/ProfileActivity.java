package com.example.matteobellinaso.listspesaandroid.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.matteobellinaso.listspesaandroid.R;
import com.example.matteobellinaso.listspesaandroid.data.db.DatabaseUserManager;
import com.example.matteobellinaso.listspesaandroid.logic.Utils;

public class ProfileActivity extends AppCompatActivity {

    private TextView welcomUserProfile;
    private TextView userProfile;
    private TextView emailProfile;
    private ImageView imgProfile;
    private Button logoutButton;

    private DatabaseUserManager databaseUserManager;
    private Cursor cursor ;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent profile = getIntent();
        int userId = profile.getIntExtra("userId",0);


        welcomUserProfile = (TextView)findViewById(R.id.welcomeUser);
        userProfile = (TextView)findViewById(R.id.username_profile);
        emailProfile = (TextView)findViewById(R.id.email_profile);
        imgProfile = (ImageView)findViewById(R.id.img_profile_home);
        logoutButton = (Button) findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.deleteSharedPreferences(ProfileActivity.this);
                Intent intent = new Intent(ProfileActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        databaseUserManager = new DatabaseUserManager(this);
        databaseUserManager.open();

        cursor = databaseUserManager.selectUserById(userId);
        if (cursor != null && cursor.getCount()>0) {
            cursor.moveToFirst();

            String user = cursor.getString(cursor.getColumnIndex("name"));
            String emailprofile = cursor.getString(cursor.getColumnIndex("email"));
            String img_profile = cursor.getString(cursor.getColumnIndex("img"));
            if(img_profile != null) {
                Uri uriImg = getUriFromString(img_profile);
                imgProfile.setImageURI(uriImg);
            }

            welcomUserProfile.setText("Benvenuto " + user);
            userProfile.setText(user);
            emailProfile.setText(emailprofile);

            cursor.close();
            databaseUserManager.close();

        } else {
            Log.d("crash", "-");
        }
    }

    public Uri getUriFromString(String stringUri){
        Uri uri = Uri.parse(stringUri);
        return uri;
    }


}

