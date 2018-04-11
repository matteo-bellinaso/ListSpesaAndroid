package com.example.matteobellinaso.listspesaandroid.ui.activity;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.matteobellinaso.listspesaandroid.R;
import com.example.matteobellinaso.listspesaandroid.data.db.DatabaseHelper;
import com.example.matteobellinaso.listspesaandroid.data.db.DbManager;

public class MainActivity extends AppCompatActivity {

    private DbManager dbManager;
    private DatabaseHelper dbHelper;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText emailValue = (EditText) findViewById(R.id.loginEmail);
        final EditText passwordValue = (EditText) findViewById(R.id.loginPassword);
        Button doLogin = (Button) findViewById(R.id.loginButton);

        dbManager = new DbManager(getApplicationContext());
        dbManager.open();

        doLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(emailValue.getText() != null && passwordValue.getText() != null) {
                    cursor = dbManager.selectUser(String.valueOf(emailValue.getText()), String.valueOf(passwordValue.getText()));
                    cursor.moveToFirst();
                    Log.d("DB", "" + cursor.getString(cursor.getColumnIndex(DbManager.KEY_EMAIL)) + " - " + cursor.getString(cursor.getColumnIndex(DbManager.KEY_PASSWORD)));

                    
                }
                dbManager.close();
            }
        });

    }
}
