package com.example.matteobellinaso.listspesaandroid.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.matteobellinaso.listspesaandroid.R;
import com.example.matteobellinaso.listspesaandroid.data.Item;
import com.example.matteobellinaso.listspesaandroid.data.ItemList;
import com.example.matteobellinaso.listspesaandroid.data.db.DbListManager;
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
    private DbListManager dbListManager;
    private Context mContext;

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        dbListManager = new DbListManager(this);
        dbListManager.open();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new MyRecyclerAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        FloatingActionButton buttons =(FloatingActionButton) findViewById(R.id.floating_button);

        buttons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    addListDialog();
            }
        });
    }


    public void addListDialog(){
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.dialog_custom, null);
        dialogBuilder.setView(dialogView);
        final EditText edit = (EditText) dialogView.findViewById(R.id.edit_add_list);
        dialogBuilder.setTitle(R.string.add_name_list);
        dialogBuilder.setPositiveButton(R.string.alert_confim, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbListManager.createList(edit.getText().toString(), null, Utils.readId(getApplicationContext()));
                mAdapter = new MyRecyclerAdapter(getApplicationContext());
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setLayoutManager(mLayoutManager);
            }
        });

        dialogBuilder.setNegativeButton(R.string.alert_undo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialogBuilder.show();
    }

}
