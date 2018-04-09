package com.example.matteobellinaso.listspesaandroid.ui.activity;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.matteobellinaso.listspesaandroid.R;
import com.example.matteobellinaso.listspesaandroid.data.db.DbManager;
import com.example.matteobellinaso.listspesaandroid.logic.Utils;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private DbManager dbManager;
    private Cursor cursor;
    private Calendar calendar;
    private Calendar currentCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Utils.readOnSharedPreferences(this ) != null){
            currentCalendar = Calendar.getInstance();
            calendar = Calendar.getInstance();
            calendar.setTimeInMillis(Utils.readOnSharedPreferences(this));
            if(currentCalendar.get(Calendar.DAY_OF_WEEK) != calendar.get(Calendar.DAY_OF_WEEK)){
                setContentView(R.layout.activity_main);
            }
        }

        final EditText emailValue = (EditText) findViewById(R.id.loginEmail);
        final EditText passwordValue = (EditText) findViewById(R.id.loginPassword);
        Button doLogin = (Button) findViewById(R.id.loginButton);

        dbManager = new DbManager(this);
        dbManager.open();

        doLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(emailValue.getText() != null && passwordValue.getText() != null) {
                    cursor = dbManager.selectUser(String.valueOf(emailValue.getText()), String.valueOf(passwordValue.getText()));
                    cursor.moveToFirst();
                    Log.d("DB", "" + cursor.getString(cursor.getColumnIndex(DbManager.KEY_EMAIL)) + " - " + cursor.getString(cursor.getColumnIndex(DbManager.KEY_PASSWORD)));
                    Calendar calendar = Calendar.getInstance();
                    Long timeStamp = calendar.getTimeInMillis();
                    Utils.writeOnSharedPreferences(timeStamp,getApplicationContext());
                }
                dbManager.close();
            }
        });

    }
}
