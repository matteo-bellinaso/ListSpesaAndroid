package com.example.matteobellinaso.listspesaandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.matteobellinaso.listspesaandroid.R;
import com.example.matteobellinaso.listspesaandroid.ui.adapter.SimpleFragmentPageAdapter;

/**
 * Created by matteobellinaso on 09/04/18.
 */

public class TutorialActivity extends AppCompatActivity {

    private int  currentFrag = 0;
    public static ViewPager viewPagerz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        Intent intent = getIntent();

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPagerz = viewPager;

        final SimpleFragmentPageAdapter simpleFragmentPageAdapter = new SimpleFragmentPageAdapter(this, getSupportFragmentManager());

        viewPager.setAdapter(simpleFragmentPageAdapter);

    }

}



