package com.example.matteobellinaso.listspesaandroid.ui.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.matteobellinaso.listspesaandroid.R;
import com.example.matteobellinaso.listspesaandroid.data.db.DatabaseUserManager;
import com.example.matteobellinaso.listspesaandroid.logic.Utils;

import java.util.Calendar;

public class LoginActivity extends AppCompatActivity {

    private DatabaseUserManager databaseUserManager;
    private Cursor cursor;
    private Calendar calendar;
    private Calendar currentCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Utils.readOnSharedPreferences(this) != null) {
            calendar = Calendar.getInstance();
            currentCalendar = Calendar.getInstance();
            calendar.setTimeInMillis(Utils.readOnSharedPreferences(this));
            String token= Utils.convertDate(String.valueOf(Utils.readOnSharedPreferences(this)),"dd");
            String current= Utils.convertDate(String.valueOf(currentCalendar.getTimeInMillis()),"dd");
            if(token.equals(current)) {
                setContentView(R.layout.login_layout);
            }
        }

        final EditText emailValue = (EditText) findViewById(R.id.loginEmail);
        final EditText passwordValue = (EditText) findViewById(R.id.loginPassword);
        Button doLogin = (Button) findViewById(R.id.loginButton);

        databaseUserManager = new DatabaseUserManager(this);
        databaseUserManager.open();

        doLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (emailValue.getText() != null && passwordValue.getText() != null) {
                    cursor = databaseUserManager.selectUser(String.valueOf(emailValue.getText()), String.valueOf(passwordValue.getText()));
                    if (cursor != null) {
                        cursor.moveToFirst();

                        //Log.d("DB", "" + cursor.getString(cursor.getColumnIndex(DatabaseUserManager.KEY_EMAIL)) + " - " + cursor.getString(cursor.getColumnIndex(DatabaseUserManager.KEY_PASSWORD)));

                        Calendar calendar = Calendar.getInstance();
                        Long timeStamp = calendar.getTimeInMillis();
                        Utils.writeOnSharedPreferences(timeStamp, getApplicationContext());


                    }

                }
                databaseUserManager.close();
            }
        });

    }



}
