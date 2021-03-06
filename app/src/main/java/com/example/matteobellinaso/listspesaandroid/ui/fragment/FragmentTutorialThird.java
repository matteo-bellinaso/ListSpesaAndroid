package com.example.matteobellinaso.listspesaandroid.ui.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.matteobellinaso.listspesaandroid.R;
import com.example.matteobellinaso.listspesaandroid.data.db.DatabaseUserManager;
import com.example.matteobellinaso.listspesaandroid.logic.Utils;
import com.example.matteobellinaso.listspesaandroid.ui.activity.ListActivity;
import com.example.matteobellinaso.listspesaandroid.ui.activity.MainActivity;

/**
 * Created by matteobellinaso on 09/04/18.
 */

public class FragmentTutorialThird extends Fragment {

    public FragmentTutorialThird(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.tutorial_fragment_third, container, false);

        Button next = (Button) view.findViewById(R.id.button_tutorial_next);

        DatabaseUserManager dbManager = new DatabaseUserManager(getActivity());
        dbManager.open();

        dbManager.updateTutorial(0,Utils.readId(getActivity()));

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ListActivity.class);
                startActivity(intent);
            }
        });


        Button skip = (Button) view.findViewById(R.id.button_tutorial_skip);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ListActivity.class);
                startActivity(intent);


            }
        });


        dbManager.close();

        return view;
    }
}
