package com.example.matteobellinaso.listspesaandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.matteobellinaso.listspesaandroid.R;
import com.example.matteobellinaso.listspesaandroid.data.Item;
import com.example.matteobellinaso.listspesaandroid.data.ItemList;
import com.example.matteobellinaso.listspesaandroid.logic.Utils;
import com.example.matteobellinaso.listspesaandroid.ui.adapter.MyRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matteobellinaso on 09/04/18.
 */

public class ListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public List<ItemList> itemLists  = new ArrayList<ItemList>();

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        /*mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyRecyclerAdapter(this, itemLists);

        mRecyclerView.setAdapter(mAdapter);*/

        Button logout = (Button) findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.deleteSharedPreferences(getApplicationContext());
            }
        });

        Button profile = (Button) findViewById(R.id.profile);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                intent.putExtra("userId",Utils.readId(getApplicationContext()));
                startActivity(intent);
            }
        });

    }


}
