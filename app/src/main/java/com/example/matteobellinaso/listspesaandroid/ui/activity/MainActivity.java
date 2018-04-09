package com.example.matteobellinaso.listspesaandroid.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.matteobellinaso.listspesaandroid.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button tutorial = (Button) findViewById(R.id.goToTutorial);

        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent tutorialIntent = new Intent(getApplicationContext(), TutorialActivity.class);
                startActivity(tutorialIntent);

            }
        });



    }





}
