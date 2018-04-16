package com.example.matteobellinaso.listspesaandroid.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.matteobellinaso.listspesaandroid.R;
import com.example.matteobellinaso.listspesaandroid.data.db.DatabaseHelper;
import com.example.matteobellinaso.listspesaandroid.data.db.DatabaseItemManager;
import com.example.matteobellinaso.listspesaandroid.ui.adapter.MyCursorAdapter;

public class DetailActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private DatabaseItemManager databaseItemManager;
    private Cursor cursor;
    private MyCursorAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final Intent getDetail = getIntent();

        databaseItemManager = new DatabaseItemManager(this);
        databaseItemManager.open();

        cursor = databaseItemManager.fetchFromId(getDetail.getIntExtra("listId", 0));
        mAdapter = new MyCursorAdapter(this, cursor);

        final ListView listView = (ListView) findViewById(R.id.listViewDetail);
        listView.setAdapter(mAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ImageView checked = (ImageView) view.findViewById(R.id.imgCheck);

                Cursor currentCursor = mAdapter.getCursor();

                if (currentCursor.getInt(currentCursor.getColumnIndex("checked")) == 0) {
                    checked.setVisibility(View.VISIBLE);
                    databaseItemManager.updateList(currentCursor.getInt(currentCursor.getColumnIndex("_id")), 1);
                    Log.d("check","Checked "+currentCursor.getInt(currentCursor.getColumnIndex("checked")));
                } else {
                    checked.setVisibility(View.INVISIBLE);
                    databaseItemManager.updateList(currentCursor.getInt(currentCursor.getColumnIndex("_id")), 0);
                    Log.d("check","Unchecked "+currentCursor.getInt(currentCursor.getColumnIndex("checked")));
                }

                mAdapter.swapCursor(databaseItemManager.fetchFromId(getDetail.getIntExtra("listId", 0)));
                listView.setAdapter(mAdapter);

            }
        });

        CheckBox checkBox = (CheckBox) findViewById(R.id.checkItems);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    listView.setAdapter(new MyCursorAdapter(getApplicationContext(),databaseItemManager.checkedValues(getDetail.getIntExtra("listId", 0))));
                }else{
                    listView.setAdapter(mAdapter);
                }
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        databaseItemManager.close();
    }
}
