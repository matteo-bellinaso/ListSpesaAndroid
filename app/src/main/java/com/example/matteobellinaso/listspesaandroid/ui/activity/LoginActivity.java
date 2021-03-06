package com.example.matteobellinaso.listspesaandroid.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Base64;

import com.example.matteobellinaso.listspesaandroid.R;
import com.example.matteobellinaso.listspesaandroid.data.db.DatabaseHelper;
import com.example.matteobellinaso.listspesaandroid.data.db.DatabaseUserManager;
import com.example.matteobellinaso.listspesaandroid.logic.Utils;

import java.util.Calendar;

public class LoginActivity extends AppCompatActivity {

    private DatabaseUserManager databaseUserManager;
    private DatabaseHelper dbHelper;
    private Cursor cursor;
    private Calendar calendar;
    private Calendar currentCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        if (Utils.readTimestamp(this) != null) {
            calendar = Calendar.getInstance();
            currentCalendar = Calendar.getInstance();
            calendar.setTimeInMillis(Utils.readTimestamp(this));
            String token = Utils.convertDate(String.valueOf(Utils.readTimestamp(this)), "dd");
            String current = Utils.convertDate(String.valueOf(currentCalendar.getTimeInMillis()), "dd");
            if (token.equals(current)) {
                Intent listIntent = new Intent(this, ListActivity.class);
                startActivity(listIntent);
            }
        }

        final EditText emailValue = (EditText) findViewById(R.id.loginEmail);
        final EditText passwordValue = (EditText) findViewById(R.id.loginPassword);
        Button doLogin = (Button) findViewById(R.id.loginButton);
        final TextView createAccount = (TextView) findViewById(R.id.createAccount);

        databaseUserManager = new DatabaseUserManager(this);
        databaseUserManager.open();


            doLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (emailValue.getText() != null && passwordValue.getText() != null) {
                        String encodedPassword = Base64.encodeToString(String.valueOf(passwordValue.getText()).getBytes(), Base64.DEFAULT);
                        cursor = databaseUserManager.selectUser(String.valueOf(emailValue.getText()), encodedPassword);
                        if(cursor!= null && cursor.moveToFirst()){
                        int id = cursor.getInt(cursor.getColumnIndex(DatabaseUserManager.KEY_USERID));
                        int result = cursor.getInt(cursor.getColumnIndex(DatabaseUserManager.KEY_TUTORIAL));

                        Calendar calendar = Calendar.getInstance();
                        Long timeStamp = calendar.getTimeInMillis();
                        Utils.writeOnSharedPreferences(timeStamp, id, getApplicationContext());


                        if (result == 1) {
                            Intent tutorialIntent = new Intent(getApplicationContext(), TutorialActivity.class);
                            startActivity(tutorialIntent);
                        } else {
                            Intent listIntent = new Intent(getApplicationContext(), ListActivity.class);
                            startActivity(listIntent);
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Utente Non Trovato", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegistrazioneActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        databaseUserManager.close();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseUserManager.close();
    }
}
