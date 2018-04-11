package com.example.matteobellinaso.listspesaandroid.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.matteobellinaso.listspesaandroid.R;
import com.example.matteobellinaso.listspesaandroid.ui.activity.MainActivity;
import com.example.matteobellinaso.listspesaandroid.ui.activity.TutorialActivity;

/**
 * Created by matteobellinaso on 09/04/18.
 */

public class FragmentTutorialOne extends Fragment {


    public FragmentTutorialOne(){}

    private ViewPager tabHost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.tutorial_fragment_first, container, false);

        Button next = (Button) view.findViewById(R.id.button_tutorial_next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tabHost = TutorialActivity.viewPagerz;
                tabHost.setCurrentItem(1,true);

            }
        });


        Button skip = (Button) view.findViewById(R.id.button_tutorial_skip);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);


            }
        });



        return view;


    }


}
