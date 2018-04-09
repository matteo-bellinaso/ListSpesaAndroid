package com.example.matteobellinaso.listspesaandroid.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.matteobellinaso.listspesaandroid.R;
import com.example.matteobellinaso.listspesaandroid.data.db.DatabaseUserManager;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class RegistrazioneActivity extends Activity {

    private EditText username;
    private EditText email;
    private EditText password;

    private String pathImmagine;
    private String usernameText;
    private String emailText;
    private String passwordText;

    private DatabaseUserManager databaseUserManager;
    private Cursor cursor;

    private static int RESULT_LOAD_IMG = 1;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);

        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        databaseUserManager = new DatabaseUserManager(this);
        databaseUserManager.open();

        Button registrati = findViewById(R.id.registrati_toDB);
        registrati.setOnClickListener(new View.OnClickListener (){
            @Override
            public void onClick(View v) {
                usernameText = username.getText().toString();
                emailText = email.getText().toString();
                passwordText = password.getText().toString();

                databaseUserManager.createUser(emailText,usernameText,passwordText,"-",true);
                databaseUserManager.close();
            }
        } );

        ImageView imgFavorite = (ImageView) findViewById(R.id.img_profilo);
        imgFavorite.setClickable(true);
        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        });

        Button accedi = findViewById(R.id.accediToLogin);
        accedi.setOnClickListener(new View.OnClickListener (){
            @Override
            public void onClick(View v) {
                //Intent per l'activity di Login
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        } );

    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        ImageView imgFavorite = (ImageView) findViewById(R.id.img_profilo);

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imgFavorite.setImageBitmap(selectedImage);

                pathImmagine = imageUri.getPath();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(getApplicationContext(), "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
}
