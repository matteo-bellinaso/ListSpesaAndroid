package com.example.matteobellinaso.listspesaandroid.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

        Intent getDetail = getIntent();

        cursor = databaseItemManager.fetchFromId(getDetail.getIntExtra("listId",0));
        mAdapter = new MyCursorAdapter(this,cursor);

        ListView listView = (ListView) findViewById(R.id.listViewDetail);
        listView.setAdapter(mAdapter);

    }
}
